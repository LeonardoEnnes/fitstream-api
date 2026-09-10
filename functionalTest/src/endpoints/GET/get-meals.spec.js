const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

jest.setTimeout(10000);

describe("GET /meals", () => {
  let mealId;

  beforeAll(async () => {
    const res = await request(API_URL).post("/meals").send({
      name: "Lanche da Tarde",
      description: "Iogurte e castanhas"
    });
    mealId = res.body.id;
  });

  it("should list all meals and contain the expected record", async () => {
    const res = await request(API_URL).get("/meals");

    expect(res.status).toBe(200);
    expect(Array.isArray(res.body)).toBeTruthy();

    const found = res.body.find(m => m.id === mealId);
    expect(found).toBeDefined();
    expect(found.name).toBe("Lanche da Tarde");
  });
});