var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var WorkSchema = new Schema({
    name: { type: String, required: true },
    dateStart: { type: Date, required: true },
    dateEnd: { type: Date, required: true },
    description: { type: String, required: true },
    projectLinks: [{ type: String, required: true }],
});

module.exports = mongoose.model("Work", WorkSchema);