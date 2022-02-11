package com.bignerdranch.android.gamecreator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner gameComponentDropdown;
    Spinner solutionComponentDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        gameComponentDropdown = findViewById(R.id.game_component_spinner);
        solutionComponentDropdown = findViewById(R.id.solution_component_spinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.game_components,
                android.R.layout.simple_spinner_item);

        gameComponentDropdown.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.solution_components,
                android.R.layout.simple_spinner_item);

        solutionComponentDropdown.setAdapter(adapter2);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String component = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
