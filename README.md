> [!IMPORTANT]
> 🚀 Projeto Finalizado

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

### 🏋️ FitStream API - Real-Time Fitness & Nutrition

Sistema de monitoramento de rotina de saúde e treinos em tempo real, focado em otimização de tempo e análise de dados.

A API atua como núcleo de processamento do ecossistema FitStream, centralizando regras de negócio, persistência de dados e processamento assíncrono de eventos.

### 🎯 O Problema resolvido

Aplicativos de saúde tradicionais tratam treinos e dietas como silos isolados com alta latência. O FitStream resolve isso centralizando o processamento de forma assíncrona, consolidando macros, calorias e cargas instantaneamente para tomadas de decisão em tempo real.
### 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot
- **Build:** Maven
- **Banco de Dados:** PostgreSQL 16
- **Migrations:** Flyway
- **Mensageria:** Apache Kafka
- **Containerização:** Docker
- **Testes Unitários:** JUnit, Mockito
- **Testes Funcionais/E2E:** Jest, Supertest
- **Testes de Integração:** Spring Boot Test
- **CI/CD:** GitHub Actions
- **Testes Funcionais:** Node.js, pnpm

### 🏗️ Arquitetura

A aplicação utiliza uma arquitetura orientada a eventos para desacoplar o processamento das ações do usuário da atualização dos dados em tempo real.

O Kafka atua como camada de mensageria entre os componentes do sistema, permitindo que eventos sejam processados de forma assíncrona e escalável.

graph TD
User["👤 Usuário / Cliente"] -->|HTTP / Navegador| FE["⚛️ Front-end\n(React + Vite)\n[Repositório Separado]"]

    subgraph "Backend - Spring Boot & Mensageria"
        FE -->|REST API & SSE| BE["☕ Spring Boot API\n(Java 21 / Virtual Threads)"]
        BE -->|Persistência| DB[("(🗄️ PostgreSQL 16\nFlyway Migrations)")]
        BE -->|Disparo de Eventos| KF["📨 Apache Kafka\n(Event Broker)"]
        KF -->|Consumo Assíncrono| BE
    end

    style User fill:#f9f,stroke:#333,stroke-width:2px
    style FE fill:#bbf,stroke:#333,stroke-width:2px
    style BE fill:#bfb,stroke:#333,stroke-width:2px
    style DB fill:#ff9,stroke:#333,stroke-width:2px
    style KF fill:#fbb,stroke:#333,stroke-width:2px
### 🧠 Decisões Técnicas

**Arquitetura Orientada a Eventos (Kafka):**

Escolhido para desacoplar a ingestão de ações do usuário da emissão do Live Feed. Isso evita gargalos no banco de dados e permite que o sistema escale horizontalmente o processamento de métricas em tempo real.

**Migrations com Flyway:**

O banco de dados é tratado como código. O Flyway garante que todos os ambientes (local, CI/CD e produção) possuam esquemas previsíveis, evitando inconsistências no PostgreSQL 16.

**Testes de Caixa Preta com Jest/Supertest:**

Em vez de depender apenas de testes de integração acoplados ao ecossistema Spring, optou-se por executar testes funcionais via Node.js.

Essa abordagem simula o comportamento real de um cliente HTTP consumindo a API de forma agnóstica, validando contratos e comportamentos finais.

**Java 21 + Virtual Threads:**

Utilização de Virtual Threads para suportar um alto throughput de conexões simultâneas exigido pelo Server-Sent Events (SSE), reduzindo o custo de recursos associado ao gerenciamento tradicional de threads.

### 🚀 Como Executar o Projeto Localmente

#### 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de possuir:

- Java 21
- Docker
- Docker Compose
- Maven
- Node.js
- pnpm

> 💡 Recomenda-se o uso de WSL com Docker integrado para ambientes Windows.

#### 🐳 1. Subir o Docker

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
````

Instale as dependencias:
````
pnpm install
````
Depois execute os testes:
````
pnpm test
````
#### Frontend
Este repositorio contem somente o backend

Acesse o Frontend em [Repostiorio do Frontend](https://github.com/LeonardoEnnes/fitstream-app)


