# CRUD Properties - Real Estate Manager

Este proyecto implementa un sistema **CRUD (Create, Read, Update, Delete)** para gestionar propiedades inmobiliarias.  
Está desarrollado con **Spring Boot** en el backend y un **frontend ligero en HTML + JavaScript** (embebido en la misma aplicación).  
La persistencia de datos se maneja con **MySQL Server** desplegado en otra instancia de AWS EC2.

---

## 🚀 Arquitectura del Sistema

La solución está compuesta por tres capas principales:

1. **Frontend + Backend (Spring Boot)**  
   - Ambos se encuentran empaquetados en la misma aplicación (`app.jar`).
   - El backend expone endpoints REST para operaciones CRUD sobre propiedades.
   - El frontend es un `index.html` (con JavaScript) que consume dichos endpoints vía **Fetch API**.
   - La aplicación corre en el puerto **8080**.

2. **Base de Datos (MySQL)**  
   - Desplegada en una **instancia separada de EC2** para mayor modularidad.
   - Contiene la tabla `properties` con los atributos:
     - `id` (PK, autoincremental)
     - `address`
     - `price`
     - `size`
     - `description`
     - `created_at`
     - `updated_at`
   - La comunicación entre backend y DB se realiza a través del puerto **3306** (MySQL).

3. **AWS Networking & Security**  
   - **Security Groups** configurados para permitir:
     - **22 (SSH)** → solo desde la IP del desarrollador.
     - **3306 (MySQL)** → solo desde la IP privada del backend.
     - **8080 (App)** → abierto a `0.0.0.0/0` (para acceso desde cualquier navegador).  
   - La aplicación Spring Boot escucha en `0.0.0.0:8080`, lo que permite acceso externo.
   - En Ubuntu, se validó que el firewall (`ufw`) permite el puerto 8080.

---

## Tecnologías Utilizadas

- **Backend:** Java 17 + Spring Boot 3.2.5  
- **Frontend:** HTML5, JavaScript (Fetch API)  
- **Base de Datos:** MySQL Server 8.x  
- **ORM:** Hibernate + Spring Data JPA  
- **Infraestructura:** AWS EC2 (Ubuntu Server 22.04)  
- **Conexión:** HikariCP (pool de conexiones)  

---

## Estructura del Proyecto

```plaintext

/src/main/java/eci/edu/co/
├── App.java # Clase principal Spring Boot
├── controller/
│ └── PropertyController.java # Endpoints REST
├── model/
│ └── Property.java # Entidad JPA
├── repository/
│ └── PropertyRepository.java # Repositorio JPA
└── service/
└── PropertyService.java # Lógica de negocio

/src/main/resources/
├── application.properties # Configuración DB
└── static/
└── index.html # Frontend HTML + JS

```

## Endpoints REST

- `POST /api/properties` → Crear propiedad  
- `GET /api/properties` → Listar todas las propiedades  
- `GET /api/properties/{id}` → Consultar propiedad por ID  
- `PUT /api/properties/{id}` → Actualizar propiedad  
- `DELETE /api/properties/{id}` → Eliminar propiedad  

## Configuración de la Base de Datos

En la instancia EC2 de la base de datos se creó la tabla:

```sql
CREATE TABLE properties (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  address VARCHAR(255) NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  size INT NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

```

## Despliegue en AWS EC2

### Backend + Frontend

1. Copiar el .jar a la instancia:

    scp -i key.pem app.jar ubuntu@<BACKEND_PUBLIC_IP>:/opt/propertyapp/

2. Ejecutar la app en segundo plano:

    * cd /opt/propertyapp
    * nohup java -jar app.jar > app.log 2>&1 &

### Base de Datos

    * Instalación y configuración de MySQL en otra instancia EC2.
    * Apertura del puerto 3306 en el Security Group solo para el backend.

### Networking

    * Security Group Backend

        Puerto 8080 abierto a 0.0.0.0/0.

    * Security Group Database

        Puerto 3306 abierto solo para la IP privada del backend.

    * Security Group General

        Puerto 22 restringido a la IP del desarrollador para SSH.


## Autor

Juan David Rodriguez Rodriguez