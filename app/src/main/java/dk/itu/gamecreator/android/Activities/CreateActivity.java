package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Fragments.CreateImageComponentFragment;
import dk.itu.gamecreator.android.Fragments.CreateTextComponentFragment;
import dk.itu.gamecreator.android.Fragments.CreateTextSolutionComponentFragment;
import dk.itu.gamecreator.android.Fragments.EditorFragment;
import dk.itu.gamecreator.android.R;

public class CreateActivity extends AppCompatActivity {

    //Spinner gameComponentDropdown;
    //Spinner solutionComponentDropdown;
    Button createTextButton;
    Button createTextSolutionButton;
    Button createImageButton;
    Button backButton;
    ComponentDB cDB;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        fm = getSupportFragmentManager();

        cDB = ComponentDB.getInstance();
        createTextButton = findViewById(R.id.create_text_button);
        createTextButton.setOnClickListener(this::createText);

        createTextSolutionButton = findViewById(R.id.create_text_solution_button);
        createTextSolutionButton.setOnClickListener(this::createSolutionText);

        createImageButton = findViewById(R.id.create_image_button);
        createImageButton.setOnClickListener(this::createImage);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this::goBack);

        fm.beginTransaction().setReorderingAllowed(true)
                .add(R.id.create_fragment, EditorFragment.class, null)
                .commit();

    }

    public void createImage(View view) {
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, CreateImageComponentFragment.class, null)
                .commit();
    }

    public void createSolutionText(View view) {
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, CreateTextSolutionComponentFragment.class, null)
                .commit();
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createText(View view) {
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, CreateTextComponentFragment.class, null)
                .commit();
    }
}


