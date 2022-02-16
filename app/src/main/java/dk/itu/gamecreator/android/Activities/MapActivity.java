package dk.itu.gamecreator.android.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.MapFragment;

import dk.itu.gamecreator.android.R;

public class MapActivity extends AppCompatActivity {

    static int ALL_PERMISSIONS_RESULT = 1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment f = new MapFragment();
    }

}
