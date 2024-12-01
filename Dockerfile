# Use Amazon Corretto 23 as the base image
FROM amazoncorretto:23

# Set the working directory inside the container
WORKDIR /app

# Copy the application's JAR file into the container
COPY target/your-application.jar app.jar

# Expose the port your Spring application listens on (default is 8080)
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
