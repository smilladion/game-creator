package dk.itu.gamecreator.android.Fragments;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    Uri pictureUri = null;

    Context context;

    private static final int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/comp_image";


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

        selectImageButton.setOnClickListener(this::imageChooser);
        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::closeFragment);
    }

/*
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
    } */

    public void imageChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }

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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    String path = saveImage(bitmap);
                    imageView.setImageBitmap(bitmap);
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
            return file.getAbsolutePath();
        }
    }



    public void onDoneClicked(View view) {
        closeFragment(view);
    }

    public void closeFragment(View view) {
        Intent intent = new Intent(this.getContext(), CreateActivity.class);
        startActivity(intent);
    }
}
