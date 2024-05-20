Este proyecto tuvo como fin entender el funcionamiento de Spring Security para la autentificación de usuarios.
Se utilizó JWT (JSON WEB TOKENS) para transmitir la informacion de sesion del usuario de manera segura. JWT se generó mediante el algoritmo HS256.

Así también se utilizo el patrón de diseño builder para la creación de los objetos.

Explicación:

API REST Sin Spring Security.
En un comportamiento normal de APIREST, el usuario hace una solicitud a un endpoint (URL), que se envía a nuestra API mediante una solicitud HTTP.
La peticion HTTP, pasa por la capa de controlador, donde accede a la capa de servicio y desde ella accede a la capa de acceso a datos (interfaz de repositorio o DAO). Desde la capa de acceso a datos, se accede al modelo y devuelve una respuesta.
La respuesta se devuelve en mismo orden, pasando por la capa de datos, servicio y controlador, para ser visto por el usuario.

API REST con Spring Security.



