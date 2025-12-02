# ----------- BUILD STAGE -----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper + config first (for caching)
COPY mvnw .
COPY .mvn .mvn

# OLD: COPY user-service/pom.xml .
COPY pom.xml .

# OLD: COPY user-service/src src
COPY src src
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