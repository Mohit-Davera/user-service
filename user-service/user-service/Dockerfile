# ----------- BUILD STAGE -----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config first (for caching)
COPY mvnw .
COPY .mvn .mvn

# Copy project files
COPY pom.xml .
RUN ./mvnw dependency:go-offline

COPY src src

# Build the application
RUN ./mvnw -DskipTests package


# ----------- RUNTIME STAGE -----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Railway overrides this automatically)
EXPOSE 8080

# Use Railway PORT env variable
ENV PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]
