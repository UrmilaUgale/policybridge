# Patient Admission Application

## Overview

This project is a Spring Boot web application that provides a hospital patient admission form and an API backend for storing submitted patient admission data into a MySQL database. The application serves an HTML form that collects extensive patient and billing data, validates it, and stores it in the database.

---

## Features

- Serves a styled Patient Admission HTML form at `http://localhost:8080/`
- Supports submission of form data to backend API endpoint `/admitPatient` (POST)
- Validation of submitted data using Java Bean Validation (JSR-380)
- Persists patient admission details into MySQL database with Spring Data JPA
- Structured Java code with Controller, Service, Repository, and Entity layers
- Automatic database schema update on startup (DDL auto update)
- Suitable for running and development inside IntelliJ IDEA

---

## Prerequisites

- **Java Development Kit (JDK) 17** or higher installed
- **MySQL Server** running locally or accessible from your machine
- **IntelliJ IDEA** (Community or Ultimate Edition)
- **Maven** build tool (comes bundled in IntelliJ)

---

## Database Setup

1. Start your MySQL server.
2. Create a database called `patientdb`:

   