FROM maven:3.9.16-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml ./

# Baixa as dependências do projeto antecipadamente
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests --no-transfer-progress

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
