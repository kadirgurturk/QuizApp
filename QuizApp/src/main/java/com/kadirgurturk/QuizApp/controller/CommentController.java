package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.request.CommentRequests.CommentSave;
import com.kadirgurturk.QuizApp.buisness.request.CommentRequests.CommentUpdate;
import com.kadirgurturk.QuizApp.buisness.service.CommentService;
import com.kadirgurturk.QuizApp.entity.Comment;
import com.kadirgurturk.QuizApp.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("quiz/api/v1/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments(
            @RequestParam Optional<Long> userId,
            @RequestParam Optional<Long> postId
            )
    {
        return commentService.findAll(userId,postId);
    }

    @PostMapping
    public Comment createComment(@RequestBody CommentSave newComment)
    {
        return commentService.save(newComment);
    }

    @GetMapping("/{postId}")
    public Comment getCommentById(@PathVariable Long postId)
    {
        return commentService.findById(postId).orElseThrow(() -> new RuntimeException("Post blunamado"));
    }

    @PutMapping("/{postId}")
    public Comment updateCommentById(@PathVariable Long commentId,@RequestBody CommentUpdate commentUpdate)
    {
        return commentService.updateCommentById(commentId,commentUpdate);
    }

    @DeleteMapping("/{postId}")
    public void deleteOneComment(@PathVariable Long postId)
    {
        commentService.deleteById(postId);
    }



}
