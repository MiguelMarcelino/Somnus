var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var UserInfoSchema = new Schema ({
    photoName : {type: String, required: true},
    userFirstName : {type: String, required: true},
    userLastName : {type: String, required: true},
    cellphone : {type: String, required: true},
    email : {type: String, required: true},
    socialLinks : [{type: String, required: true}],
    birthDate : {type: Date, required: true},
    nacionality : {type: String, required: true},
    personalWebsiteLink : {type: String, required: true},
});

module.exports = mongoose.model("UserInfo",UserInfoSchema);