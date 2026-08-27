# fitstream-api

Sistema de monitoramento de rotina de saúde e treinos em
tempo real, focado em otimização de tempo e analise de dados.

---

## Tecnologias
* **Java 21**
* **Spring Boot**
* **Maven**
* **PostgreSQL 16**
* **Flyway**
* **Docker**
* **Kafka**
* **JUnit & Mockito**
* **GitHub Actions** (CI/CD com testes unitários, de integração e funcionais)
* **Jest & Supertest** (Testes de caixa preta = funcionais/E2E em ambiente Node.js)
* **pnpm**

## Como executar o projeto localmente
Certifique se de ter o docker previamente instalados
> Recomendo o uso de WSL com doker integrado

### 1. Subir o Docker
Na raiz do projeto execute:
```bash
    docker compose up -d --build
```

### 2. Verifique se a API está respondendo
```bash
    curl http://localhost:8080/
```
## Rodar Testes Unitário e Integração
```bash
  ./mvnw clean verify
```
talvez outro

## Testes Funcionais
Acesse a pasta de testes, instale as dependencia (na primeira vez) e execute:
```bash
  cd __functionalTest
  pnpm install
  pnpm test
```


