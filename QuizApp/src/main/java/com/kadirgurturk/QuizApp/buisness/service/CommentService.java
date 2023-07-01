package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.buisness.dto.CommentDto;
import com.kadirgurturk.QuizApp.buisness.request.CommentRequests.CommentSave;
import com.kadirgurturk.QuizApp.buisness.request.CommentRequests.CommentUpdate;
import com.kadirgurturk.QuizApp.database.CommentRepository;
import com.kadirgurturk.QuizApp.entity.Comment;
import com.kadirgurturk.QuizApp.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, @Lazy  PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentDto> findAll(Optional<Long> userId, Optional<Long> postId) {

        if(userId.isPresent() && postId.isPresent()){
            return StreamSupport.stream(commentRepository.findByUserIdAndPostId(userId.get(),postId.get()).spliterator(),false)
                    .map(CommentDto::new)
                    .collect(Collectors.toList());
        }else if(userId.isPresent()){

            return StreamSupport.stream( commentRepository.findByUserId(userId.get()).spliterator(),false)
                    .map(CommentDto::new)
                    .collect(Collectors.toList());

        }else if(postId.isPresent()){
            return StreamSupport.stream(commentRepository.findByPostId(postId.get()).spliterator(),false)
                    .map(CommentDto::new)
                    .collect(Collectors.toList());
        }
        return StreamSupport.stream(commentRepository.findAll().spliterator(),false)
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    public Comment save(CommentSave newComment) {

        var user = userService.findById(newComment.getUser_id());
        var post = postService.findById(newComment.getPost_ıd());
        if(user.isPresent() && post.isPresent()){
            var saveComment = new Comment();
            saveComment.setText(newComment.getText());
            saveComment.setPost(post.get());
            saveComment.setUser(user.get());

            commentRepository.save(saveComment);

            return saveComment;
        }

        return null;

    }

    public CommentDto findById(Long CommentId) {

        var comment = commentRepository.findById(CommentId).get();

        return new CommentDto(comment);
    }

    public void deleteById(Long CommentId) {

        commentRepository.deleteById(CommentId);
    }

    public CommentDto updateCommentById(Long commentId, CommentUpdate commentUpdate)
    {
        Optional<Comment> foundComment = commentRepository.findById(commentId);

        if(foundComment.isPresent()){
            var updateComment = foundComment.get();
            updateComment.setText(commentUpdate.getText());

            commentRepository.save(updateComment);

            return new CommentDto(updateComment);
        }

        return null;


    }
}
