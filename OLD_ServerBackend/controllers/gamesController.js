var GameArticle = require("../models/game_article");

const { body, sanitizeBody, validationResult } = require('express-validator');

exports.game_article_list = function (req, res, next) {
    GameArticle.find({}).exec(function (err, articles) {
        if (err) {
            return next(err);
        }

        res.json({
            article_list: articles.map((article) => {
                return {
                    _id: article._id,
                    articleName: article.articleName,
                    authorUserName: article.authorUserName,
                    description: article.description,
                    datePublished: req.body.datePublished,
                    type: req.body.type,
                    content: article.content,

                };
            }),
        });
    });
};

exports.game_article_detail = function (req, res, next) {
    GameArticle.findOne()
       .where({ _id: req.params.id })
       .exec(function (err, article) {
          if (err) {
             return next(err);
          }
          res.json({ article: article });
       });
};

exports.game_article_create = [
    // Validate fields.
    // Validate Article Name
    body('articleName').isLength({ min: 1 }).trim().withMessage('An article name must be specified.'),
    // Validate Article Author Name
    body('authorUserName').isLength({ min: 1 }).trim().withMessage('An author name must be specified.'),
    // Validate Article description
    body('description').isLength({ min: 1 }).trim().withMessage('A description must be specified.'),
    //Validate Article publish date
    body('datePublished').isLength({ min: 1 }).trim().withMessage('A date of publication must be specified.'),
    //Validate Article type
    body('type').isLength({ min: 1 }).trim().withMessage('A date of publication must be specified.'),
    //Validate Article Content
    body('content').isLength({ min: 1 }).trim().withMessage('Content must not be empty.'),

    // Sanitize fields.
    // sanitizeBody('articleName').escape(),
    // sanitizeBody('authorUserName').escape(),
    // sanitizeBody('content').escape(),

    // Process request after validation and sanitization.

    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            // There are errors. Render form again with sanitized values/errors messages.
            // console.log(body('authorUserName'));
            console.log(errors.array());
            res.render('editorForm', { articleName: 'Name your Article', authorUserName: 'Author', description: 'Describe your article', 
                content: null, errors: errors.array() });
            return;
        } else {
            // Data from form is valid.

            // Create an Article object with escaped and trimmed data.
            var article = new GameArticle({
                articleName: req.body.articleName,
                authorUserName: req.body.authorUserName,
                description: req.body.description,
                datePublished: req.body.datePublished,
                type: req.body.type,
                content: req.body.content
            });
            article.save(function (err) {
                if (err) { return next(err); }
                // Successful - redirect to new author record.
                // res.redirect(author.url);
                res.json(article._id);
            });
        }
    }
];