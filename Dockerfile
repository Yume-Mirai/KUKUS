# Multi-stage build untuk optimasi
# Stage 1: Build aplikasi
FROM openjdk:17-jdk-slim as builder

WORKDIR /app

# Copy Maven wrapper dan pom.xml terlebih dahulu untuk leverage Docker cache
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn/ .mvn/

# Download dependencies (akan di-cache jika pom.xml tidak berubah)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src/ src/

# Build aplikasi
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jre-slim

WORKDIR /app

# Install curl untuk health checks (opsional)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy JAR file dari stage builder
COPY --from=builder /app/target/*.jar app.jar

# Expose port yang digunakan Spring Boot (default 8080)
EXPOSE 8080

# Health check untuk memastikan aplikasi berjalan dengan baik
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Command untuk menjalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]