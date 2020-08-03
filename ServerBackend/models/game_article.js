var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var GameArticleSchema = new Schema({
    articleName: { type: String, required: true },
    authorUserName: { type: String, required: true },
    description: { type: String, required: true },
    datePublished: { type: Date, required: true },
    type: { type: String, required: true },
    content: { type: String, required: true },
})

// Virtual to get a given article
GameArticleSchema.virtual("url").get(function () {
    return "/gamesApi/article/" + this._id;
});
 
 // Export model
 module.exports = mongoose.model("GameArticle", GameArticleSchema);
 