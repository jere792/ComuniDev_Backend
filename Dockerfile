FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests -B

EXPOSE 8080

CMD ["java", "-Djdk.tls.client.protocols=TLSv1.2", "-Dhttps.protocols=TLSv1.2", "-jar", "target/comunidev-backend-0.0.1-SNAPSHOT.jar"]
