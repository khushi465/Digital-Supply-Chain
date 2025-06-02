# 📦 Digital Supply Chain Tracker

A Spring Boot-based simulation project that digitally tracks and manages supply chain activities — from supplier to transporter to warehouse to retailer — ensuring visibility, transparency, and timely alerts.

---

## 🌐 Domain
**Logistics / Manufacturing / Retail**

---

## 🎯 Objectives

- Digitally track and monitor items across the supply chain.
- Record item statuses at every checkpoint.
- Provide role-based access to different users.
- Offer real-time visibility with reports and dashboards.

---

## 🧱 Tech Stack

| Layer        | Technology                    |
|--------------|-------------------------------|
| Framework    | Spring Boot                   |
| Security     | Spring Security + JWT (optional) |
| Persistence  | Spring Data JPA               |
| Database     | MySQL                         |
| Build Tool   | Maven or Gradle               |
| Utilities    | Lombok, ModelMapper (optional)|
| Testing      | JUnit                         |
| Documentation| Swagger (springdoc-openapi)   |
| Optional     | JavaMailSender, Kafka, @Scheduled |

---

## 🧩 Key Modules

1. **User & Role Management**
2. **Item & Shipment Tracking**
3. **Checkpoints & Event Logs**
4. **Reports**

---

## 🔐 Roles & Access

| Role             | Access Description                                 |
|------------------|-----------------------------------------------------|
| **Admin**        | Full access, manage users and roles, view reports   |
| **Supplier**     | Add items, create shipments                         |
| **Transporter**  | Update shipment and checkpoint status               |
| **Warehouse Manager** | Receive goods, confirm delivery            |

---

## 🗃 Entity Overview

- **User**: `id`, `name`, `email`, `password`, `role` (`ADMIN`, `SUPPLIER`, `TRANSPORTER`, `MANAGER`)
- **Item**: `id`, `name`, `category`, `supplierId`, `createdDate`
- **Shipment**: `id`, `itemId`, `fromLocation`, `toLocation`, `expectedDelivery`, `currentStatus`, `assignedTransporter`
- **CheckpointLog**: `id`, `shipmentId`, `location`, `status`, `timestamp`

---

## 🔁 REST API Endpoints

### 🔐 AuthController
- `POST /api/auth/register`
- `POST /api/auth/login`

### 👤 UserController (Admin)
- `GET /api/users`
- `PUT /api/users/{id}/role`

### 📦 ItemController (Supplier/Admin)
- `POST /api/items`  
- `GET /api/items`  
- `GET /api/items/{id}`

### 🚚 ShipmentController
- `POST /api/shipments`  
- `PUT /api/shipments/{id}/assign`  
- `GET /api/shipments`  
- `GET /api/shipments/{id}`  
- `PUT /api/shipments/{id}/status`

### 📍 CheckpointLogController
- `POST /api/checkpoints`  
- `GET /api/checkpoints/shipment/{id}`

### 📊 ReportController
- `GET /api/reports/delivery-status`
- `GET /api/reports/delayed-shipments`

---

## 🧪 Example Workflow

1. Supplier registers an item and creates a shipment.
2. Transporter updates shipment status and checkpoints.
3. System logs each event and detects delivery delays.
4. Reports are generated accordingly.

---

## 🖼 ER Diagram & Class Diagram
ER Diagram
![ER Diagram](https://github.com/khushi465/Digital-Supply-Chain/blob/main/SupplyChain/src/main/java/com/supplytracker/Diagrams/ER%20Diagram.jpg)
Class Diagram
![Class Diagram](https://github.com/khushi465/Digital-Supply-Chain/blob/main/SupplyChain/src/main/java/com/supplytracker/Diagrams/Class%20Diagram.jpg)
## ⚙ Sample Configuration (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://192.168.61.243:3306/digitalsupplytracker
spring.datasource.username=root
spring.datasource.password=Root

server.port=8081

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
