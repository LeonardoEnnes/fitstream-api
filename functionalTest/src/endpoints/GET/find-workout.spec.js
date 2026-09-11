const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

describe("GET /workouts", () => {
  it("should find an existing workout by ID successfully", async () => {
    const createResponse = await request(API_URL)
      .post("/workouts")
      .send({
        exercise: "Rosca Direta",
        sets: 3,
        reps: 12,
        weight: 15.0
      });

    const workoutId = createResponse.body.id;
    const response = await request(API_URL).get(`/workouts/${workoutId}`);

    expect(response.status).toBe(200);
    expect(response.body.id).toBe(workoutId);
    expect(response.body.exercise).toBe("Rosca Direta");
  });

  it("should return 404 Not Found when the workout ID does not exist", async () => {
    const fakeId = "00000000-0000-0000-0000-000000000000";
    const response = await request(API_URL).get(`/workouts/${fakeId}`);

    expect(response.status).toBe(404);
  });
});
