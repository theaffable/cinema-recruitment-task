# Use a multi-stage build for efficiency
# Stage 1: Build the application
FROM gradle:8.8-jdk21 as builder
WORKDIR /app

# Copy the project files
COPY build.gradle.kts settings.gradle.kts gradle/ ./
COPY gradle/libs.versions.toml gradle/libs.versions.toml

# Pre-download dependencies for caching
RUN gradle dependencies --no-daemon || return 0

COPY src ./src

# Build the application
RUN gradle build --no-daemon

# Stage 2: Create runtime image
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Set env variables
ENV OMDB_API_KEY $OMDB_API_KEY

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
