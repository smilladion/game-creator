package dk.itu.gamecreator.android.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import dk.itu.gamecreator.android.R;

public class MainActivity extends AppCompatActivity {

    public static final int LOCATION_PERMISSION_REQ_ID = 1099;

    private Button play;
    private Button create;
    private Button myGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Game Creator");

        play = findViewById(R.id.play_button);
        create = findViewById(R.id.create_button);
        myGames = findViewById(R.id.my_games_button);

        requestPermissions();

        play.setOnClickListener(this::onPlayClicked);
        create.setOnClickListener(this::onCreateClicked);
        myGames.setOnClickListener(this::OnMyGamesClicked);
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // We already have permissions
            return;
        }

        // TODO: Show a prompt explaining rationale for permissions (see Android docs)

        // We don't have permissions, so we need to request them
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                },
                LOCATION_PERMISSION_REQ_ID
        );
    }

    private void onPlayClicked(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void onCreateClicked(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    private void OnMyGamesClicked(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
}
