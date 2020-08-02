var express = require('express');
var router = express.Router();

var teamMembersController = require("../controllers/teamMembersController");

router.get('/teamMembers', teamMembersController.team_member_list);

module.exports = router;