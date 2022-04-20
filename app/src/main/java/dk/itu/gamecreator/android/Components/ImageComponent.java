package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import dk.itu.gamecreator.android.R;

public class ImageComponent extends GameComponent {

    private static final int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/comp_image";
    private static final int ROTATE_LEFT = 0, ROTATE_RIGHT = 1;

    private ImageView image;
    private Bitmap bitmap;
    private Button selectImageButton, takePictureButton;
    private Button rotateLeftButton, rotateRightButton;
    private Context context;
    private FragmentManager fragM;

    public ImageComponent(int id) {
        super(id);
    }

    public View getDisplayView(Context context) {
        this.context = context;
        image = new ImageView(context);
        image.setLayoutParams(new LinearLayout.LayoutParams(650, 600));
        image.setImageBitmap(bitmap);
        return image;
    }

    @Override
    public View getCreateView(Context context) {
        this.context = context;
        this.fragM = ((FragmentActivity) context).getSupportFragmentManager();

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_image_component, null, false);

        selectImageButton = view.findViewById(R.id.select_image_button);
        takePictureButton = view.findViewById(R.id.take_picture_button);
        rotateLeftButton = view.findViewById(R.id.rotate_left_button);
        rotateRightButton = view.findViewById(R.id.rotate_right_button);
        image = view.findViewById(R.id.preview_image_view);

        selectImageButton.setOnClickListener(this::openGallery);
        takePictureButton.setOnClickListener(this::openCamera);
        rotateLeftButton.setOnClickListener(v -> rotate(v, ROTATE_LEFT));
        rotateRightButton.setOnClickListener(v -> rotate(v, ROTATE_RIGHT));

        if (bitmap != null) {
            image.setImageBitmap(bitmap);
        }

        return view;
    }

    @Override
    public String getName() {
        return "Image";
    }

    @Override
    public boolean saveComponent(Context context) {
        if (image.getDrawable() == null) {
            Toast toast = Toast.makeText(context,
                    "You must choose an image or discard component", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        return true;
    }

    public void openGallery(View view) {
        Fragment f = new GalleryFragment(context, image);
        fragM.beginTransaction()
                .add(f, "hey")
                .commit();
    }

    public void openCamera(View view) {
        Fragment f = new CameraFragment(image);
        fragM.beginTransaction()
                .add(f, "hey")
                .commit();
    }

    public void rotate(View view, int direction) {
        Matrix matrix = new Matrix();

        if (direction == ROTATE_LEFT) {
            matrix.postRotate(-90);
        } else {
            matrix.postRotate(90);
        }

        if (image.getDrawable() != null) {
            Bitmap bp = ((BitmapDrawable) image.getDrawable()).getBitmap();
            bp = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
            image.setImageBitmap(bp);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public static String saveImage(Bitmap bitmap) {
        File file = null;

        try {
            file = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            file.createNewFile();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bytes);
            byte[] bitmapdata = bytes.toByteArray();

            FileOutputStream fstream = new FileOutputStream(file);
            fstream.write(bitmapdata);
            fstream.flush();
            fstream.close();
            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            //returns null
            return file.getAbsolutePath();
        }
    }

    /**
     * Inner class CameraFragment - it has to be used in order to open an Activity (implicit
     * activity 'camera'. In order to use it, it is required to use startActivityForResult, and override
     * the method 'onActivityResult'. This can only be done from Activity or Fragment.
     * */
    public static class CameraFragment extends Fragment {

        private final ImageView image;

        private CameraFragment(ImageView image) {
            this.image = image;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode,
                                     Intent data) {
            if (resultCode == getActivity().RESULT_CANCELED) {
                return;
            }
            if (requestCode == CAMERA) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bitmap);
                saveImage(bitmap);
            }
        }
    }
    /**
     * Inner class GalleryFragment - it has to be used in order to open an Activity (implicit
     * activity 'gallery'. In order to use it, it is required to use startActivityForResult, and override
     * the method 'onActivityResult'. This can only be done from Activity or Fragment.
     * */
    public static class GalleryFragment extends Fragment {

        private final Context context;
        private final ImageView image;

        private GalleryFragment(Context context, ImageView image) {
            this.context = context;
            this.image = image;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode,
                                     Intent data) {
            if (resultCode == getActivity().RESULT_CANCELED) {
                return;
            }
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

                        image.setImageBitmap(bitmap);

                        saveImage(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
