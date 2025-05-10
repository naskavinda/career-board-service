# Career Board

This is a Spring Boot application that runs with **Java 21** and uses **PostgreSQL** as the database. You can set it up either using **Docker** or manually on your local machine.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Git** ([Download here](https://git-scm.com/))

Before running the application, ensure you have the following installed:

- **Java 21** ([Download here](https://adoptium.net/))
- **PostgreSQL** (if running locally)
- **Docker & Docker Compose** (if using Docker setup)

## Cloning the Repository

To get started, first clone the repository:
```sh
git clone https://github.com/career-board/career-board-service.git
cd career-board-service
```

## Setup Instructions

### 1. Configuration
Before starting the application, you need to configure environment variables:

1. **Rename the environment file:**
   ```sh
   mv .example.env .env
   ```
2. **Update credentials** inside `.env` file to match your database configuration.

---

### 2. Running with Docker (Recommended)

To start the application using Docker:

To start the application using Docker:

```sh
mv .example.env .env  # Rename the env file if not already done
# Update the credentials in the .env file

docker-compose up --build
```

This will build the application and start it along with a PostgreSQL database inside a Docker container.

If you make changes and need to remove all images and containers created, you can use:
```sh
docker-compose down --rmi all
```

---

### 3. Running Locally (Without Docker)

If you prefer to run the application without Docker:

1. **Ensure you have Java 21 installed** and set up correctly.
2. **Ensure you have a running PostgreSQL instance.**
3. **Rename and configure the `.env` file** to match your local PostgreSQL setup.
4. **Run the application:**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

This will start the application and connect to your local PostgreSQL instance.

---

## Verifying the Setup

Once the application is running, verify that it's working by visiting:

```sh
http://localhost:8081
```

You should see the application running successfully.

---

## Stopping the Application

- **If running with Docker:**
  ```sh
  docker-compose down
  ```

- **If running locally:** Press `Ctrl + C` in the terminal where the app is running.

---

## Troubleshooting

- If the application fails to start, check the `.env` file for incorrect database credentials.
- Ensure PostgreSQL is running and accessible.
- If using Docker, ensure Docker is installed and running.

---

## License

This project is licensed under the MIT License.

