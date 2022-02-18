package dk.itu.gamecreator.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.R;

public class CreateImageFragment extends Fragment {

    Button doneButton;
    Button discardButton;

    Button selectImageButton;
    ImageView imageView;
    int SELECT_PICTURE = 200;

    GameComponent gameComponent;
    ComponentDB cDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_text_component, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        cDB = ComponentDB.getInstance();

        selectImageButton = view.findViewById(R.id.select_image_button);
        imageView = view.findViewById(R.id.preview_image_view);

        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::closeFragment);
    }

    public void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

    }

    public void onDoneClicked(View view) {
        closeFragment(view);
    }

    public void closeFragment(View view) {
        Intent intent = new Intent(this.getContext(), CreateActivity.class);
        startActivity(intent);
    }
}
