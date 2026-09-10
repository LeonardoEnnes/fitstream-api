import request from "supertest";
const API_URL = "http://localhost:8080";

describe("DELETE /workouts/{id}", () => {
  it("should delete an existing workout and return 204", async () => {
    const createRes = await request(API_URL).post("/workouts").send({
      title: "Treino para Deletar",
      description: "Teste de delete"
    });
    const id = createRes.body.id;

    const response = await request(API_URL).delete(`/workouts/${id}`);
    expect(response.status).toBe(204);

    const getRes = await request(API_URL).get(`/workouts/${id}`);
    expect(getRes.status).toBe(404);
  });
});
