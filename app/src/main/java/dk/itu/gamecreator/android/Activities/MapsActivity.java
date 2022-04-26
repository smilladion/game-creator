package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Util;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ComponentDB cDB;

    private static final int DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        cDB = ComponentDB.getInstance();

        // Action bar
        setTitle("World Map");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Game game : cDB.getAllGames()) {
            LatLng location = new LatLng(game.getLocation().getLatitude(), game.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title(game.getName()).snippet("Click here to play"));
        }

        mMap.setOnInfoWindowClickListener(marker -> {
            for (Game game : cDB.getAllGames()) {
                if (game.getName().equals(marker.getTitle())) {
                    cDB.setCurrentGame(game);
                    Intent intent = new Intent(this, GameActivity.class);
                    startActivity(intent);
                }
            }
        });

        Util.requestCurrentLocation(this, location -> {
            if (location == null) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM
            ));
        });
    }

    // Used for the back button in the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}