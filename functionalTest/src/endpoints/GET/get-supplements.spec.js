const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

describe("GET /supplements", () => {
    let supplementId;

    beforeAll(async () => {
        const res = await request(API_URL).post("/supplements").send({
            name: "Multivitamínico",
            dosage: 1,
            unit: "caps"
        });
        supplementId = res.body.id;
    });

    it("Should list all supplements and contain the record created in the setup", async () => {
        const res = await request(API_URL).get("/supplements");

        expect(res.status).toBe(200);
        expect(Array.isArray(res.body)).toBeTruthy();

        const found = res.body.find(s => s.id === supplementId);
        expect(found).toBeDefined();
        expect(found.name).toBe("Multivitamínico");
    });
});
