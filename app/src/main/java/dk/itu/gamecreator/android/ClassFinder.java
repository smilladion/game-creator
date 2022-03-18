package dk.itu.gamecreator.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexFile;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.SolutionComponent;

public final class ClassFinder {

    private static final Class<?> CLASS_LOADER_TYPE = BaseDexClassLoader.class;

    private static final String PATH_LIST_FIELD = "pathList";
    private static final String DEX_ELEMENTS_FIELD = "dexElements";
    private static final String DEX_FILE_FIELD = "dexFile";

    private static final int BUFFER_SIZE = 16 * 1024;
    private static final String DEX_CLASS_PATTERN = "classes\\d*\\.dex";

    private static final String PACKAGE_PREFIX = "dk.itu.gamecreator.android.Components.";

    private static final Class<?>[] VALID_PARENTS = {
            GameComponent.class,
            SolutionComponent.class
    };

    private ClassFinder() {}

    public static Collection<String> find() {
        ClassLoader classLoader = ClassFinder.class.getClassLoader();
        Collection<String> matches = ClassFinder.find(classLoader, PACKAGE_PREFIX);

        Iterator<String> iterator = matches.iterator();

        while (iterator.hasNext()) {
            String path = iterator.next();

            if (path.contains("$")) {
                iterator.remove();
                continue;
            }

            try {
                Class<?> clazz = classLoader.loadClass(path);

                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    iterator.remove();
                    continue;
                }

                boolean valid = false;

                for (Class<?> parent : VALID_PARENTS) {
                    if (doesExtend(clazz, parent)) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    iterator.remove();
                }
            } catch (ClassNotFoundException e) {
                iterator.remove();
            }
        }

        return matches;
    }

    private static boolean doesExtend(Class<?> child, Class<?> parent) {
        while (child != null && child != Object.class) {
            if (child == parent) {
                return true;
            }

            child = child.getSuperclass();
        }

        return false;
    }

    public static Collection<String> find(ClassLoader classLoader, String packagePrefix) {
        // Only specific Android class loader types work with this
        if (!CLASS_LOADER_TYPE.isAssignableFrom(classLoader.getClass())) {
            return Collections.emptyList();
        }

        List<String> matches = new ArrayList<>();

        try {
            // 1: Find the path list field on the current class loader
            Field pathListField = CLASS_LOADER_TYPE.getDeclaredField(PATH_LIST_FIELD);
            pathListField.setAccessible(true);
            Object pathListObj = pathListField.get(classLoader);

            if (pathListObj == null) {
                return Collections.emptyList();
            }

            // 2: Find the elements (individual paths) inside the path list
            Field dexElementsField = pathListObj.getClass().getDeclaredField(DEX_ELEMENTS_FIELD);
            dexElementsField.setAccessible(true);
            Object[] dexElementsObj = (Object[]) dexElementsField.get(pathListObj);

            if (dexElementsObj == null || dexElementsObj.length == 0) {
                return Collections.emptyList();
            }

            // 3: For each element (path to apk/dex) inside the list
            for (Object dexElementObj : dexElementsObj) {
                List<Field> potentialFields = new ArrayList<>();

                // 4: Find fields which potentially contain the loaded DexFile
                for (Field dexElementField : dexElementObj.getClass().getDeclaredFields()) {
                    if (dexElementField.getType() == DexFile.class) {
                        potentialFields.add(dexElementField);
                        dexElementField.setAccessible(true);
                    }
                }

                DexFile dexFile = null;

                // 5: Find the best matching field containing the DexFile
                for (Field dexElementField : potentialFields) {
                    if (dexElementField.getName().equals(DEX_FILE_FIELD)) {
                        dexFile = (DexFile) dexElementField.get(dexElementObj);
                    }
                }

                // 6: Use the first potential field if no best match is found
                if (dexFile == null && !potentialFields.isEmpty()) {
                    dexFile = (DexFile) potentialFields.get(0).get(dexElementObj);
                }

                // 7: Loop through all of the class entries inside the DexFile
                if (dexFile != null) {
                    ClassFinder.find(dexFile, packagePrefix, matches);
                }

                // Old method that may not be as portable as the above:

                /*
                Field dexFileField = dexElementObj.getClass().getDeclaredField("dexFile");
                dexFileField.setAccessible(true);
                Object dexFieldObj = dexFileField.get(dexElementObj);

                Field pathField = dexElementObj.getClass().getDeclaredField("path");
                pathField.setAccessible(true);
                Object pathObj = pathField.get(dexElementObj);
                */
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return matches;
    }

    public static Collection<String> find(String path, String packagePrefix) {
        List<String> matches = new ArrayList<>();

        // Second method to find all class names by manually loading the base apk file
        // Can be used as a fallback when the first (faster) method fails

        try {
            // 1: Load the apk as a zip file
            ZipFile zipFile = new ZipFile(path);
            Enumeration<? extends ZipEntry> zipIterator = zipFile.entries();

            // 2: Loop through each file inside the zip
            while (zipIterator.hasMoreElements()) {
                ZipEntry zipEntry = zipIterator.nextElement();

                // 3: Skip any files that are not classesXYZ.dex
                if (!zipEntry.getName().matches(DEX_CLASS_PATTERN)) {
                    continue;
                }

                // 4: Load all bytes from the classesXYZ.dex file into memory
                try (InputStream zipIs = zipFile.getInputStream(zipEntry);
                     ByteArrayOutputStream arrayOs = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[BUFFER_SIZE];

                    // 5: Read 16 * 1024 bytes at a time and write to our output stream
                    for (int c = zipIs.read(buffer); c != -1; c = zipIs.read(buffer)) {
                        arrayOs.write(buffer, 0, c);
                    }

                    // 6: Convert our output stream to a byte array (dex bytes are loaded)
                    byte[] data = arrayOs.toByteArray();

                    // 7: Create a temporary file and write our decompressed data
                    File file = File.createTempFile(zipEntry.getName(), null);

                    try (FileOutputStream fileOs = new FileOutputStream(file)) {
                        fileOs.write(data);
                    }

                    // 8: Load the temporary decompressed file as a DexFile object
                    DexFile dexFile = new DexFile(file);
                    ClassFinder.find(dexFile, packagePrefix, matches);
                    dexFile.close();
                }
            }

            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matches;
    }

    private static void find(DexFile dexFile, String packagePrefix, List<String> matches) {
        Enumeration<String> dexIterator = dexFile.entries();

        while (dexIterator.hasMoreElements()) {
            String entry = dexIterator.nextElement();

            // Only add entries which match our package prefix
            if (entry.startsWith(packagePrefix)) {
                matches.add(entry);
            }
        }
    }
}
