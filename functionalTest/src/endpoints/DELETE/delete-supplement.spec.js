const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

describe("DELETE /supplements/{id}", () => {
    let supplementId;

    beforeAll(async () => {
        const res = await request(API_URL).post("/supplements").send({
            name: "Ômega 3",
            dosage: 2,
            unit: "caps"
        });
        supplementId = res.body.id;
    });

    it("Should remove the existing supplement and return 204", async () => {
        const deleteRes = await request(API_URL).delete(`/supplements/${supplementId}`);
        expect(deleteRes.status).toBe(204);

        const listRes = await request(API_URL).get("/supplements");
        const stillExists = listRes.body.find(s => s.id === supplementId);
        expect(stillExists).toBeUndefined();
    });
});
