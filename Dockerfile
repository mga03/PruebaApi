FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
# Copiamos el pom.xml de esta carpeta
COPY pom.xml . 
RUN mvn dependency:go-offline
# Copiamos el código fuente de esta carpeta
COPY src ./src
# Empaquetamos (la compilación con Lombok ocurre aquí)
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8083 
ENTRYPOINT ["java", "-jar", "app.jar"]