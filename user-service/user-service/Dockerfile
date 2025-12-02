# --------- BUILD STAGE ---------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# 1. Copy build tools and config
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 2. Download dependencies (Cached layer)
# This should run BEFORE copying source code
RUN ./mvnw dependency:go-offline

# 3. Copy source code (Change strictly to 'src')
COPY src src

# 4. Build the application
RUN ./mvnw -DskipTests package

# --------- RUNTIME STAGE ---------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]