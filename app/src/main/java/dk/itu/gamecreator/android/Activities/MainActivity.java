package dk.itu.gamecreator.android.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.function.Consumer;

import dk.itu.gamecreator.android.R;

public class MainActivity extends AppCompatActivity {

    public static final int LOCATION_PERMISSION_REQ_ID = 1099;

    private Button play;
    private Button create;
    private Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Game Creator");

        play = findViewById(R.id.play_button);
        create = findViewById(R.id.create_button);
        map = findViewById(R.id.map_button);

        if (requestPermissions()) {
            // We already have permissions, do the location check
            requestLocation(this::onFirstLocationReceived);
        }

        play.setOnClickListener(this::onPlayClicked);
        create.setOnClickListener(this::onCreateClicked);
        map.setOnClickListener(this::onMapClicked);
    }

    // This method runs after the user has given a response to a permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQ_ID) {
            // We just received a permission response, try location check
            requestLocation(this::onFirstLocationReceived);
        }
    }

    private boolean requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // We already have permissions
            return true;
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

        return false;
    }

    private boolean requestLocation(Consumer<Location> callback) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // We do not have location permissions so the request failed
            return false;
        }

        FusedLocationProviderClient locationProvider = LocationServices.getFusedLocationProviderClient(this);

        // Android Studio complains about this line but it should still work
        locationProvider.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, null)
                .addOnSuccessListener(location -> {
                    if (location == null) {
                        return;
                    }

                    callback.accept(location);
                });

        return true;
    }

    private void onFirstLocationReceived(Location location) {
        System.out.println(location);
    }

    private void onPlayClicked(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    private void onCreateClicked(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    private void onMapClicked(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
