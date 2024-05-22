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

1. El request es interceptado por el filtro.
2. La responsabilidad de autentication se delega a AuthenticationManager.
3. AuthenticationManager utiliza los providers dado que estos implementan la logica de autenticacion (en este proyecto se ocupan los providers: DAO Authentication Provider y JDBC UserDetailsManager).
4. El Provider busca el usuario con el UserDetailsService y valida el password usando PasswordEncoder.
5. El resultado de autenticacion es retornado al Filter.
6. Los detalles de la autenticacion son almacenados en el SecurityContext.

   



JWT: JSON WEB TOKEN.
JWT participa en el proceso de atenticacion, verificando la identidad de usuario.
Es importante recalcar que se encarga solo de la autenticacion, no de la autorización.

Una vez el usuario es autenticado, se crea un JWT de acceso. En este proyecto, se utiliza Bearer JWT.
Un token se compone de:
1. HEADER: Este es el tipo de algoritmo a utilizar para la encriptacion. En este proyecto se ocupa HS256.
3. PAYLOAD: Contiene las propiedades, conocidas como claims. Entre ellos están el nombre del usuario.
4. SIGNATURE: Usado para validar que el token es valido y no ha sido modificado. Para ello, utiliza una clave secreta : SECRET_KEY.

#foto sacada de : https://jwt.io/
![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/4dda5039-3366-4d3e-9c95-4336f66dc6d5)



PROCESO DE AUTENTICACION JWT.
1. El request es interceptado por el filtro, el cual verifica si existe o no un JWT.
2. Si no existen token de acceso, se deniega la solicitud HTTP.
3. Si existe un Token de acceso, se dirige a JWTService para extraer el username del token. Luego verifica que el token sea valido.
4. Luego, retorna el username al filtro, el cual lo envia a UserDetailsService. Dentro, mediante el metodo loadUserByUsername() crea el objeto usuario implementando UserDetails.
5. El usuario es devuelto al filtro.
6. Si hay usuario, se verifica si tiene permisos para su solicitud.
7.  Si tiene los permisos, acepta la solicitud y la envia al controlador, donde se genera la respuesta HTTP en formato json al cliente.



PROCESO DE SPRING SECURITY + JWT.

<<<<<<< HEAD
![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/6ced1a24-56a0-401f-9efc-4c7637fc2cdf)

=======
![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/43964be1-1dbd-4eda-955c-252e1f030412)
>>>>>>> master


SPRING SECURITY PARA VARIOS TIPOS DE USUARIO Y ROLES:
En vez de usar el servicio por defecto UserDetailsService de Spring, creamos un servicio que implemente o extienda de este servicio: En este proyecto, lo llamamos CustomUserDetailsService.
En esto, hacemos que el llamado a la funcion loadUserByUsername() en AuthenticationService sea recibido por este nuevo servicio.

Dentro del servicio sobreescribimos la funcion loadUserByUsername(), para que busque en todos los repositorios de nuestros distintos usuarios y dependiende de en que repositorio se escuentre, retorna al usuario correspondiente y se genera el token. Recordar que estos ya vienen diferenciados en sus entidades, por su ROLE (ADMIN, CLIENTE y Empresa). Con esto ya esta lista la funcionalidad del token.

Luego, para las peticiones de registro, se modifica el controlador para generar un endpoint unico para la entidad a registrar. En este proyecto son dos: /register/cliente, /register/empresa.

Luego, se modifica la clase que se envia como formato de peticion. En este proyecto al ser dos entidades al registrar, se generan dos clases para el formato del request:
RegisterRequestCliente, RegisterRequestEmpresa. En este proyecto estan guardadas como objetos DTO.

Se agregan las clases en el controlador, como tipos de request a recibir en los endpoints correspondientes.

<<<<<<< HEAD
![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/54398a91-f383-4d96-930a-6561f7fe4baa)


La estructura del proyecto completo queda de la siguiente forma:

![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/0e7be151-9dce-414c-a339-790586f977a4)
=======
La estructura del proyecto completo queda de la siguiente forma:

![image](https://github.com/samirabawad/Spring-Security-JWT.-Login-Registro/assets/136211595/8b682169-9821-4b4e-abab-2f647237d01b)



SEGUNDO PROCESO: RECUPERACIÓN DE CONTRASEÑA.

En el proceso anterior se realizo la autenticación para los endpoints de login y registro. A continuación se explica como realizar el proceso de autenticación para la recuperación de contraseña con JWT y Spring Security.

Lo primero es definir el endpoint para la recuperación de contraseña, donde el usuario ingresa su username. Este endpoint debe estar declarado como publico, para que cualquier persona pueda acceder a el (en este proyecto, el endpoint se llama /auth/recover).

Luego se verifica si el usuario existe en la base de datos. Si existe, se genera un token en un jwtService, donde se incluye en sus claims el codigo de verificación y su username o algun identificador unico del usuario. Es importante destacar que la SECRET_KEY debe ser distinta para una mayor seguridad. Este token generado es almacenado temporalmente con clave:valor en un localStorage.

Luego, se envía un correo electronico con el token generado, para que el usuario pueda recibir el codigo de verificación. En este proyecto, se guarda el token asociado a su key user en la clase VerificationCodeStorage (quizas podria guardarse solo usuario y codigo), que contendra una lista de todos las claves valor de tokens generados.

Posteriormente se redirige al usuario a un nuevo endpoint publico en donde pueda ingresar el codigo de verificación. Al recibir el codigo, se implementa en un servicio la logica de comparación del codigo recibido con el enviado por correo. Para esto, se le solicita al usuario que ingrese su username, y se busque en el localStorage su token correspondiente. Del token correspondiente se extrae el codigo de verificación y se almacena en una variable, para posteriormente verificar si ambos codigos calzan. Si ambos codigos calzan, se le entrega el token de recuperación.

Se le pide al usuario que ingrese al nuevo endpoint privado, al que podra acceder por que a tiene su token de acceso al endpoint. Es aca donde se le solicita al usuario que ingrese su nueva contraseña. Posteriormente se debe enviar esta contraseña a un servicio correspondiente donde lo envie a la base de datos para hacer el cambio. Luego, se debe hacer expirar el token de recuperación y redirigir el usuario al login. Así tambien, se debe eliminar este claveÑvalor de la lista de localStorage.



>>>>>>> master

