package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.service.LikeService;
import com.kadirgurturk.QuizApp.entity.Like;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz/api/v1/likes/")
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;

    @GetMapping
    public List<Like> getAllLikes()
    {
        return likeService.findAll();
    }

    @PostMapping
    public Like createLike(@RequestBody Like newLike)
    {
        return likeService.save(newLike);
    }

    @GetMapping("/{likeId}")
    public Like getLikeById(@PathVariable Long likeId)
    {
        return likeService.findById(likeId).orElseThrow(() -> new RuntimeException("Post blunamado"));
    }


    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId)
    {
        likeService.deleteById(likeId);
    }



}
