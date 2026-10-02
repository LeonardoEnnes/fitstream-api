
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F.svg?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Enabled-C71A36.svg?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1.svg?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-Enabled-CC0200.svg?logo=flyway&logoColor=white)](https://documentation.red-gate.com/flyway)
[![Kafka](https://img.shields.io/badge/Apache_Kafka-Enabled-231F20.svg?logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg?logo=docker&logoColor=white)](https://www.docker.com/)
[![JUnit](https://img.shields.io/badge/JUnit-Enabled-25A162.svg?logo=junit5&logoColor=white)](https://junit.org/)
[![Mockito](https://img.shields.io/badge/Mockito-Enabled-C5D9C8.svg)](https://site.mockito.org/)
[![Jest](https://img.shields.io/badge/Jest-Enabled-C21325.svg?logo=jest&logoColor=white)](https://jestjs.io/)
[![Supertest](https://img.shields.io/badge/Supertest-Enabled-333333.svg)](https://github.com/ladjs/supertest)
[![CI](https://img.shields.io/badge/CI-GitHub_Actions-2088FF.svg?logo=githubactions&logoColor=white)](https://github.com/features/actions)

# FitStream API — Real-Time Fitness & Nutrition

Backend do ecossistema FitStream, responsável pelo gerenciamento de treinos,
nutrição, regras de negócio, persistência de dados e processamento de eventos
em tempo real.

Desenvolvido com Java 21 e Spring Boot, o projeto utiliza uma arquitetura
modular, processamento assíncrono com Apache Kafka e Server-Sent Events (SSE)
para comunicação em tempo real com o frontend.

> Frontend: [FitStream App](https://github.com/LeonardoEnnes/fitstream-app)

## Sobre o Projeto

O FitStream é uma aplicação voltada ao acompanhamento de treinos e nutrição,
permitindo registrar e acompanhar métricas como exercícios, cargas, calorias
e macronutrientes.

O backend centraliza as regras de negócio e disponibiliza uma API REST,
além de mecanismos de atualização em tempo real para o frontend.

## Principais Funcionalidades

- Gerenciamento de treinos e exercícios
- Registro e acompanhamento de cargas
- Controle de calorias e macronutrientes
- Atualização de dados em tempo real via SSE
- Processamento assíncrono de eventos com Apache Kafka
- Persistência em PostgreSQL
- Versionamento do schema com Flyway
- Testes unitários, integração e funcionais
- Pipeline de integração contínua com GitHub Actions

## Tecnologias

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot |
| Build | Maven |
| Banco de dados | PostgreSQL 16 |
| Migrations | Flyway |
| Mensageria | Apache Kafka |
| Comunicação em tempo real | Server-Sent Events (SSE) |
| Containerização | Docker |
| Testes unitários | JUnit, Mockito |
| Testes de integração | Spring Boot Test |
| Testes funcionais | Jest, Supertest |
| Testes funcionais | Node.js, pnpm |
| CI | GitHub Actions |

## Arquitetura

```mermaid
graph TD
    User["Usuário / Cliente"] -->|HTTP / Navegador| FE["Front-end\n(React + Vite)\n[Repositório Separado]"]

    subgraph "Backend - Spring Boot & Mensageria"
        FE -->|REST API & SSE| BE[" Spring Boot API\n(Java 21 / Virtual Threads)"]
        BE -->|Persistência| DB[("(PostgreSQL 16\nFlyway Migrations)")]
        BE -->|Disparo de Eventos| KF[" Apache Kafka\n(Event Broker)"]
        KF -->|Consumo Assíncrono| BE
    end

    style User fill:#2D3748,stroke:#E2E8F0,stroke-width:2px,color:#FFFFFF
    style FE fill:#1A365D,stroke:#63B3ED,stroke-width:2px,color:#FFFFFF
    style BE fill:#22543D,stroke:#68D391,stroke-width:2px,color:#FFFFFF
    style DB fill:#744210,stroke:#F6E05E,stroke-width:2px,color:#FFFFFF
    style KF fill:#742A2A,stroke:#FC8181,stroke-width:2px,color:#FFFFFF

```

### Como Executar o Projeto Localmente

#### 1. Subir o Docker

Na raiz do projeto, execute:

```bash
docker compose up -d --build
```

#### 2. Verificar se a API está respondendo
```bash
http://localhost:8080/
```
Ou acesse a documentação Swagger através do navegador:
````
http://localhost:8080/swagger-ui.html
````
### Testes Unitários e de Integração
Para executar os testes unitários e de integração:
````
./mvnw clean verify
````
#### Testes Funcionais
Os testes funcionais utilizam Jest + Supertest e são executados de maneira independente através do Node

Acesse a pasta de testes:
````
cd __functionalTest
pnpm install
pnpm test
````
