var express = require('express');
var router = express.Router();

var games_controller = require("../controllers/gamesController");

router.get('/games', games_controller.game_article_list);

router.get('/game/:id', games_controller.game_article_detail);

router.post('/game/create', games_controller.game_article_create);

module.exports = router;