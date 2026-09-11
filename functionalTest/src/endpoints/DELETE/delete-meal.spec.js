const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

jest.setTimeout(10000);

describe("DELETE /meals/{id}", () => {
  let mealId;

    beforeAll(async () => {
        const res = await request(API_URL).post("/meals").send({
            name: "Ceia",
            description: "Chá de camomila",
            calories: 50,
            protein: 1,
            carbs: 5,
            fat: 0
        });
        mealId = res.body.id;
    });

  it("Should return 204 when deleting an existing meal", async () => {
    const deleteRes = await request(API_URL).delete(`/meals/${mealId}`);
    expect(deleteRes.status).toBe(204);

    const listRes = await request(API_URL).get("/meals");
    const stillExists = listRes.body.find(m => m.id === mealId);
    expect(stillExists).toBeUndefined();
  });
});
