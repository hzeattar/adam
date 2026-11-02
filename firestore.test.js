const { initializeTestEnvironment, assertFails, assertSucceeds } = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;

before(async () => {
  testEnv = await initializeTestEnvironment({
    projectId: "elite-law-firm-1762617525682",
    firestore: {
      rules: fs.readFileSync("firestore.rules", "utf8"),
      host: "127.0.0.1",
      port: 8085
    }
  });
});

after(async () => {
  if (testEnv) await testEnv.cleanup();
});

beforeEach(async () => {
  if (testEnv) await testEnv.clearFirestore();
});

test("unauthenticated user cannot read user profile", async () => {
  const unauthedDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthedDb.collection("users").doc("user123").get());
});

test("authenticated user can write their own profile", async () => {
  const authedDb = testEnv.authenticatedContext("user123").firestore();
  await assertSucceeds(
    authedDb.collection("users").doc("user123").set({
      userId: "user123",
      email: "user@revive.eg",
      ecoPoints: 100,
      totalCashEarnedEgp: 500
    })
  );
});

test("authenticated user cannot write another user's profile", async () => {
  const authedDb = testEnv.authenticatedContext("user123").firestore();
  await assertFails(
    authedDb.collection("users").doc("other456").set({
      userId: "other456",
      email: "other@revive.eg"
    })
  );
});
