package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.dto.LikeDto;
import com.kadirgurturk.QuizApp.buisness.request.LikeRequest.LikeSave;
import com.kadirgurturk.QuizApp.buisness.service.LikeService;
import com.kadirgurturk.QuizApp.entity.Like;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("quiz/api/v1/likes/")
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;

    @GetMapping
    public List<LikeDto> getAllLikes(
            @RequestParam(value = "user") Optional<Long> userId,
            @RequestParam(value = "post") Optional<Long> postId
    )
    {
        return likeService.findAll(userId,postId);
    }

    @PostMapping
    public LikeSave createLike(@RequestBody LikeSave newLike)
    {
        return likeService.save(newLike);
    }

    @GetMapping("/{likeId}")
    public LikeDto getLikeById(@PathVariable Long likeId)
    {
        return likeService.findById(likeId);
    }


    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId)
    {
        likeService.deleteById(likeId);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByPostIdAndUserId(
            @RequestParam(value = "user") Long userId,
            @RequestParam(value = "post") Long postId
    )
    {
        likeService.deleteByPostIdAndUserId(postId,userId);

        return ResponseEntity.ok("Silinid" + postId + " " + userId);
    }



}
