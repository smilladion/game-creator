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

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.ImageComponent;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.R;

public class CreateImageFragment extends Fragment {

    Button doneButton;
    Button discardButton;

    Button selectImageButton;
    Button takePictureButton;
    ImageView imageView;
    //int SELECT_PICTURE = 200;
    String currentPhotoPath;
    ImageComponent component;
    ComponentDB cDB;

    Uri pictureUri = null;
    Bitmap bitmap;

    Context context;

    private static final int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/comp_image";


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

        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        selectImageButton.setOnClickListener(this::openGallery);
        takePictureButton.setOnClickListener(this::openCamera);
        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::closeFragment);

        if (component != null) {
            imageView.setImageBitmap(component.getBitmap());
            bitmap = component.getBitmap();
        }

    }

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
        } else {
            int id = cDB.getNextId();
            component = new ImageComponent(id, bitmap);
            cDB.getCurrentGame().addComponent(component);
        }
        closeFragment(view);
    }

    public void closeFragment(View view) {
        Intent intent = new Intent(this.getContext(), CreateActivity.class);
        startActivity(intent);
    }
}
