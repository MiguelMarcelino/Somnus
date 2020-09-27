var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var cors = require("cors");
const mongoose = require('mongoose');

var indexRouter = require('./routes/index');
var articlesRouter = require('./routes/articles');
var gamesRouter = require('./routes/games');
var teamMembersRouter = require('./routes/team-members');

// For SSL
var fs = require('fs');
var privateKey  = fs.readFileSync('sslcert/server.key', 'utf8');
var certificate = fs.readFileSync('sslcert/server.crt', 'utf8');
var credentials = {key: privateKey, cert: certificate};
var https = require('https');

var app = express();

//////////////////////////////////////////////
////////////////// Firebase //////////////////
//////////////////////////////////////////////

// Firebase App (the core Firebase SDK) is always required and
// must be listed before other Firebase SDKs
// var firebase = require("firebase/app");

// Add the Firebase products that you want to use
// require("firebase/auth");
// require("firebase/firestore");

// var firebaseConfig = {
//   apiKey: "AIzaSyD52dfJGrzSn3bV974ABZiK6Kt7Xfie2FM",
//   authDomain: "somnusserverv1.firebaseapp.com",
//   databaseURL: "https://somnusserverv1.firebaseio.com",
//   projectId: "somnusserverv1",
//   storageBucket: "somnusserverv1.appspot.com",
//   messagingSenderId: "537573150465",
//   appId: "1:537573150465:web:34fd4520c9b628362a5500",
//   measurementId: "G-G618VY0T5R"
// };

// Initialize Firebase
// firebase.initializeApp(firebaseConfig);

//////////////////////////////////////////////
//////////////////////////////////////////////
//////////////////////////////////////////////

//////////////////////////////////////////////
////////////////// MongoDB ///////////////////
//////////////////////////////////////////////
var mongoDB =
   "mongodb+srv://SomnusAdmin:EtvbBSsjUqvB26j@somnuscluster.0lgek.mongodb.net/somnusDB?retryWrites=true&w=majority";
mongoose.connect(mongoDB, { useNewUrlParser: true });
var db = mongoose.connection;
db.on("error", console.error.bind(console, "MongoDB connection error:"));

//////////////////////////////////////////////
//////////////////////////////////////////////
//////////////////////////////////////////////

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// CORS
app.options('*', cors()) 
app.use(cors());

// Routes
app.use('/', indexRouter);

// Articles
app.use('/articlesApi', articlesRouter);
// Game-Articles-Api
app.use('/gamesApi', gamesRouter);
// Team-Members
app.use('/teamMembersApi', teamMembersRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

var httpsServer = https.createServer(credentials, app);
httpsServer.listen(app.get('port'));

// module.exports = app;
