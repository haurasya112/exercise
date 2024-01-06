## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Running the Application](#running-the-application)
- [Restoring the Database](#restoring-the-database)

## Introduction

This application serves as one of the technical evaluation stages, allowing users to perform various transactions seamlessly. 

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17
- Spring Boot 3
- Database PostgreSQL
- Maven

## Running the Application

Follow these steps to run the application locally:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/haurasya112/luna-be-test.git
   ```

2. **Navigate to the project directory:**

   ```bash
   cd your-repo
   ```

3. **Build and run the application:**

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run

   Open a web browser and go to [http://localhost:8080](http://localhost:8080) to access the application.

## Restoring the Database

**Backup Database:**

The backed-up database is available in this repository with the name db-luna.sql.

To restore the database, follow these steps:

1. **Restore Database:**

   Run the following command to restore the database.

   ```bash
   psql -U yourusername -d yourdatabase < db-luna.sql
   ```
