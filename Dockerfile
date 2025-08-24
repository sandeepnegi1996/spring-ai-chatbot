FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
# OPENAI_API_KEY must be passed at runtime
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
