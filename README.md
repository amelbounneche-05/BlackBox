# Boîte Noire

A high-performance event tracking and analysis backend application built with **Java 17**, **Spring Boot 3**, and **MongoDB**.

## Features
- **Event Management**: Record, retrieve, update, and delete events securely.
- **Analytics & Funnels**: Advanced log analysis, error statistics, top users identification, and conversion funnel analysis.
- **Database Optimization**: Custom compound indexes (\{userId: 1, timestamp: -1}\) to eliminate \COLLSCAN\ bottlenecks and ensure high-speed querying.
- **Data Generation**: Automated bulk data generation tool capable of handling 100,000+ events smoothly.
- **Interactive API Documentation**: Fully integrated Swagger UI for seamless endpoint testing.

## Tech Stack
- **Java 17**
- **Spring Boot 3.2.3** (Web, Data MongoDB)
- **MongoDB**
- **Springdoc OpenAPI** (Swagger UI)

## Project Structure
\\\	ext
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── pigeon/
│   │           └── boitenoire/
│   │               ├── controller/  # REST Controllers (API endpoints)
│   │               ├── model/       # Data models & entities
│   │               ├── repository/  # MongoDB repositories & custom queries
│   │               └── service/     # Business logic & data generation
│   └── resources/
│       └── application.properties  # App configurations & port setup
\\\

## Getting Started

### Prerequisites
- Java 17+ installed
- MongoDB running locally
- Maven installed

### Running the Application & Swagger UI
1. Run the application using Maven:
   \\\bash
   mvn spring-boot:run
   \\\
2. Once the application is running (look for \Started BoitenoireApplication\), open your web browser and access Swagger UI on port **8090**:
   > \http://localhost:8090/swagger-ui/index.html\

## License
This project is developed for educational and professional optimization purposes.
