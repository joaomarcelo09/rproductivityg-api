FROM openjdk:17
WORKDIR /app
COPY . .
RUN ./mvnw clean package
CMD ["java", "-jar", "target/your-app-name.jar"]
