var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var TeamMemberSchema = new Schema({
    memberName: { type: String, required: true },
    photoPath: { type: String, required: true },
    dateJoined: { type: Date, required: true },
    contributions: { type: Number, required: true },
})

// Export model
module.exports = mongoose.model("User", TeamMemberSchema);
 