# Microservicio de Gestión de Usuarios con Spring Boot

1. [Introducción](#introducción)
2. [Requisitos Previos](#requisitos-previos)
3. [Configuración del Proyecto](#configuración-del-proyecto)
4. [Ejecución del Microservicio](#ejecución-del-microservicio)
5. [Uso del Microservicio](#uso-del-microservicio)
6. [Endpoint signUp](#endpoint-signup)
8. [Documentación](#documentación)
    - [Contrato Swagger](#contrato-swagger)
    - [Diagrama de Componentes](#diagrama-de-componentes)
    - [Diagrama de Secuencias](#diagrama-de-secuencias)
9. [Ejecutar Pruebas](#ejecutar-pruebas)

## Introducción

Este proyecto es parte de la prueba técnica para el puesto de Lider de integración en NTT Data para el cliente BCI en Chile, desarrollado con Spring Boot 2.5.14, Gradle 7.4 y Java 8 para la gestión de usuarios.

## Requisitos Previos

Asegúrate de tener instalado lo siguiente antes de ejecutar el microservicio:

- Java 8
- Gradle 7.4

## Configuración del Proyecto

1. Clona este repositorio:

    ```bash
    git clone https://github.com/jessy-aguilera/java-bci-ntt.git
    cd prueba-java-bci
    ```

2. Construye el proyecto con Gradle:

    ```bash
    ./gradlew build
    ```

## Ejecución del Microservicio

Para ejecutar el microservicio, utiliza el siguiente comando:

```bash
./gradlew bootRun
```
El servicio estará disponible en http://localhost:8080.

## Uso del Microservicio
A continuación, se describen los métodos disponibles:

- POST /sign-up: Realiza el registro de un usuario.

## Endpoint signUp
Este método sirve para realizar el registro de un usuario en la base de datos, este método valida si el usuario ya existe

Ejemplo de solicitud:

```shell
curl --location 'localhost:8080/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Jessy",
    "email": "jessy.aguilera.lopez1@gmail.com",
    "password": "a2asfGfdfdf4",
    "phones": [
        {
            "number": 20570717,
            "citycode": 9,
            "countrycode": "+56"
        }
    ]
}'
```

Ejemplo de respuesta:
```json
{
   "id": "130feef0-917c-4776-a54e-4c63f6deb1c2",
   "created": "jul 09, 2024 01:59:23 PM",
   "modified": "jul 09, 2024 01:59:23 PM",
   "lastLogin": "jul 09, 2024 01:59:23 PM",
   "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqZXNzeS5hZ3VpbGVyYS5sb3BlejFAZ21haWwuY29tIiwiZXhwIjoxNzIwNjE5OTYzfQ.ZoiCdLpOdNqbHClLFttIe0QVDFpcd5cKsnf0sOGS-fW1MmYn4hj2KLQ9xNZ3kQY-",
   "isActive": true,
   "name": "Jessy Aguilera",
   "email": "jessy.aguilera.lopez1@gmail.com",
   "password": "$2a$12$DGNa2fbVfY4ntK4P3fiI7OiSV/fyoyDBqPQW3omBupUm5DNDs/Jty",
   "phones": [
      {
         "number": "20570717",
         "citycode": "9",
         "countrycode": "56"
      }
   ]
}
```

## Documentación
### Contrato Swagger
En el contrato Swagger puedes encontrar los endpoint definidos y las operaciones disponibles en este microservicio. 
- Puedes encontrar el contrato Swagger [aquí](https://github.com/jessy-aguilera/java-bci-ntt/blob/master/docs/swagger.yml) 

### Diagrama de Componentes
El diagrama de componentes proporciona una visión general de la arquitectura del microservicio y sus componentes. 
- Puedes encontrar el diagrama [aquí](https://github.com/jessy-aguilera/java-bci-ntt/blob/master/docs/diagrama-componentes.png)

### Diagrama de Secuencias
El diagrama de secuencias ilustra la interacción entre los diferentes componentes durante un proceso específico.
- Puedes encontrar el diagrama de secuencias de registro de usuario [aquí](https://github.com/jessy-aguilera/java-bci-ntt/blob/master/docs/diagrama-secuencia-registro.png)

## Ejecutar Pruebas
Para ejecutar las pruebas, utiliza el siguiente comando:

```bash
./gradlew test
```
Esto ejecutará todas las pruebas unitarias en el proyecto.

Proyecto realizado por [<u>__Jessy Aguilera__</u>](https://www.linkedin.com/in/jessyaguilera/)

[![GitHub Profile](https://img.shields.io/badge/GitHub-jessy-green?style=flat&logo=github)](https://github.com/jessy-aguilera)