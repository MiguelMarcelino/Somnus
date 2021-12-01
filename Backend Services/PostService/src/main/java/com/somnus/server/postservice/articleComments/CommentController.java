package com.somnus.server.postservice.articleComments;

import com.somnus.server.postservice.articleComments.dto.CommentDto;
import com.somnus.server.postservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments-api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/comments/{articleId}")
    public List<CommentDto> getCommentsFromArticle(@RequestBody User user, @PathVariable String articleId) {
        if(user != null) {
            return commentService.getCommentsFromArticle(user, Integer.parseInt(articleId));
        }
        return commentService.getCommentsFromArticle(null, Integer.parseInt(articleId));

    }

    @PostMapping(value = "/comment/create")
    public CommentDto addCommentToArticle(@RequestBody User user, @RequestBody CommentDto commentDto) {
        return commentService.addCommentToArticle(user, commentDto);
    }

    @PostMapping(value = "/comment/update")
    public CommentDto updateComment(@RequestBody User user, @RequestBody CommentDto commentDto) {
        return commentService.updateComment(user, commentDto);
    }

    @DeleteMapping(value = "/comment/delete/{commentId}")
    public void removeComment(@RequestBody User user, @PathVariable String commentId) {
        commentService.removeComment(user, Integer.parseInt(commentId));
    }

    @RequestMapping("/comment/add-like/{commentId}")
    public void addCommentLike(@RequestBody User user, @PathVariable String commentId) {
        commentService.addCommentLike(user, Integer.parseInt(commentId));
    }

    @RequestMapping("/comment/remove-like/{commentId}")
    public void removeCommentLike(@RequestBody User user, @PathVariable String commentId) {
        commentService.removeCommentLike(user, Integer.parseInt(commentId));
    }

}
