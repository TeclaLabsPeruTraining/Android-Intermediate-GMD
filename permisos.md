#Cómo declarar permisos

Cada app de Android se ejecuta en una zona de pruebas con acceso limitado. Si una app necesita usar recursos o información externos a su propia zona de pruebas, debe solicitar el permiso correspondiente. Para declarar que tu app necesita un permiso, debes mencionarlo en el manifest de la app.

Según el grado de confidencialidad del permiso, el sistema podría otorgar el permiso automáticamente o el usuario del dispositivo podría tener que conceder la solicitud. Por ejemplo, si tu app solicita permiso para activar la linterna del dispositivo, el sistema otorga ese permiso automáticamente. Sin embargo, si tu app necesita leer los contactos del usuario, el sistema le solicita aprobar dicho permiso. Según la versión de la plataforma, el usuario otorga el permiso cuando se instala la app (en Android 5.1 y versiones anteriores) o mientras esta se ejecuta (en Android 6.0 y versiones posteriores).

##Determina los permisos que necesita tu app
Mientras desarrollas tu app, debes tener en cuenta los casos en que esta use capacidades que requieran un permiso. Normalmente, una app necesitará permisos siempre que use información o recursos que no cree, o cuando realice acciones que afecten el comportamiento del dispositivo o de demás apps. Por ejemplo, si una app necesita acceder a Internet, usar la cámara del dispositivo o activar o desactivar la conexión Wi-Fi, necesitará los permisos correspondientes. 


##Agregar permisos al manifiesto
Para declarar que tu app necesita un permiso, dispón un elemento <uses-permission> en el manifiesto de tu app como elemento secundario del elemento de nivel superior <manifest>. Por ejemplo, en el manifiesto de una app que necesite enviar mensajes SMS debería incluirse esta línea:
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.example.snazzyapp">

    <uses-permission android:name="android.permission.SEND_SMS"/>
    

    <application ...>
        ...
    </application>
```
</manifest>

El comportamiento del sistema después de declarar un permiso depende del grado de confidencialidad de este último. Si el permiso no afecta la privacidad del usuario, el sistema otorga el permiso automáticamente. Si el permiso puede otorgar acceso a información confidencial del usuario, el sistema solicita al usuario que apruebe la solicitud. 

##Cómo solicitar permisos durante el tiempo de ejecución

A partir de Android 6.0 (nivel de API 23), los usuarios conceden permisos a las apps mientras se ejecutan, no cuando instalan la app. Este enfoque simplifica el proceso de instalación de la app, ya que el usuario no necesita conceder permisos cuando instala o actualiza la app. También brinda al usuario mayor control sobre la funcionalidad de la app; por ejemplo, un usuario podría optar por proporcionar a una app de cámara acceso a esta, pero no a la ubicación del dispositivo. El usuario puede revocar los permisos en cualquier momento desde la pantalla de configuración de la app.

Los permisos del sistema se dividen en dos categorías, normal y riesgoso:

 - Los permisos **normales** no ponen en riesgo la privacidad del usuario de forma directa. Si tu app tiene un permiso normal en su manifiesto, el sistema concede el permiso automáticamente.
 - Los permisos **riesgosos** pueden permitir que la app acceda a información confidencial del usuario. Si tu app tiene un permiso normal en su manifiesto, el sistema concede el permiso automáticamente. Si tienes un permiso peligroso, el usuario debe autorizar explícitamente a tu app.

En todas las versiones de Android, tu app debe declarar los permisos normales y peligrosos que necesita en su manifiesto, como se describe en Declaración de permisos. No obstante, el efecto de esa declaración es diferente según la versión del sistema y el nivel de SDK de destino de tu app:

 - Si el dispositivo tiene Android 5.1 o una versión anterior, o el nivel de SDK de destino de tu app es el 22 o uno inferior: Si tienes un permiso peligroso en tu manifiesto, el usuario debe conceder el permiso cuando instale la app; si no otorga el permiso, el sistema no instalará la app.
 - Si el dispositivo tiene Android 6.0 o una versión posterior, y el nivel de SDK de destino de tu app es el 23 o uno posterior: Los permisos deben estar indicados en el manifiesto de la app, y esta debe solicitar cada permiso riesgoso que necesite mientras la app esté en ejecución. El usuario puede conceder o negar cada permiso y la app puede continuar ejecutándose con capacidades limitadas aun cuando el usuario rechace una solicitud de permiso.


Nota: A partir de Android 6.0 (nivel de API 23), los usuarios pueden revocar permisos desde cualquier app en cualquier momento, aunque la app esté orientada a un nivel de API inferior. Debes probar tu app para verificar que se comporte correctamente cuando no cuente con un permiso necesario, independientemente del nivel de API al que esté orientada tu app.


##Comprobar si existen permisos
Si tu app necesita un permiso riesgoso, debes verificar si tienes ese permiso cada vez que realices una operación que lo requiera. El usuario siempre puede revocar el permiso. Por lo tanto, aunque la app haya usado la cámara el día anterior, no puede suponer que seguirá teniendo ese permiso para el día en curso.

Para comprobar si tienes un permiso, llama al método `ContextCompat.checkSelfPermission()`. Por ejemplo, en este fragmento se muestra la manera de comprobar si la actividad tiene permiso para realizar operaciones de escritura en el calendario:

```java
int permissionCheck = ContextCompat.checkSelfPermission(thisActivity,
        Manifest.permission.WRITE_CALENDAR);
```
Si la app tiene el permiso, el método muestra `PackageManager.PERMISSION_GRANTED` y esta puede continuar con la operación. Si la app no tiene el permiso, el método muestra `PERMISSION_DENIED` y la app debe solicitar permiso al usuario de manera explícita.

##Solicitar permisos
Si tu app necesita un permiso riesgoso indicado en el manifiesto, debe solicitar al usuario que lo otorgue. Android ofrece varios métodos que puedes usar para solicitar un permiso. Cuando llamas a estos métodos, aparece un diálogo de Android estándar que no se puede personalizar.

![permission dialog](https://github.com/TeclaLabsPeruTraining/Android-Intermediate-GMD/blob/Lesson8-AndroidM/images/request_permission_dialog.png)


##Solicitar los permisos que se necesitan
Si tu app todavía no tiene el permiso que necesita, debe llamar a uno de los métodos requestPermissions() para solicitar los permisos correspondientes. Tu app pasa los permisos que necesita y también un código de solicitud de entero que tú especificas para identificar esta solicitud de permiso. Este método funciona de manera asincrónica: realiza la devolución inmediatamente y, cuando el usuario responde al cuadro de diálogo, el sistema llama al método callback de la app con los resultados y pasa el mismo código de solicitud que la app le pasó a requestPermissions().

El siguiente código verifica si la app tiene permiso para leer los contactos del usuario y solicita el permiso si es necesario:

```java
if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {

    ActivityCompat.requestPermissions(thisActivity,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
}
```

Nota: Cuando tu app llama a requestPermissions(), el sistema muestra al usuario un cuadro de diálogo estándar. Tu app no puede configurar ni modificar ese cuadro de diálogo. 

##Controla la respuesta a la solicitud de permisos
Cuando tu app solicita permisos, el sistema muestra al usuario un cuadro de diálogo. Cuando el usuario responde, el sistema invoca el método `onRequestPermissionsResult()` de tu app y le pasa la respuesta del usuario. Tu app debe sobre-escribir ese método para averiguar si se otorgó el permiso. El callback recibe el mismo código de solicitud que le pasaste a `requestPermissions()`. Por ejemplo, si una app solicita acceso a `READ_CONTACTS`, es posible que tenga el siguiente método callback:

```java
@Override
public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
    switch (requestCode) {
        case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
            // Si el pedido es cancelado el array estará vacío
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permiso otorgado

            } else {

                // Permiso denegado, desactivar la funcionalidad.
            }
            return;
        }

    }
}
```

El cuadro de diálogo que muestra el sistema describe el grupo de permisos al que tu app necesita acceder, pero no indica el permiso específico. Por ejemplo, si solicitas el permiso `READ_CONTACTS`, el cuadro de diálogo del sistema simplemente indica que tu app necesita acceder a los contactos del dispositivo. El usuario solo debe otorgar el permiso una vez para cada grupo de permisos. Si tu app requiere otros permisos de ese grupo (que se indican en el manifiesto de tu app), el sistema los concede automáticamente. Cuando solicitas el permiso, el sistema llama a tu método callback `onRequestPermissionsResult()` y pasa `PERMISSION_GRANTED`, de la misma manera en que lo haría si el usuario hubiera aceptado explícitamente tu solicitud a través del cuadro de diálogo del sistema.


Por ejemplo, supón que indicas `READ_CONTACTS` y `WRITE_CONTACTS` en el manifiesto de tu app. Si solicitas `READ_CONTACTS`, el usuario concede el permiso y luego solicitas `WRITE_CONTACTS`, el sistema te otorga de inmediato ese permiso sin interactuar con el usuario.

Si un usuario rechaza una solicitud de permiso, tu app debe tomar una medida adecuada. Por ejemplo, tu app podría mostrar un diálogo en el que se explique por qué no podría realizar la acción solicitada por el usuario para la cual se requiere ese permiso.

Cuando el sistema solicita al usuario que otorgue un permiso, el usuario tiene la opción de indicar al sistema que no solicite ese permiso de nuevo. En ese caso, cuando la app use requestPermissions() para solicitar ese permiso nuevamente, el sistema rechazará la solicitud de inmediato. El sistema llama a tu método callback `PERMISSION_DENIED` y pasa `onRequestPermissionsResult()`, de la misma manera en que lo haría si el usuario hubiera rechazado explícitamente tu solicitud una vez más. Esto significa que cuando llamas a `requestPermissions()`, no puedes suponer que haya existido interacción directa con el usuario.



##Referencias

 - https://developer.android.com/training/permissions/declaring.html?hl=es
 - https://developer.android.com/guide/topics/security/permissions.html?hl=es#normal-dangerous
