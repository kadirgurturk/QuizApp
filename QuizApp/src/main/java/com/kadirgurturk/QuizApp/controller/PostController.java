package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.request.PostRequests.PostSave;
import com.kadirgurturk.QuizApp.buisness.request.PostRequests.PostUpdate;
import com.kadirgurturk.QuizApp.buisness.service.PostService;
import com.kadirgurturk.QuizApp.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("quiz/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId)
    {
        return postService.findAll(userId);
    }

    @PostMapping
    public Post savePost(@RequestBody PostSave newPost)
    {
        return postService.save(newPost);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId)
    {
        return postService.findById(postId).orElseThrow(() -> new RuntimeException("Post blunamado"));
    }

    @PutMapping("/{postId}")
    public Post updatePostById(@PathVariable Long postId,@RequestBody PostUpdate postUpdate)
    {
        return postService.updatePostById(postId, postUpdate);
    }

    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable Long postId)
    {
        postService.deleteById(postId);
    }



}
