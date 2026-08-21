import request from "supertest";

const API_URL = "http://localhost:8080";

describe("GET /", () => {
  it("deve retornar status 200 e a mensagem Hello, World!", async () => {
    const response = await request(API_URL).get("/");

    expect(response.status).toBe(200);
    expect(response.text).toBe("Hello, World!");
  });
});
