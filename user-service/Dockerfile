# ----------- BUILD STAGE -----------
# Use eclipse-temurin for a stable JDK
FROM eclipse-temurin:17-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# --- Dependency Layer Caching ---
# The Dockerfile is located inside the 'user-service' folder, so we copy files directly.
# Copy Maven wrapper and configuration first (for better caching)
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml (project definition)
COPY pom.xml .

# Download all dependencies. This is cached as long as pom.xml doesn't change.
RUN ./mvnw dependency:go-offline

# --- Source Code & Build ---
# Copy the actual source code
COPY src src

# Build the application (skipping tests)
RUN ./mvnw -DskipTests package


# ----------- RUNTIME STAGE -----------
# Use a smaller JRE image for the final, lightweight container
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the generated JAR file from the 'build' stage
# The name pattern is often 'target/<appname>-<version>.jar', 
# using wildcards ensures it finds the file.
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080
ENV PORT=8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
