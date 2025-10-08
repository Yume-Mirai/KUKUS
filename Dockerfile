# Single-stage build menggunakan Eclipse Temurin untuk kompatibilitas yang lebih baik
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install curl untuk health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy Maven wrapper dan pom.xml terlebih dahulu untuk leverage Docker cache
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn/ .mvn/

# Download dependencies (akan di-cache jika pom.xml tidak berubah)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src/ src/

# Build aplikasi
RUN ./mvnw clean package -DskipTests

# Expose port yang digunakan Spring Boot (default 8080)
EXPOSE 8080

# Health check untuk memastikan aplikasi berjalan dengan baik
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Command untuk menjalankan aplikasi
ENTRYPOINT ["java", "-jar", "target/test3-0.0.1-SNAPSHOT.jar"]