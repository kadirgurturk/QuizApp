package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.dto.CommentDto;
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
@RequestMapping("quiz/api/v1/comments/")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllComments(
            @RequestParam(value = "user") Optional<Long> userId,
            @RequestParam(value = "post") Optional<Long> postId
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
    public CommentDto getCommentById(@PathVariable Long postId)
    {
        return commentService.findById(postId);
    }

    @PutMapping("/{postId}")
    public CommentDto updateCommentById(@PathVariable Long commentId,@RequestBody CommentUpdate commentUpdate)
    {
        return commentService.updateCommentById(commentId,commentUpdate);
    }

    @DeleteMapping("/{postId}")
    public void deleteOneComment(@PathVariable Long postId)
    {
        commentService.deleteById(postId);
    }



}
