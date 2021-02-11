console.log('This script populates mock entities to the database for testing purposes' +
    'populatedb mongodb://');

var async = require('async');

var mongoose = require('mongoose');
var mongoDB = "mongodb+srv://SomnusAdmin:EtvbBSsjUqvB26j@somnuscluster.0lgek.mongodb.net/somnusDB?retryWrites=true&w=majority";
mongoose.connect(mongoDB, { useNewUrlParser: true, poolSize: 100 });
mongoose.Promise = global.Promise;
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));

var TeamMember = require('./models/team-member');

function teamMemberCreate(memberName, photoPath, dateJoined, 
        contributions, cb) {
    var teamMember = new TeamMember({ 
        'memberName': memberName, 
        'photoPath': photoPath,
        'dateJoined': dateJoined,
        'contributions': contributions,
     });

     teamMember.save(function (err) {
        if (err) {
            cb(err, null);
            return;
        }
        // no need for an array
        cb(null, teamMember);
    });
}

function createTeamMembers(cb) {
    async.series([
        function (callback) {
            var memberName = "Miguel Marcelino";
            var photoPath = "../../assets/icons/user-img-stock.png";
            var dateJoined = new Date();
            var contributions = 1;
            teamMemberCreate(memberName, photoPath, dateJoined, contributions, callback);
        },
        function (callback) {
            var memberName = "Diogo Soares";
            var photoPath = "../../assets/icons/user-img-stock.png";
            var dateJoined = new Date();
            var contributions = 1;
            teamMemberCreate(memberName, photoPath, dateJoined, contributions, callback);
        },
    ], cb);
}

async.series([
    createTeamMembers,
],
    // Optional callback
    function (err, results) {
        if (err) {
            console.log('ERROR: ' + err);
        }
        // All done, disconnect from database
        mongoose.connection.close();
        console.log("The end");
});