#Ubicación usando los servicios de Google

Los APIs de ubicación disponibles con los servicios de Google Play facilitan el adicionarle la funcionalidad de ubicación a una aplicación. Es preferible usar estos APIs que los que provee el *framework* de Android.

##Preparar el proyecto para usar los servicios de google
Para desarrollar una aplicación que pueda trabajar con los APIs de los servicios de Google Play se necesita configurar el proyecto con el SDK de los servicios de Google Play. Para esto es necesario instalar el SDK de la siguiente manera:

 - Abre Android Studio
 - Abrir el menu Tools y clickear SDK Manager
 - Actualizar el SDK Manager de Android Studio, click en SDK Tools, expandir Support Repository, seleccionar Google Repository y luego click en OK.

Luego de esto debemos agregar las dependencias al proyecto:

 - Abrir el build.gradle del módulo.
 - Agregar la dependencia al bloque de dependencies
```groovie
dependencies {
        compile 'com.google.android.gms:play-services:10.2.0'
    }
```
 - Guardar los cambios y sincronizar el proyecto

##Trabajar con la ultima ubicación conocida
Para obtener la ultima ubicación conocida del usuario debemos usar el proveedor de ubicación *fused*. Este proveedor encapsula la parte técnica y provee una API bastante simple con la cual se puede especificar requerimientos a alto nivel como alta precisión o poco consumo. También optimiza el uso de la batería del dispositivo.

##Conectarnos a los servicios de Google
Para conectarnos al API necesitamos crear una instancia del cliente API de los servicios de Google Play. Dentro del método `onCreate()` creamos la instancia como lo indica el siguiente código:
```java
if (mGoogleApiClient == null) {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
}
```
Para conectar, llama al método `connect()` en el método `onStart()` de la actividad. Para desconectar, llama al método `disconnect()` dentro de método `onStop()`.
```java
protected void onStart() {
    mGoogleApiClient.connect();
    super.onStart();
}

protected void onStop() {
    mGoogleApiClient.disconnect();
    super.onStop();
}
```

##Cómo obtener la última posición conocida del usuario?
Una vez que nos hayamos conectado con el API de los servicios de Google Play, ya podemos conocer la ultima ubicación conocida del dispositivo del usuario. Cuando la aplicación esta conectada a estos podemos usar el método `getLastLocation()` del proveedor fused para obtener la posición. La precisión obtenida de esta llamada está determinada por el permiso que hayamos solicitado en el manifest.
El llamado al `getLastLocation()` debe hacerse en el método onConnected() del cliente de API de Google.
```java
public class MainActivity extends ActionBarActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {
    ...
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }
}
```
El método getLastLocation() devuelve un objeto Location del cual se puede obtener la latitud y longitud del dispositivo de usuario. El objeto obtenido puede ser nulo en algunos casos.


##Configurando las actualizaciones de ubicación

Para configurar las actualizaciones es necesario crear un `LocationRequest`. En el es posible configurar el intervalo de actualización, el máximo intervalo de actualización y la prioridad.

 - Intervalo de actualización: Se configura a través del método setInterval(). Este método especifica el ratio en milisegundos el cual la aplicación prefiere obtener actualizaciones. 
 - Máximo intervalo de actualización: Se configura a través del método setFastestInterval(). Este método especifica el máximo ratio en milisegundos el cual la aplicación puede manejar los updates que recibe.
 - Prioridad: Se configura a través del método setPriority(). Este método sirve para indicar a los servicios de Google que fuente de información de ubicación utilizar.
Los siguientes son los valores soportados:
	 - PRIORITY_BALANCED_POWER_ACCURACY: Utiliza este valor para especificar que se desea un nivel de precision de aproximadamente 100 metros. Este es considerado un nivel de precisión relativamente bajo por lo que consume poca batería.
	 - PRIORITY_HIGH_ACCURACY: Utiliza este valor para solicitar la máxima precisión posible. 
	 - PRIORITY_LOW_POWER: Utiliza este valor para obtener un valor de precisión a nivel de ciudad lo cual es aproximadamente 10 kilómetros.
	 - PRIORITY_NO_POWER: Utiliza este valor si no deseas crear un impacto en el consumo de batería pero a la vez deseas recibir actualizaciones de ubicación cuando sea posible. Con este valor la aplicación no pedirá ninguna actualización pero las recibirá cuando otra aplicación lo haga.
```java
protected void createLocationRequest() {
    LocationRequest mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(10000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
}
```

##Pedir al usuario que cambie la configuración de ubicación
Para verificar si las configuración de la ubicación es apropiada para el request que hemos generado necesitamos verificar el código de estatus del objeto `LocationSettingsResult`. Un estado `RESOLUTION_REQUIRED`indica la configuración debe ser cambiada.
To determine whether the location settings are appropriate for the location request, check the status code from the LocationSettingsResult object. A status code of indicates that the settings must be changed. To prompt the user for permission to modify the location settings, call startResolutionForResult(Activity, int). This method brings up a dialog asking for the user's permission to modify location settings. The following code snippet shows how to check the location settings, and how to call startResolutionForResult(Activity, int).

```java
private void addAndCheckRequest() {
        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdates();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // La configuracion no ha sido satisfactoria pero puede ser arreglada mostrando el siguiente dialogo
                        try {

                            status.startResolutionForResult(GoogleLocationActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignorar el error
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // La configuracion no ha sido satisfactoria y no hay nada que hacer por lo que el dialogo no se mostrara

                        break;
                }
            }
        });
    }
```

##Activar las actualizaciones de ubicación
Para activar las actualizaciones de ubicación es necesario llamar al método requestLocationUpdates utilizando el LocationRequest que creamos previamente.

```java
protected void startLocationUpdates() {        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
```
El tercer parámetro sera un un LocationListener el cual tendremos que implementar para manejar las actualizaciones.

```java
@Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateUI();
    }
```

##Referencias

 - https://developers.google.com/android/guides/setup
 - https://developer.android.com/training/location/index.html
