Pequeño ejemplo para ver cómo se pueden solicitar permisos exclusivos de android y de PWA desde un aplicación React con Capacitor.

	1. Primero ejecutamos "npx create-react-app react_capacitor_android_permissions" para crear un proyecto React desde 0. Este comando ya no se suele utilizar pero nos puede servir perfectamente para aprender el uso de los permisos.

	2. Instalamos las dependencias necesarias con "npm install @capacitor/core @capacitor/cli @capacitor/android". Inicializamos Capacitor con "npx cap init" y añadimos android con "npx cap add android". Hacemos el build con "npm run build" y sincronizamos con "npx cap sync". Cuando queramos ejecutar el proyecto en android haremos "npx cap run android".

	3. Vamos a empezar solicitando un permiso propio de entorno web. Primero creamos la clase "WebGeolocationPermission" que hereda de WebPlugin. En esta clase ponemos el método requestPermission(). En este método primero haremos navigator.permissions.query({name: 'geolocation'}) para comprobar si el permiso "geolocation" ya está aceptado. Si no lo está llamaremos a navigator.geolocation.getCurrentPosition() para solicitar el permiso al usuario.

	4. En App.js ponemos un botón al que asociamos un evento que utilice nuestro plugin para llamar a requestPermission() y un texto debajo donde mostraremos la altitud y latitud. Tras esto hacemos un build y ejecutamos "npm run start" para comprobar que funciona correctamente en el navegador.

	5. Ahora vamos a implementar solicitud de permisos en Android desde una vista React. Primero debemos incluir en el Manifest el permiso para usar la cámara.

	6. Después creamos en el directorio donde se encuentra MainActivity.java un fichero CameraPermissionPlugin.java. La clase CameraPermissionPlugin debe heredar de Plugin y tener una anotación @CapacitorPlugin que incluya el nombre y los permisos.

	7. Es importante que los métodos que queremos exponer a través del plugin tengan la anotación @PluginMethod. Además podemos usar las anotaciones @PermissionCallback y @ActivityCallback si necesitamos un método de callback para comprobar los permisos o para lanzar Activities. 

	8. En MainActivity debemos registrar el plugin antes de la llamada a super.onCreate().

	9. Ahora volvemos al directorio src/ donde se encuentra App.js y creamos un nuevo fichero "PluginBridge.js"que servirá como puente para el plugin entre la parte de Android y la parte de React. Llamamos al método registerPlugin() pasándole el nombre que hemos puesto a nuestro plugin en Android.

	10. Por último importamos el plugin en App.js y utilizamos su método takePhoto() para solicitar el permiso de la cámara.

	11. Debemos tener en cuenta que si nuestro proyecto apunta a una API de nivel 30 o superior tendremos que incluir en el AndroidManifest.xml este código: 
"<queries>
        <intent>
            <action android:name="android.media.action.IMAGE_CAPTURE" />
        </intent>
 </queries>"
