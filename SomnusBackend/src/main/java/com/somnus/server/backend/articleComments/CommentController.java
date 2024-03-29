package com.somnus.server.backend.articleComments;

import com.somnus.server.backend.articleComments.dto.CommentDto;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/comments-api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/comments/{articleId}")
    public List<CommentDto> getCommentsFromArticle(Principal principal, @PathVariable String articleId) {
        if(principal != null) {
            User user = (User) ((Authentication) principal).getPrincipal();
            return commentService.getCommentsFromArticle(user, Integer.parseInt(articleId));
        }
        return commentService.getCommentsFromArticle(null, Integer.parseInt(articleId));

    }

    @PostMapping(value = "/comment/create")
    public CommentDto addCommentToArticle(Principal principal, @RequestBody CommentDto commentDto) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return commentService.addCommentToArticle(user, commentDto);
    }

    @PostMapping(value = "/comment/update")
    public CommentDto updateComment(Principal principal, @RequestBody CommentDto commentDto) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return commentService.updateComment(user, commentDto);
    }

    @DeleteMapping(value = "/comment/delete/{commentId}")
    public void removeComment(Principal principal, @PathVariable String commentId) {
        User user = (User) ((Authentication) principal).getPrincipal();

        commentService.removeComment(user, Integer.parseInt(commentId));
    }

    @RequestMapping("/comment/add-like/{commentId}")
    public void addCommentLike(Principal principal, @PathVariable String commentId) {
        User user = (User) ((Authentication) principal).getPrincipal();

        commentService.addCommentLike(user, Integer.parseInt(commentId));
    }

    @RequestMapping("/comment/remove-like/{commentId}")
    public void removeCommentLike(Principal principal, @PathVariable String commentId) {
        User user = (User) ((Authentication) principal).getPrincipal();

        commentService.removeCommentLike(user, Integer.parseInt(commentId));
    }

}
