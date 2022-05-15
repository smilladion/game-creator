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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    // load() only retrieves classes that extend these abstract classes
    private static final Class<?>[] GAME_COMPONENT = {
            GameComponent.class
    };

    private static final Class<?>[] SOLUTION_COMPONENT = {
            SolutionComponent.class
    };

    private ClassFinder() {}

    /** Returns all component classes that extend GameComponent or SolutionComponent. */
    public static List<ArrayList<Class<?>>> load() {
        ClassLoader classLoader = ClassFinder.class.getClassLoader();
        Collection<String> matches = ClassFinder.find(classLoader, PACKAGE_PREFIX);

        List<ArrayList<Class<?>>> loaded = new ArrayList<>();
        ArrayList<Class<?>> gameComponents = new ArrayList<>();
        ArrayList<Class<?>> solutionComponents = new ArrayList<>();
        loaded.add(gameComponents);
        loaded.add(solutionComponents);

        for (String path : matches) {
            // If it's an inner class, skip it
            if (path.contains("$")) {
                continue;
            }
            System.out.println("Path to class is: " + path);

            try {
                Class<?> clazz = classLoader.loadClass(path);

                // If it's an interface or abstract class, skip it
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }

                for (Class<?> parent : GAME_COMPONENT) {
                    if (doesExtend(clazz, parent)) {
                        gameComponents.add(clazz);
                    }
                }

                for (Class<?> parent : SOLUTION_COMPONENT) {
                    if (doesExtend(clazz, parent)) {
                        solutionComponents.add(clazz);
                    }
                }

            } catch (ClassNotFoundException ignored) {
            }
        }

        return loaded;
    }

    /** Checks if the child class extends the parent class. */
    private static boolean doesExtend(Class<?> child, Class<?> parent) {
        while (child != null && child != Object.class) {
            if (child == parent) {
                return true;
            }

            child = child.getSuperclass();
        }

        return false;
    }

    /** Returns all classes within the Components package, including inner classes (such as ClickListeners). */
    public static Collection<String> find(ClassLoader classLoader, String packagePrefix) {
        // Only specific Android class loader types work with this
        if (!CLASS_LOADER_TYPE.isAssignableFrom(classLoader.getClass())) {
            return Collections.emptyList();
        }

        Set<String> matches = new HashSet<>();

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

    /** Second method to find all class names by manually loading the base apk file.
     * Can be used as a fallback if/when the first (faster) method fails. */
    public static Collection<String> find(String path, String packagePrefix) {
        Set<String> matches = new HashSet<>();

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

    /** Adds all classes found within the DexFile, which are located in the Components package, to the given "matches" set. */
    private static void find(DexFile dexFile, String packagePrefix, Set<String> matches) {
        Enumeration<String> dexIterator = dexFile.entries();

        while (dexIterator.hasMoreElements()) {
            String entry = dexIterator.nextElement();

            // Only add entries which match our package prefix
            if (entry.startsWith(packagePrefix)) {
                matches.add(entry);
            }
        }
    }

    public static String nameForClass(Class<?> clazz) {
        String name = clazz.getSimpleName();

        return name.replace("Component", "")
                .replaceAll("(?=[A-Z])", " ").trim();
    }
}
