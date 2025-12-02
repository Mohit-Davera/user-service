# ----------- BUILD STAGE -----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config first (for caching)
COPY user-service/mvnw .
COPY user-service/.mvn .mvn

# Copy project files
COPY user-service/pom.xml .
RUN ./mvnw dependency:go-offline

# Copy source code
COPY user-service/src src

# Build the application
RUN ./mvnw -DskipTests package


# ----------- RUNTIME STAGE -----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]
