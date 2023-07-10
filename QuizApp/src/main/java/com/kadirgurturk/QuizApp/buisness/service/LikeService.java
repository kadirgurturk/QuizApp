package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.buisness.dto.CommentDto;
import com.kadirgurturk.QuizApp.buisness.dto.LikeDto;
import com.kadirgurturk.QuizApp.buisness.request.LikeRequest.LikeSave;
import com.kadirgurturk.QuizApp.database.LikeRepository;
import com.kadirgurturk.QuizApp.database.UserRepository;
import com.kadirgurturk.QuizApp.entity.Comment;
import com.kadirgurturk.QuizApp.entity.Like;
import com.kadirgurturk.QuizApp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Lazy
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, @Lazy PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeDto> findAll(Optional<Long> userId, Optional<Long> postId) {

        if(userId.isPresent() && postId.isPresent()){
            return StreamSupport.stream(likeRepository.findByUserIdAndPostId(userId.get(),postId.get()).spliterator(),false)
                    .map(LikeDto::new)
                    .collect(Collectors.toList());
        }else if(userId.isPresent()){

            return StreamSupport.stream( likeRepository.findByUserId(userId.get()).spliterator(),false)
                    .map(LikeDto::new)
                    .collect(Collectors.toList());

        }else if(postId.isPresent()){
            return StreamSupport.stream(likeRepository.findByPostId(postId.get()).spliterator(),false)
                    .map(LikeDto::new)
                    .collect(Collectors.toList());
        }
        return StreamSupport.stream(likeRepository.findAll().spliterator(),false)
                .map(LikeDto::new)
                .collect(Collectors.toList());
    }

    public LikeSave save(LikeSave newLike) {



        var user = userService.findById(newLike.getUser_id());
        var post = postService.findById(newLike.getPost_ıd());
        if(user.isPresent() && post.isPresent()){

            if(findAll(Optional.of(user.get().getId()),Optional.of(post.get().getId())).isEmpty()){
                var like = new Like();
                like.setPost(post.get());
                like.setUser(user.get());

                likeRepository.save(like);

                return newLike;
            }
        }

        return null;

    }

    public LikeDto findById(Long LikeId) {

        if(likeRepository.findById(LikeId).isPresent()){
            return new LikeDto(likeRepository.findById(LikeId).get());
        }

        return null;
    }

    public void deleteById(Long LikeId) {

        likeRepository.deleteById(LikeId);
    }

    public void deleteByPostIdAndUserId(Long postId, Long userId){
        likeRepository.deleteByPostIdAndUserId(postId,userId);
    }

}
