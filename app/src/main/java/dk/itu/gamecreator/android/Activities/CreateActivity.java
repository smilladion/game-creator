package dk.itu.gamecreator.android.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import dk.itu.gamecreator.android.ComponentDB;

import dk.itu.gamecreator.android.Fragments.CreateImageFragment;
import dk.itu.gamecreator.android.Fragments.CreateTextFragment;
import dk.itu.gamecreator.android.Fragments.CreateTextSolutionFragment;

import dk.itu.gamecreator.android.Fragments.EditorFragment;
import dk.itu.gamecreator.android.R;

public class CreateActivity extends AppCompatActivity {

    Button createTextButton;
    Button createTextSolutionButton;
    Button createImageButton;

    ComponentDB cDB;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = getSupportFragmentManager();

        cDB = ComponentDB.getInstance();
        createTextButton = findViewById(R.id.create_text_button);
        createTextButton.setOnClickListener(this::createText);

        createTextSolutionButton = findViewById(R.id.create_text_solution_button);
        createTextSolutionButton.setOnClickListener(this::createSolutionText);

        createImageButton = findViewById(R.id.create_image_button);
        createImageButton.setOnClickListener(this::createImage);

        fm.beginTransaction().setReorderingAllowed(true)
                .add(R.id.create_fragment, EditorFragment.class, null)
                .commit();
    }

    public void createImage(View view) {
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, CreateImageFragment.class, null)
                .addToBackStack(null)
                .commit();
    }

    public void createSolutionText(View view) {
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, CreateTextSolutionFragment.class, null)
                .addToBackStack(null)
                .commit();

        setButtonsEnabled(false);
    }

    public void createText(View view) {
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, CreateTextFragment.class, null)
                .addToBackStack(null)
                .commit();

        setButtonsEnabled(false);
    }

    public void setButtonsEnabled(boolean isEnabled) {
        createTextButton.setEnabled(isEnabled);
        createTextSolutionButton.setEnabled(isEnabled);
    }

    // Used for the back button in the title bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
