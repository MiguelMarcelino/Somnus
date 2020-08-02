var admin = require("firebase-admin");
const serviceAccount = require("./somnusserverv1-firebase-adminsdk-npfh3-9c04ac118e.json");

function initializeFirebase() {
    admin.initializeApp({
      credential: admin.credential.cert(serviceAccount),
      databaseURL: "https://somnusserverv1.firebaseio.com"
    });
}

function getAdmin() {
    return admin;
}