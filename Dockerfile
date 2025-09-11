# Step 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
COPY data/hr_faq.csv /app/data/hr_faq.csv
RUN mvn clean package -DskipTests=True

# Step 2: Runtime stage
# Use the Debian-based Temurin image
FROM eclipse-temurin:21-jre
WORKDIR /app
# Install the missing C++ shared library runtime for full compatibility
RUN apt-get update && apt-get install -y libstdc++6
COPY --from=builder /app/target/spring-ai-chatbot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
