package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.buisness.request.CommentRequests.CommentSave;
import com.kadirgurturk.QuizApp.buisness.request.CommentRequests.CommentUpdate;
import com.kadirgurturk.QuizApp.database.CommentRepository;
import com.kadirgurturk.QuizApp.entity.Comment;
import com.kadirgurturk.QuizApp.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public List<Comment> findAll(Optional<Long> userId, Optional<Long> postId) {

        if(userId.isPresent() && postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }else if(userId.isPresent()){
            return commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()){
            return commentRepository.findByPostId(postId.get());
        }

        return commentRepository.findAll();
    }

    public Comment save(CommentSave newComment) {

        var user = userService.findById(newComment.getUser_id());
        var post = postService.findById(newComment.getPost_ıd());
        if(user.isPresent() && post.isPresent()){
            var saveComment = new Comment();
            saveComment.setText(newComment.getText());
            saveComment.setId(newComment.getId());
            saveComment.setPost(post.get());
            saveComment.setUser(user.get());

            commentRepository.save(saveComment);

            return saveComment;
        }

        return null;

    }

    public Optional<Comment> findById(Long CommentId) {

        return commentRepository.findById(CommentId);
    }

    public void deleteById(Long CommentId) {

        commentRepository.deleteById(CommentId);
    }

    public Comment updateCommentById(Long commentId, CommentUpdate commentUpdate)
    {
        Optional<Comment> foundComment = commentRepository.findById(commentId);

        if(foundComment.isPresent()){
            var updateComment = foundComment.get();
            updateComment.setText(commentUpdate.getText());

            commentRepository.save(updateComment);

            return updateComment;
        }

        return null;


    }
}
