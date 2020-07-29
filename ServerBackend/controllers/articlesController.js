var ArticleModel = require("../models/article");

exports.article_list = function (req, res, next) {
    ArticleModel.find({}).exec(function (err, articles) {
       if (err) {
          return next(err);
       }
 
       res.json({
            article_list: articles.map((article) => {
             return {
                articleName: article.name,
                authorUserName: article.authorUserName,
                content: article.content,

             };
          }),
       });
    });
};

exports.article_create = [
    // Validate fields.
    // Validate Article Name
    body('articleName').isLength({ min: 1 }).trim().withMessage('An article name must be specified.')
    .isAlphanumeric().withMessage('The article name has non-alphanumeric characters.'),
    // Validate Article Author Name
    body('authorUserName').isLength({ min: 1 }).trim().withMessage('An author name must be specified.')
    .isAlphanumeric().withMessage('The author name has non-alphanumeric characters.'),
    //Validate Article Content
    body('content').isLength({ min: 1 }).trim().withMessage('Content must not be empty.'),

    // Sanitize fields.
    sanitizeBody('articleName').escape(),
    sanitizeBody('authorUserName').escape(),
    sanitizeBody('content').escape(),

    // Process request after validation and sanitization.

    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            // There are errors. Render form again with sanitized values/errors messages.
            res.render('editorForm', { title: 'Create Article', author: req.body, errors: errors.array() });
            return;
        } else {
            // Data from form is valid.

            // Create an Article object with escaped and trimmed data.
            var article = new Author({
                articleName: req.body.articleName,
                authorUserName: req.body.authorUserName,
                content: req.body.content
            });
            article.save(function(err) {
                if (err) { return next(err); }
                // Successful - redirect to new author record.
                // res.redirect(author.url);
            });
        }
    }
];