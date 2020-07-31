var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var ArticleSchema = new Schema({
    articleName: { type: String, required: true },
    authorUserName: { type: String, required: true },
    description: { type: String, required: true },
    content: { type: String, required: true },
})

// Virtual to get a given article
ArticleSchema.virtual("url").get(function () {
    return "/articlesAPI/article/" + this._id;
});
 
 // Export model
 module.exports = mongoose.model("Article", ArticleSchema);
 