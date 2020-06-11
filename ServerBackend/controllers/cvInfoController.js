var CVInfoModel = require("../models/cv_contents/cv_info");

var fs = require("fs");
var async = require("async");

// Rever 
exports.cv_info = function (req, res, next) {
    CVInfoModel.findOne()
        .populate({path: "skills"})
        .populate({path:"languages"})
        .populate({path:"operatingSystems"})
        .populate({path: "otherSkills"})
        .populate({path: "experience"})
        .where({ userID: req.params.userID })
        .exec(function (err, hotel) {
            if (err) {
               return next(err);
            }
            res.json({ cv_info: cv_info });
         });
   
};
 