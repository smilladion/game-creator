package dk.itu.gamecreator.android.Components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    static final int ROTATE_LEFT = 0, ROTATE_RIGHT = 1;
    private static ImageView image;
    private static Bitmap bitmap;
    private Button selectImageButton, takePictureButton;
    private Button rotateLeftButton, rotateRightButton;
    private static Context context;
    private static String currentPhotoPath;
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
        selectImageButton.setOnClickListener(v -> testGallery(v, context));
        return view;
    }

    @Override
    public String getGravity() {
        return "center";
    }

    @Override
    public void saveComponent(Context context) {

    }

    public static class GalleryFragment extends Fragment {
        //Context context;
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
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

                        // Image comes out rotated - to have it normal, do this.
                        Matrix matrix = new Matrix();
                        matrix.postRotate(270);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                        image.setImageBitmap(bitmap);
                        currentPhotoPath = saveImage(bitmap);

                        //setButtonVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public String saveImage(Bitmap bitmap) {
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
    }

    public void testGallery(View view, Context context) {
        Fragment f = new GalleryFragment();
        fragM.beginTransaction()
                .add(f, "hey")
                .commit();
        }

        public void openGallery (View view, Context context){
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, GALLERY);
            }
            //startActivityForResult(intent, GALLERY);
        }

        public void openCamera (View view){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, CAMERA);
            }
        }
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

                    // Image comes out rotated - to have it normal, do this.
                    Matrix matrix = new Matrix();
                    matrix.postRotate(270);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    image.setImageBitmap(bitmap);
                    currentPhotoPath = saveImage(bitmap);

                    //setButtonVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
            currentPhotoPath = saveImage(bitmap);

            //setButtonVisibility(View.VISIBLE);
        }
    }*/

        public void rotate ( int direction){
            Matrix matrix = new Matrix();

            if (direction == ROTATE_LEFT) {
                matrix.postRotate(-90);
            } else {
                matrix.postRotate(90);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            image.setImageBitmap(bitmap);
        }

        public void setBitmap (Bitmap bitmap){
            this.bitmap = bitmap;
        }

        public Bitmap getBitmap () {
            return bitmap;
        }
    }

