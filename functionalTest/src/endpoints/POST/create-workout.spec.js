const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

describe("POST /workouts", () => {
  it("should create a new workout successfully and return status 201", async () => {
    const response = await request(API_URL)
      .post("/workouts")
      .send({
        exercise: "Supino Reto",
        sets: 4,
        reps: 10,
        weight: 80.0
      });

    expect(response.status).toBe(201);
    expect(response.body).toHaveProperty("id");
    expect(response.body.exercise).toBe("Supino Reto");
    expect(response.body.completed).toBe(false);
  });
});
