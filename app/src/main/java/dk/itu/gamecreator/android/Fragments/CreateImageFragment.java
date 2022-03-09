package dk.itu.gamecreator.android.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.ImageComponent;
import dk.itu.gamecreator.android.R;

public class CreateImageFragment extends Fragment {
    private static final int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/comp_image";
    static final int ROTATE_LEFT = 0, ROTATE_RIGHT = 1;
    static final int MAKE_SMALLER = 0, MAKE_BIGGER = 1;
    static final int RESIZE = 40;


    Button doneButton, discardButton;
    Button selectImageButton, takePictureButton;

    ImageView imageView;

    Button rotateLeftButton, rotateRightButton;
    Button smallerButton, biggerButton;

    String currentPhotoPath;
    ImageComponent component;
    ComponentDB cDB;

    Bitmap bitmap;

    Context context;

    float rotation;
    int height;
    int width;

    /*This is just used to get the context of the Activity*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();
        Bundle bundle = getArguments();

        // Checks if the fragment was opened through an edit button and fetches component data
        if (bundle != null) {
            int index = bundle.getInt("componentIndex");
            component = (ImageComponent) cDB.getCurrentGame().getComponents().get(index);
        }

        return inflater.inflate(R.layout.fragment_create_image_component, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        cDB = ComponentDB.getInstance();

        selectImageButton = view.findViewById(R.id.select_image_button);
        takePictureButton = view.findViewById(R.id.take_picture_button);
        imageView = view.findViewById(R.id.preview_image_view);
        rotateLeftButton = view.findViewById(R.id.rotate_left_button);
        rotateRightButton = view.findViewById(R.id.rotate_right_button);
        smallerButton = view.findViewById(R.id.minimise_button);
        biggerButton = view.findViewById(R.id.maximise_button);

        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        selectImageButton.setOnClickListener(this::openGallery);
        takePictureButton.setOnClickListener(this::openCamera);
        rotateLeftButton.setOnClickListener(v -> rotate(ROTATE_LEFT));
        rotateRightButton.setOnClickListener(v -> rotate(ROTATE_RIGHT));
        /*
        smallerButton.setOnClickListener(v -> resize(MAKE_SMALLER));
        biggerButton.setOnClickListener(v -> resize(MAKE_BIGGER));
        ?/
         */
        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::onDiscardClicked);

        rotation = imageView.getRotation();

        //height = imageView.getHeight();
        //width = imageView.getWidth();

        if (component != null) {
            imageView.setImageBitmap(component.getBitmap());
            bitmap = component.getBitmap();
            rotation = component.getRotation();
        }

    }
    //
    public void rotate(int direction) {
        if (direction == ROTATE_LEFT) {
            rotation = rotation - 90;
        } else {
            rotation = rotation + 90;
        }
        imageView.setRotation(rotation);
    }
    /* TODO - taking out resize function can't make it work
    public void resize(int direction) {
        if (direction == MAKE_SMALLER) {
            width = width - RESIZE;
            height = height - RESIZE;
        } else {
            width = width + RESIZE;
            height = height + RESIZE;
        }
        imageView.getLayoutParams().height = height;
        imageView.getLayoutParams().width = width;
        imageView.requestLayout();
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }*/

    /* Opens the gallery with an implicit intent, and starts an Activity for result
    * - This ensures that when the activity "returns", it knows what to do with the
    * result.
    * startActivityForResult is deprecated, but it is very limited what can be found about
     * the new method online, and what HAS been found seems to be more complicated.*/
    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }

    /* Opens the camera with an implicit intent, and starts an Activity for result
     * - This ensures that when the activity "returns", it knows what to do with the
     * result.
     * startActivityForResult is deprecated, but it is very limited what can be found about
     * the new method online, and what HAS been found seems to be more complicated. */
    public void openCamera(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    /* When any Activity returns to this UI, this function is called.
    * It is deprecated (See reasons in comments above) */
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
                    //Bitmap resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    imageView.setImageBitmap(bitmap);
                    // TODO ? Image comes out rotated - to have it normal, do this.
                    imageView.setRotation(270);
                    currentPhotoPath = saveImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            currentPhotoPath = saveImage(bitmap);
        }
    }

    /*  Takes the bitmap and converts it into a file and returns the path to the save image. */
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

    public void onDoneClicked(View view) {
        if (component != null) {
            component.setBitmap(bitmap);
            component.setRotation(rotation);
        } else {
            ImageComponent ic = new ImageComponent(cDB.getNextComponentId(), bitmap, rotation, width, height);
            cDB.getCurrentGame().addComponent(ic);
        }

        getParentFragmentManager().popBackStack(); // Close fragment and go back to editor
    }

    public void onDiscardClicked(View view) {
        getParentFragmentManager().popBackStack();
    }
}
