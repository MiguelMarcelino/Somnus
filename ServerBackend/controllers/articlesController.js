var Article = require("../models/article");

const { body, sanitizeBody, validationResult } = require('express-validator');

exports.article_list = function (req, res, next) {
    Article.find({}).exec(function (err, articles) {
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
                    content: article.content,

                };
            }),
        });
    });
};

exports.article_detail = function (req, res, next) {
    Article.findOne()
       .where({ _id: req.params.id })
       .exec(function (err, article) {
          if (err) {
             return next(err);
          }
          res.json({ article: article });
       });
};

exports.article_create = [
    // Validate fields.
    // Validate Article Name
    body('articleName').isLength({ min: 1 }).trim().withMessage('An article name must be specified.'),
    // Validate Article Author Name
    body('authorUserName').isLength({ min: 1 }).trim().withMessage('An author name must be specified.'),
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
            var article = new Article({
                articleName: req.body.articleName,
                authorUserName: req.body.authorUserName,
                description: req.body.description,
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