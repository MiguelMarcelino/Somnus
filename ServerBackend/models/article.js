// var mongoose = require("mongoose");
// var Schema = mongoose.Schema;

var ArticleSchema = new Schema({
    articleName: { type: String, required: true },
    authorUserName: { type: String, required: true },
    description: { type: String, required: true },
    content: { type: String, required: true },
})

 // Export model
 module.exports = mongoose.model("Article", ArticleSchema);

