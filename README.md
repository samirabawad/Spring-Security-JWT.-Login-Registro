Este proyecto tuvo como fin entender el funcionamiento de Spring Security para la autentificación de usuarios.
Se utilizó JWT (JSON WEB TOKENS) para transmitir la informacion de sesion del usuario de manera segura. JWT se generó mediante el algoritmo HS256.

Así también se utilizo el patrón de diseño builder para la creación de los objetos.

Explicación:

Estructura de API REST Sin Spring Security.
En un comportamiento normal de APIREST, el usuario hace una solicitud a un endpoint (URL), que se envía a nuestra API mediante una solicitud HTTP.
La peticion HTTP, pasa por la capa de controlador, donde accede a la capa de servicio y desde ella accede a la capa de acceso a datos (interfaz de repositorio o DAO). Desde la capa de acceso a datos, se accede al modelo y devuelve una respuesta.
La respuesta se devuelve en mismo orden, pasando por la capa de datos, servicio y controlador, para ser visto por el usuario.


![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/5d324d2b-888c-408a-ade0-4a5c91b7e27c)



Spring Security:

PROCESO DE AUTENTICACION.
Funcionamiento.
Es un framework de Spring que aporta un conjunto de clases que permite que la autenticación se realice mediante nombre de usuario y contraseña.
Este proceso se realiza antes del acceso a los endpoints del controlador: 
![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/60ff9df9-e601-4bb5-b2ac-d9c80e7a993d)




Para utilizar los servicios de autentificación, es necesario configurar un filtro, junto con AuthenticationProvider.
Si un usuario inicia el proceso de autentificacion, crea un objeto Authentication con elementos principales y credenciales.
Si se realiza la autentificacion, se crea un objeto UsernamePasswordAuthenticationToken.


AuthenticationManager.
Una vez obtenido el objeto Authentication, se envia al AuthenticationManager, donde se realiza una comprobacion del contenido del objeto con las credenciales esperadas (nombre y contraseña).
Si la comprobacion es exitosa, se añade al objeto Authentication las autorizaciones asociadas a esa identidad (roles).
AuthenticationManager es un Bean de tipo ProviderManager (este es un gestor de autenticacion por defecto de Spring).

AuthenticationProvider.
Una vez que los detalles de autenticación se recogen en el agente de usuario, un objeto "solicitud de autenticación" se construye y se presenta a una AuthenticationProvider.
Este es el responsable de tomar el objeto y decidir si es valido o no.
Si es valido, pone la autenticacion en el SecurityContextHolder. De lo contrario, rechaza la solicitud y muestra un mensaje de error.

DaoAthenticationProvider.
Es una interfaz para acceder a los datos almacenados en la base de datos.
Para evitar que se acceda directamente al contenido de la base datos, se puede configurar el DAO mediante la interfaz de Spring UserDetailsService.


UserDetailsService.
Interfaz de acceso a datos de Spring, que contiene un unico metodo llamado loadUserByUsername().
Mediante este metodo, accede a la informacion del usuario mediante su nombre de usuario.



PROCESO DE FILTROS.
Aca se definen a que recursos tiene acceso el usuario, segun sus autorizaciones.



Proceso de Spring boot:
![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/1332e8a3-130b-40d0-b14c-cf52b415461f)







