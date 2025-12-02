# ----------- BUILD STAGE -----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Fix permissions & Windows line endings on mvnw
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the JAR
RUN ./mvnw -B -DskipTests package


# ----------- RUNTIME STAGE -----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]
