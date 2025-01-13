# Career Board

![Career Board](https://github.com/career-crew/career-board-service/assets/53615807/61b0c2fb-820f-4464-b84c-c18f0fc793dd)

## Overview

Career Board is a basic Spring Boot application that provides a simple REST endpoint. This project is a starting point for a career board application and can be expanded with additional features and functionality.

## Features

- Single REST endpoint that returns a welcome message.
- Built with Java 11 and Spring Boot.

## Prerequisites

- Java 11
- Maven 3.6.3 or higher
- Git

## Setting Up the Project Locally

1. **Clone the repository**

    ```sh
    git clone https://github.com/career-crew/career-board.git
    cd career-board
    ```

2. **Build the project**

    ```sh
    mvn clean install
    ```

3. **Run the application**

    ```sh
    mvn spring-boot:run
    ```

4. **Access the application**

   Open your browser and go to `http://localhost:8081`.

5. **Access Swagger UI & API Docs**

   Open your browser and go to `http://localhost:8081/swagger-ui.html` to access the swagger UI.

   Open your browser and go to `http://localhost:8081/v3/api-docs` to access the API documentation.

## Endpoint

- `GET /`: Returns a `Hello World!` message.

## Contributing

We welcome contributions! If you want to contribute to this project, please follow these steps:

1. **Fork the repository**

   Click the "Fork" button at the top right of this page to create a copy of the repository under your own GitHub account.

2. **Clone your fork**

    ```sh
    git clone https://github.com/career-crew/career-board.git
    cd career-board
    ```

3. **Create a new branch**

    ```sh
    git checkout -b my-new-feature
    ```

4. **Make your changes**

   Make the necessary changes to the codebase.

5. **Commit your changes**

    ```sh
    git add .
    git commit -m "Add some feature"
    ```

6. **Push to the branch**

    ```sh
    git push origin my-new-feature
    ```

7. **Create a pull request**

   Open a pull request to the main repository and provide a clear description of your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](https://www.mit.edu/~amini/LICENSE.md) file for details.
