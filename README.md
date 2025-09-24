# CRUD Properties - Real Estate Manager

This project implements a **CRUD (Create, Read, Update, Delete)** system for managing real estate properties.  
It is developed with **Spring Boot** on the backend and a **lightweight HTML + JavaScript frontend** (embedded in the same application).  
Data persistence is handled with **MySQL Server** deployed on another AWS EC2 instance.

---

## Sample workshop video
In this video, we can see how the backend is deployed and how the REST APIs for create, read, update, and delete operations are tested. It also shows how this looks in the MySQL database, since we are managing them in different instances.

You can watch the demo video here: [Workshop Video](./WorkShop%20VIdeo.mp4)



## System Architecture

The solution consists of three main layers:

1. **Frontend + Backend (Spring Boot)**  
   - Both are packaged in the same application (`app.jar`).
   - The backend exposes REST endpoints for CRUD operations on properties.
   - The frontend is an `index.html` (with JavaScript) that consumes these endpoints via **Fetch API**.
   - The application runs on port **8080**.

2. **Database (MySQL)**  
   - Deployed on a **separate EC2 instance** for greater modularity.
   - Contains the `properties` table with the following attributes:
     - `id` (PK, auto-increment)
     - `address`
     - `price`
     - `size`
     - `description`
     - `created_at`
     - `updated_at`
   - Communication between backend and DB is done through port **3306** (MySQL).

3. **AWS Networking & Security**  
   - **Security Groups** configured to allow:
     - **22 (SSH)** → only from developer's IP.
     - **3306 (MySQL)** → only from backend's private IP.
     - **8080 (App)** → open to `0.0.0.0/0` (for access from any browser).  
   - The Spring Boot application listens on `0.0.0.0:8080`, allowing external access.
   - On Ubuntu, it was validated that the firewall (`ufw`) allows port 8080.

---

## Technologies Used

- **Backend:** Java 17 + Spring Boot 3.2.5  
- **Frontend:** HTML5, JavaScript (Fetch API)  
- **Database:** MySQL Server 8.x  
- **ORM:** Hibernate + Spring Data JPA  
- **Infrastructure:** AWS EC2 (Ubuntu Server 22.04)  
- **Connection:** HikariCP (connection pool)  

---

## Project Structure

```plaintext

/src/main/java/eci/edu/co/
├── App.java # Main Spring Boot class
├── controller/
│ └── PropertyController.java # REST endpoints
├── model/
│ └── Property.java # JPA entity
├── repository/
│ └── PropertyRepository.java # JPA repository
└── service/
└── PropertyService.java # Business logic

/src/main/resources/
├── application.properties # DB configuration
└── static/
├── index.html # HTML + JS frontend
└── styles.css # CSS stylesheet

```

## REST Endpoints

- `POST /api/properties` → Create property  
- `GET /api/properties` → List all properties  
- `GET /api/properties/{id}` → Get property by ID  
- `PUT /api/properties/{id}` → Update property  
- `DELETE /api/properties/{id}` → Delete property  

## Database Configuration

In the database EC2 instance, the following table was created:

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

## AWS EC2 Deployment

### Backend + Frontend

1. Copy the .jar to the instance:

    scp -i key.pem app.jar ubuntu@<BACKEND_PUBLIC_IP>:/opt/propertyapp/

2. Run the app in background:

    * cd /opt/propertyapp
    * nohup java -jar app.jar > app.log 2>&1 &

### Database

    * MySQL installation and configuration on another EC2 instance.
    * Opening port 3306 in Security Group only for backend access.

### Networking

    * Backend Security Group

        Port 8080 open to 0.0.0.0/0.

    * Database Security Group

        Port 3306 open only to backend's private IP.

    * General Security Group

        Port 22 restricted to developer's IP for SSH.


## Author

Juan David Rodriguez Rodriguez

## Extra Information

There is a file named Informe.pdf, which is the workshop report.
