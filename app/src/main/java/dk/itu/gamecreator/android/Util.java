package dk.itu.gamecreator.android;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.function.Consumer;

public class Util {
    public static boolean requestCurrentLocation(Context context, Consumer<Location> callback) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    ){
            // We do not have location permissions so the request failed
            return false;
        }

        FusedLocationProviderClient locationProvider = LocationServices.getFusedLocationProviderClient(context);

        // Android Studio complains about this line but it should still work
        locationProvider.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(location -> {
                    if (location == null) {
                        return;
                    }

                    callback.accept(location);
                });

        return true;
    }
}
