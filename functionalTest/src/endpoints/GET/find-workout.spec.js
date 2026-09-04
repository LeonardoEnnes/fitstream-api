import request from "supertest";

const API_URL = "http://localhost:8080";

describe("GET /workouts", () => {
  it("deve buscar um treino existente pelo ID com sucesso", async () => {
    const createResponse = await request(API_URL)
      .post("/workouts")
      .send({
        title: "Treino para Busca",
        description: "Validando o GET por ID"
      });

    const workoutId = createResponse.body.id;

    const response = await request(API_URL).get(`/workouts/${workoutId}`);

    // Assert
    expect(response.status).toBe(200);
    expect(response.body.id).toBe(workoutId);
    expect(response.body.title).toBe("Treino para Busca");
  });

  it("deve retornar 404 Not Found quando o ID do treino não existir", async () => {
    const fakeId = "00000000-0000-0000-0000-000000000000";
    const response = await request(API_URL).get(`/workouts/${fakeId}`);

    expect(response.status).toBe(404);
    expect(response.body.status).toBe(404);
    expect(response.body.error).toBe("Not Found");
    expect(response.body).toHaveProperty("message");
  });
});
