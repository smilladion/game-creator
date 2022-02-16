package dk.itu.gamecreator.android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.R;

public class MainActivity extends AppCompatActivity {

    private Button play;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play_button);
        create = findViewById(R.id.create_button);

        play.setOnClickListener(this::onPlayClicked);
        create.setOnClickListener(this::onCreateClicked);
    }

    private void onPlayClicked(View view) {

    }

    private void onCreateClicked(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}
