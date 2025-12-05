# Use Eclipse Temurin JRE for Java 21
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Copy the built jar file into the container
COPY build/libs/transaction-api-*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

