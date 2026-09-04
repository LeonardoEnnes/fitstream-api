import request from "supertest";

const API_URL = "http://localhost:8080";

describe("POST /workouts", () => {
  it("deve criar um treino com sucesso e retornar status 201", async () => {
    const response = await request(API_URL)
      .post("/workouts")
      .send({
        title: "Treino Funcional E2E",
        description: "Teste automatizado via Supertest"
      });

    expect(response.status).toBe(201);
    expect(response.body).toHaveProperty("id");
    expect(response.body.title).toBe("Treino Funcional E2E");
    expect(response.body.completed).toBe(false);
  });
});
