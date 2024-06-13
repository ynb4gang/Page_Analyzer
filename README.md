### Hexlet tests and linter status:
[![Actions Status](https://github.com/ynb4gang/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/ynb4gang/java-project-72/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/dd277cc934657df02c0a/maintainability)](https://codeclimate.com/github/ynb4gang/Page_Analyzer/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/dd277cc934657df02c0a/test_coverage)](https://codeclimate.com/github/ynb4gang/Page_Analyzer/test_coverage)


# Page Analyzer

## Live Demo
Check out the live demo of the Page Analyzer: [Page Analyzer Live](https://page-analyzer-y829.onrender.com)

Page Analyzer is a web application designed to analyze and provide insights into web pages. This project leverages a modern tech stack to deliver efficient and scalable performance.

## Technology Stack

### Frontend
- **Bootstrap**: A powerful front-end framework for faster and easier web development.

### Backend Framework
- **Javalin**: A lightweight web framework for Java and Kotlin that is simple, flexible, and extensible.

### Database
- **H2 Database**: Used for local development to simplify setup and testing.
- **PostgreSQL**: Used for production environments to ensure reliability and scalability.

### Parser
- **Jsoup**: A Java library for working with real-world HTML. Provides a very convenient API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.

### Testing
- **JUnit 5**: The next generation of JUnit, used for unit testing in Java.
- **MockWebServer**: A library for testing HTTP clients by mocking responses.

### Deployment
- **Render**: A modern cloud platform (PaaS) for hosting web applications. Ensures seamless deployment and scaling.

## Installation and Setup

To run this project locally, follow these steps:

1. **Clone the repository:**
    ```sh
    git clone https://github.com/ynb4gang/Page_Analyzer.git
    cd Page_Analyzer
    ```

2. **Set up the database:**
   - For local development, configure the H2 database settings in your application properties.
   - For production, set up a PostgreSQL database and configure the connection settings.

3. **Build and run the application:**
    ```sh
    ./gradlew run
    ```

4. **Access the application:**
   Open your web browser and navigate to `http://localhost:7000` (or the port configured in your application).

## Running Tests

To execute tests, run:
```sh
./gradlew test
