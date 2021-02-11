var TeamMember = require('../models/team-member');

exports.team_member_list = function (req, res, next) {
    TeamMember.find({}).exec(function (err, team_members) {
        if (err) {
            return next(err);
        }

        res.json({
            team_member_list: team_members.map((teamMember) => {
                return {
                    _id: teamMember._id,
                    memberName: teamMember.memberName,
                    photoPath: teamMember.photoPath,
                    dateJoined: teamMember.dateJoined,
                    contributions: teamMember.contributions,
                };
            }),
        });
    });
};