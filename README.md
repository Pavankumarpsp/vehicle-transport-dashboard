# 🚚 Vehicle Transport Dashboard

A full-stack web application for managing vehicle transport records through an interactive dashboard. It supports Excel-based bulk uploads, vehicle search, record editing, deletion, and dashboard statistics.

## ✨ Features

- **Excel Upload:** Import multiple vehicle records using an `.xlsx` file.
- **Download Template:** Download a predefined Excel template with the required columns.
- **Vehicle Management:** View and manage transport records in a centralized table.
- **Search Vehicles:** Find records by vehicle number.
- **Edit Records:** Update vehicle and transport details.
- **Delete Records:** Remove unwanted records.
- **Dashboard Statistics:** View the total number of vehicle records.
- **MongoDB Integration:** Store vehicle records in a database.
- **Responsive UI:** Access the dashboard through a React-based interface.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | REST API and business logic |
| Spring Data MongoDB | Database integration |
| MongoDB | Data storage |
| React.js | Frontend user interface |
| Vite | Frontend development server |
| Apache POI | Excel file processing |
| Maven | Backend dependency management |
| Git & GitHub | Version control |

## 📁 Project Structure

```text
vehicle-dashboard/
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   │   ├── EditModal.jsx
│   │   │   ├── Header.jsx
│   │   │   ├── SearchFilter.jsx
│   │   │   ├── StatsCard.jsx
│   │   │   ├── UploadSection.jsx
│   │   │   └── VehicleTable.jsx
│   │   ├── services/
│   │   │   └── vehicleService.js
│   │   ├── App.jsx
│   │   ├── App.css
│   │   └── main.jsx
│   └── package.json
├── src/
│   └── main/
│       ├── java/com/vehicle/vehicle_dashboard/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── model/
│       │   ├── repository/
│       │   ├── service/
│       │   └── util/
│       └── resources/
│           └── application.properties
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 📋 Excel Template

The uploaded Excel file must contain these seven columns in the specified order:

1. Vehicle Number
2. Driver Name
3. Driver Phone Number
4. Transporter Name
5. Transporter Email
6. Delivery Address
7. Plant Code

The application validates the headers before processing the uploaded records.

**Note:** A vehicle may appear in multiple records because the same vehicle can complete different trips. Each trip should be identified and managed as a separate record.

## ⚙️ Prerequisites

Install the following before running the project:

- Java Development Kit (JDK 21 recommended)
- Maven, or use the included Maven Wrapper
- Node.js and npm
- MongoDB Community Server
- Git

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Pavankumarpsp/vehicle-transport-dashboard.git
cd vehicle-transport-dashboard
```

### 2. Configure MongoDB

Ensure MongoDB is running locally.

Configure `src/main/resources/application.properties`:

```properties
spring.application.name=vehicle-dashboard
spring.mongodb.uri=mongodb://localhost:27017/vehicle_dashboard
server.port=8080
```

If your Spring Boot version uses a different MongoDB connection property, use the property supported by your installed version.

### 3. Run the Spring Boot backend

Open a terminal in the project root:

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

The backend runs at:

`http://localhost:8080`

### 4. Run the React frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

Open the local URL displayed by Vite in your terminal, usually:

`http://localhost:5173`

## 🔗 API Endpoints

Base URL: `http://localhost:8080/api/vehicles`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/vehicles` | Retrieve all vehicle records |
| GET | `/api/vehicles/{id}` | Retrieve a record by ID |
| GET | `/api/vehicles/number/{vehicleNumber}` | Retrieve by vehicle number |
| GET | `/api/vehicles/search?vehicleNumber=KA01` | Search vehicle records |
| GET | `/api/vehicles/count` | Get the total record count |
| POST | `/api/vehicles/upload` | Upload an Excel file |
| GET | `/api/vehicles/template` | Download the Excel template |
| PUT | `/api/vehicles/{id}` | Update a record |
| DELETE | `/api/vehicles/{id}` | Delete a record |

The update endpoint assumes the backend implements the `PUT` operation.

## 🗄️ Database

- Database: `vehicle_dashboard`
- Collection: `vehicle_records`

Each vehicle record stores:

- Vehicle number
- Driver name and phone number
- Transporter name and email
- Delivery address
- Plant code

MongoDB assigns a unique document ID to each record.

## 🔒 Security Notes

- Do not commit database passwords, credentials, or API keys.
- Keep sensitive configuration in environment variables when deploying.
- Restrict CORS origins before production deployment.
- Configure authentication and authorization before exposing management APIs publicly.

## 🔮 Future Improvements

- Trip history and trip status tracking
- Excel export
- Pagination and advanced filtering
- Role-based access control
- Deployment to a cloud platform
- Automated tests and CI/CD

## 👨‍💻 Author

**Pavankumar P S**

GitHub: [Pavankumarpsp](https://github.com/Pavankumarpsp)

---

⭐ If you find this project useful, consider giving the repository a star.
