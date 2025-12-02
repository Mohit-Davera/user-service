# ----------- BUILD STAGE -----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config first (for caching)
# NO user-service/ prefix needed as the Dockerfile is inside the service folder
COPY mvnw .
COPY .mvn .mvn

# Copy project files (pom.xml must be copied before dependency:go-offline)
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Copy source code (Now this should definitely work)
COPY src src

# Build the application
RUN ./mvnw -DskipTests package


# ----------- RUNTIME STAGE -----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy built jar from previous stage
# Assuming your JAR name is spring-boot-app.jar or similar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]
