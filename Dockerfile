FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY .mvn .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw

# serve para baixar todas as dependencias do projeto e evitar que o maven baixe as dependencias toda vez que for buildar a imagem
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
