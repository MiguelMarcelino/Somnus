var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var SkillSchema = new Schema({
    name: { type: String, min: 1, max: 10, required: true },
    level: { type: Number, required: true }
});

module.exports = mongoose.model("Skill", SkillSchema);