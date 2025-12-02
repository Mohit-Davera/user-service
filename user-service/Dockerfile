# ----------- BUILD STAGE -----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config first (for caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Fix permissions & possible Windows line endings on mvnw
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Download dependencies (optional but good for cache)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw -B -DskipTests package


# ----------- RUNTIME STAGE -----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Railway will set PORT env, expose 8080 by convention
EXPOSE 8080
ENV PORT=8080

# If your Spring Boot app uses server.port=${PORT:8080}
# this will bind correctly on Railway
ENTRYPOINT ["java", "-jar", "app.jar"]