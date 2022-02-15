package dk.itu.gamecreator.android;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class CreateActivity extends AppCompatActivity {

    Spinner gameComponentDropdown;
    Spinner solutionComponentDropdown;
    ComponentDB cDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        gameComponentDropdown = findViewById(R.id.game_component_spinner);
        solutionComponentDropdown = findViewById(R.id.solution_component_spinner);
        cDB = ComponentDB.getInstance();

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.game_components,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.solution_components,
                android.R.layout.simple_spinner_item);

        gameComponentDropdown.setAdapter(adapter1);
        solutionComponentDropdown.setAdapter(adapter2);

        FragmentManager fm = getSupportFragmentManager();

        gameComponentDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int pos, long id) {
                String component = parent.getItemAtPosition(pos).toString();

                switch (component) {
                    case "Text":
                        fm.beginTransaction().setReorderingAllowed(true)
                                .add(R.id.create_fragment, CreateTextComponentFragment.class, null)
                                .commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        fm.beginTransaction().setReorderingAllowed(true)
                .add(R.id.create_fragment, EditorFragment.class, null)
                .commit();

    }
}


