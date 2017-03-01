package com.gmd.android.lesson5;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AndroidLocationActivity extends AppCompatActivity {

    private static final int DOS_MINUTOS = 1000 * 60 * 2;
    private static final int FINE_LOCATION_REQUEST_CODE = 100;
    private android.location.LocationManager locationManager;
    private LocationListener locationListener;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        setLocationUpdates();


    }

    private void setLocationUpdates() {
        // Obtener una referencia al location manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Definimos un listener que responde a las actualizaciones de locacion
        locationListener = new LocationListener() {

            // Es llamado cuando una nueva locacion es encontrada por el proveedor de locacion
            public void onLocationChanged(Location location) {
                String str = String.format("latitud %f, longitud %f", location.getLatitude(), location.getLongitude());
                Log.e("Pablo", str);
                textView.setText(str);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.e("Pablo", "onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                Log.e("Pablo", "onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                Log.e("Pablo", "onProviderDisabled");
            }
        };
    }

    private void requestLocationsUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocationsUpdate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestLocationsUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseLocationsUpdate();
    }

    private void pauseLocationsUpdate() {
        // Remove the listener you previously added
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(locationListener);
    }

    /** Determina si una locacion es mejor que la actual mejor locacion
     * @param newLocation  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // Una nueva locacion siempre es mejor que ninguna locacion
            return true;
        }

        // Chequea si la nueva locacion es nueva o antigua
        long diferenciaDeTiempo = newLocation.getTime() - currentBestLocation.getTime();
        boolean esSignificativamenteNueva = diferenciaDeTiempo > DOS_MINUTOS;
        boolean esSignificativamenteVieja = diferenciaDeTiempo < -DOS_MINUTOS;
        boolean esNueva = diferenciaDeTiempo > 0;

        // Si ya ha pasado mas de dos minutos desde la locacion actual, entonces usar la nueva
        // ya que el usuario puede haberse movido
        if (esSignificativamenteNueva) {
            return true;
            // Si la nueva locacion es mas de dos minutos vieja entonces esta debe ser peor
        } else if (esSignificativamenteVieja) {
            return false;
        }

        // Chequea si la nueva locacion es mas precisa o menos precisa
        int diferenciaDePrecision = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean esMenosPrecisa = diferenciaDePrecision > 0;
        boolean esMasPrecisa = diferenciaDePrecision < 0;
        boolean esSignificativamenteMenosPrecisa = diferenciaDePrecision > 200;

        // Chequea si la nueva y la antigua locacion son de mismo proveedor
        boolean sonDelMismoProveedor = isSameProvider(newLocation.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (esMasPrecisa) {
            return true;
        } else if (esNueva && !esMenosPrecisa) {
            return true;
        } else if (esNueva && !esSignificativamenteMenosPrecisa && sonDelMismoProveedor) {
            return true;
        }
        return false;
    }

    /** Chequea si son el mismo proveedor */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

}
