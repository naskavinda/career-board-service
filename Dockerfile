# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy the source code into the container
COPY src ./src
# Run the build command to create the build files in the /target/classes directory
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
VOLUME /tmp
# Copy the built JAR file from the build stage to the run stage
COPY --from=build /app/target/*.jar app.jar
# Copy the classes directory to ensure build files are available
COPY --from=build /app/target/* /target/
# Set the entry point for the application
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
