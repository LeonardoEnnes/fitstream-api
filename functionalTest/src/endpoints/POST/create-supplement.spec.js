const request = require("supertest");
const API_URL = process.env.API_URL || "http://localhost:8080";

describe("POST /supplements", () => {
    it("Should register a new supplement successfully", async () => {
        const payload = {
            name: "Creatina",
            dosage: 5,
            unit: "g"
        };

        const res = await request(API_URL)
            .post("/supplements")
            .send(payload);

        expect(res.status).toBe(201);
        expect(res.body.id).toBeDefined();
        expect(res.body.name).toBe(payload.name);
        expect(res.body.dosage).toBe(payload.dosage);
        expect(res.body.unit).toBe(payload.unit);
    });

    it("Should return 400 when trying to create a supplement with zero dosage", async () => {
        const res = await request(API_URL)
            .post("/supplements")
            .send({ name: "Whey Protein", dosage: 0, unit: "g" });

        expect(res.status).toBe(400);
    });
});
