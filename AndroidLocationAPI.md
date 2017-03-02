# Android Location

Conocer la ubicación del usuario permite a la aplicación brindar una mejor información al usuario. Para esto, Android nos permite utilizar distintos proveedores de ubicación como el GPS y el de Red (Network). 
El proveedor de GPS es mas acertado pero tiene limitaciones como el hecho de tener que usarse al aire libre, consumir mas batería y tener un tiempo más largo de respuesta. Mientras que el proveedor de red puede usarse dentro o fuera de algún lugar, responde relativamente rápido, usa menos batería pero es menos preciso. Se pueden usar ambos a la vez o uno solo.

##Desafíos al momento de determinar la ubicación del usuario
Obtener la ubicación del usuario puede convertirse en una tarea complicada. Hay algunas razones por las que se puede recibir información de ubicación errónea o poco precisa. Algunas de estas causas son las siguientes:

 - Multiples fuentes de ubicación
 GPS, Torres de señal móvil y WiFi pueden proveer información de ubicación. Determinar en cual confiar es una cuestión de comparar precisión, velocidad y eficiencia en la batería.
 - Movimiento del usuario
 El usuario puede estar en constante movimiento por lo que es necesario re-estimar la ubicación bastante seguido.
 - Variación de la precisión
 Una ubicación obtenida hace 10 segundos de algún proveedor puede ser mas precisa que una nueva ubicación obtenida de otro o del mismo proveedor.

Estos detalles pueden hacer difícil una lectura confiable de la ubicación del usuario.

##Cómo obtener actualizaciones de la ubicación del usuario?

Para obtener una ubicación es necesario indicar que se desea recibir actualizaciones de ubicación del `LocationManager` llamando al método `requestLocationUpdates()` y pasando un `LocationListener`. Este `LocationListener` implementará ciertos métodos que el `LocationManager` llama cuando el usuario cambia de ubicación o cuando el estatus del servicio ha cambiado.
El siguiente código muestra como definir un `LocationListener` y solicitar actualizaciones de ubicación:

```java
LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

LocationListener locationListener = new LocationListener() {
    public void onLocationChanged(Location location) {
      makeUseOfNewLocation(location);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public void onProviderEnabled(String provider) {}

    public void onProviderDisabled(String provider) {}
  };

location updates
locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
```

El primer parámetro del `requestLocationUpdates()` es el proveedor de la información. Este puede tomar el valor de `NETWORK_PROVIDER` o de `GPS_PROVIDER`. Es posible manejar el intervalo de actualización con el segundo y tercer parámetro (El segundo indica el periodo de tiempo entre cada actualización y el tercero el mínimo cambio en distancia entre cada actualización). El último parámetro es el `LocationListener`.

###Permisos de usuario
Para poder obtener información de la ubicación del usuario es necesario especificar los respectivos permisos en el manifest:

 - `ACCESS_COARSE_LOCATION` Es necesario cuando usamos como proveedor de información de ubicación al `NETWORK_PROVIDER`.
 - `ACCESS_FINE_LOCATION` Es necesario cuando usamos como proveedor de información de ubicación al `GPS_PROVIDER`. Este permiso contiene implícitamente al `ACCESS_COARSE_LOCATION`.

###Última ubicación conocida
El tiempo que toma al location listener recibir la primera ubicación normalmente es bastante largo como para que un usuario pueda esperar. Hasta que una mejor ubicación pueda llegar, se puede utilizar una ubicación guardada en el dispositivo llamando al método `getLastKnownLocation(String)`.

```java
String locationProvider = LocationManager.NETWORK_PROVIDER;
// O usa LocationManager.GPS_PROVIDER

Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
```

###Detener las actualizaciones de ubicación
Es muy importante que no olvidemos detener las actualizaciones de ubicación cuando no las necesitemos más, de lo contrario estaremos drenando la batería del dispositivo.
```java
locationManager.removeUpdates(locationListener);
```

##Referencias

 - https://developer.android.com/guide/topics/location/strategies.html
