import request from "supertest";
const API_URL = "http://localhost:8080";

describe("PATCH /workouts/{id}/complete", () => {
  it("should mark the workout as completed successfully", async () => {
    const createRes = await request(API_URL).post("/workouts").send({
      title: "Treino para Concluir",
      description: "Teste de patch"
    });
    const id = createRes.body.id;

    const response = await request(API_URL).patch(`/workouts/${id}/complete`);

    expect(response.status).toBe(200);
    expect(response.body.completed).toBe(true);
  });
});
