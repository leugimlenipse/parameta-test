# Prueba de conocimiento java - Parameta

## Características

- Servicio SOAP para registro de objetos de tipo Empleado.
- Servicio REST para recibir peticiones con objetos de tipo empleado, realizar validaciones y consumir servicio SOAP.
- Soporte de documentación haciendo uso de Swagger para servicio REST.
- Base de datos MySQL en instancia de AWS lightsail usada por los servicios creados.

## Tecnologías y herramientas usadas

- **Java 17**
- **Spring Boot 3.3.5**
  - Spring Web
  - Spring Data JPA
  - Spring Web Services
- **MySQL**- uso de base de datos remota en instancia de AWS Lightsail.
- **Lombok** - reducción de código mediante anotaciones.
- **Mapstruct** - reducción de código mediante uso de mappers.
- **JUnit** - pruebas unitarias

## Prerequisitos

- **Java 17** or mayor
- **Maven**
- **Postman**
- **Docker** - Docker compose (opcional)

## Recomendación antes de preparar el proyecto

Si no se quiere ejecutar el proyecto usando contenedores de docker, se deben sobreescribir los valores de las variables en los archivos ***application.properties*** de cada aplicación, ya que están configuradas para usar variables de entorno. Los valores de las variables definidas en los archivos .properties se pueden encontrar dentro del archivo ***docker-compose*** para cada aplicación respectivamente.

## Preparación del proyecto - Automático

### Sin docker via script

Para ejecutar el proyecto sin el uso de docker por medio del script de inicio, sólo se necesita abrir una terminal (linux o wsl) en el directorio raiz del repositorio y ejecutar los comandos:

```bash
chmod +x run-apps.sh
./run-apps.sh
```

### Con docker

Para ejecutar el proyecto usando docker, se debe ejecutar el script de construcción de las apps y luego construir los contenedores haciendo uso de docker compose

```bash
chmod +x build-apps.sh
./build-apps.sh

docker compose up --build
```

## Preparación del proyecto - Manual

Se recomienda abrir cada proyecto en una ventana de un IDE como IntelliJ o una terminal y ejecutar los comandos a continuación para cada app.

* **Servicio REST:** la app se ejecuta en el puerto por defecto (8080) con ruta ***/restservice***
* **Servicio SOAP:** la app se ejecuta en el puerto 8081 con ruta ***/soapservice***

1. **Construir la app**:

   ```bash
   mvn clean install


   si recibe un error de tests, intente ejecutar de nuevo saltando los tests:

   mvn clean install -Dmaven.test.skip=true
   ```
2. **Ejecutar la aplicación**:

   ```bash
   mvn spring-boot:run

   ```

## Uso

### Base URL

Los servicios pueden ser probados directamente desde la interfaz de swagger mediante *http://localhost:8080/restservice/swagger-ui/index.html* (se debe tener ambos servicios en ejecución).

También pueden ser consumidos desde Postman mediante las siguientes URLs:

**POST - *http://localhost:8080/restservice/api/v1/employee/register***

Por medio de peticiones POST se puede enviar el cuerpo del mensaje con la representación del objeto de empleado a registrar en formato JSON, como se muestra a continuación:

```
{
    "names": "John",
    "lastNames": "Doe",
    "documentType": "CC",
    "documentNumber": "1234567890",
    "birthDate": "2006-11-10",
    "hiringDate": "2024-11-10",
    "position": "DEVELOPER",
    "salary": 1650.50
}
```

**GET - *http://localhost:8080/restservice/api/v1/employee/register***

Por medio de peticiones GET a la misma ruta, se puede enviar la misma representación del objeto usando parámetros de la petición:

```
http://localhost:8080/restservice/api/v1/employee/register?names=checo&lastNames=perez&documentType=CC&documentNumber=112095&birthDate=2006-11-09&hiringDate=2021-02-15&position=DEVELOPER
```

Cada petición, de ser procesada correctamente, devolverá el mismo objeto con campos adicionales de id del empleado registrado, edad y tiempo de vinculación a la compañía. Ejemplo:

```
{
    "id": 5,
    "names": "John",
    "lastNames": "Doe",
    "documentType": "CC",
    "documentNumber": "1234567890",
    "birthDate": "2006-11-10",
    "hiringDate": "2024-11-10",
    "position": "DEVELOPER",
    "salary": 1650.50,
    "employmentTime": "5 years, 2 months and 3 days",
    "employeeAge": "27 years, 8 months and 10 days"
}
```

De ocurrir un error de validación sobre los atributos del objeto, el servicio arrojará una respuesta HTTP 400 o 409 con su respectivo mensaje de error.

## Desarrollado por Miguel Espinel
