package dk.itu.gamecreator.android.Fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.R;

public class CreateImageComponentFragment extends Fragment {

    Button doneButton;
    Button discardButton;

    Button selectImageButton;
    Button takePictureButton;
    ImageView imageView;
    int SELECT_PICTURE = 200;
    String currentPhotoPath;

    GameComponent gameComponent;
    ComponentDB cDB;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    //static final Uri locationForPhotos;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_image_component, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        cDB = ComponentDB.getInstance();

        selectImageButton = view.findViewById(R.id.select_image_button);
        takePictureButton = view.findViewById(R.id.take_picture_button);
        imageView = view.findViewById(R.id.preview_image_view);

        takePictureButton.setOnClickListener(this::dispatchTakePictureIntent);

        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::closeFragment);
    }

    private void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            System.out.println("HEEEY");
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onDoneClicked(View view) {
        closeFragment(view);
    }

    public void closeFragment(View view) {
        Intent intent = new Intent(this.getContext(), CreateActivity.class);
        startActivity(intent);
    }
}
