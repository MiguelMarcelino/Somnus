var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var CVInfoSchema = new Schema(
    {
        userID: { type: Number, required: true },
        userInfo: { type: Schema.Types.ObjectId, ref: "UserInfo", required: true },
        description: { type: String },
        programmingLanguages: [{ type: Schema.Types.ObjectId, ref: "ProgrammingLanguage" }],
        languages: [{ type: Schema.Types.ObjectId, ref: "Language" }],
        operatingSystems: [{ type: Schema.Types.ObjectId, ref: "OperatingSystem" }],
        internships: [{ type: Schema.Types.ObjectId, ref: "Internship" }],
        otherSkills: [{ type: Schema.Types.ObjectId, ref: "OtherSkill" }],
    }
)