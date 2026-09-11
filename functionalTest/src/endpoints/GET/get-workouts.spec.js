const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

describe("GET /workouts", () => {
  it("should list all workouts successfully", async () => {
    // Setup: Cria um treino para garantir dados na listagem
    await request(API_URL).post("/workouts").send({
      exercise: "Agachamento",
      sets: 4,
      reps: 8,
      weight: 100.0
    });

    const response = await request(API_URL).get("/workouts");

    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBeTruthy();
    expect(response.body.length).toBeGreaterThan(0);

    const found = response.body.find(w => w.exercise === "Agachamento");
    expect(found).toBeDefined();
    expect(found.sets).toBe(4);
    expect(found.reps).toBe(8);
    expect(found.weight).toBe(100.0);
  });
});
