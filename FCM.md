
### Firebase Cloud Messaging ( FCM )

## Configuración Previa

1. Crear un proyecto en Google API Console (basta con tener un cuenta de gmail ) [https://console.developers.google.com/](https://console.developers.google.com/) . 
  
  - Crear un nuevo proyecto
  
  - Agregar el API "Google Cloud Messaging"
  
  - Ir a credenciales y crear "Clave de API"
  
  - Guardar el id del proyecto y el key generado
  
2. Asociar el proyecto anterior en la consola de Firebase [https://console.firebase.google.com](https://console.firebase.google.com)

  - Importamos un proyecto de Google API Console o creamos un nuevo proyecto
  
  - Agregamos una App Android y seguimos los pasos indicados
  
  - Luego vamos a la configuración del proyecto y seleccionamos "MENSAJERIA EN LA NUBE"
  
  - Guardamos el "ID del remitente" y la "Clave de servidor heredada" . Esto nos va servir configurar del lado del servidor el envio de notificaciones.
  
  - Tambien podemos usar la herramienta "Notificaciones" para enviar un notificación a un cliente.
  
  
3. Configuración del lado del servidor

  - En este caso usaremos el API Rest de Backendless y la función de mensajería. La herramienta de mensajeria te permite configurarlo con FCM, donde te pide el "ID de remitente" y la "Clave de servidor Heredada"
  
  - Revisar la documentación de backendless, luego usar el API Rest  para registrar un dispositivo , enviar un notificación, etc etc [https://backendless.com/documentation/messaging/rest/messaging_overview.htm](https://backendless.com/documentation/messaging/rest/messaging_overview.htm)
  
  
## Cliente Android


## Ejemplo 
