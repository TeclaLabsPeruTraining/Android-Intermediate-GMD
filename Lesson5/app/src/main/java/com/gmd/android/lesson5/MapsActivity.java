package com.gmd.android.lesson5;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //obtener la notificacion cuando el mapa este listo
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipular el mapa una vez que esta disponible
     * Este metodo es disparado una vez que el mapa esta listo para usarse
     * Aqui es donde podemos agregar markers o lineas, agregar listeners o mover la camara. Es este caso
     * Solo estamos agregando un marker en la plaza san martin
     * Si Google Play Services no esta instalado en el device, el usuario sera conducido a la instalacion de este
     * dentro de un fragmento. Este metodo solo se disparara una vez que el usuario tenga instalado
     * Google Play Services y regrese a la aplicacion
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng plazaSanMartin = new LatLng(-12.051700, -77.034497);
        mMap.addMarker(new MarkerOptions().position(plazaSanMartin).title("Plaza San Martin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(plazaSanMartin, 15));
    }
}
