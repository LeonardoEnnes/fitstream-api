const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

describe("POST /meals", () => {
  it("should register a new meal successfully", async () => {
    const payload = {
      name: "Café da Manhã",
      description: "Tapioca com queijo e café preto"
    };

    const res = await request(API_URL)
      .post("/meals")
      .send(payload);

    expect(res.status).toBe(201);
    expect(res.body.id).toBeDefined();
    expect(res.body.name).toBe(payload.name);
    expect(res.body.description).toBe(payload.description);
    expect(res.body.consumedAt).toBeDefined();
  }, 10000);

  it("should return error when trying to create a meal without a name", async () => {
    const res = await request(API_URL)
      .post("/meals")
      .send({ name: "", description: "Apenas descrição" });

    expect(res.status).toBe(400);
  });
});