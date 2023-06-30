package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.buisness.dto.PostDto;
import com.kadirgurturk.QuizApp.buisness.request.PostRequests.PostSave;
import com.kadirgurturk.QuizApp.buisness.request.PostRequests.PostUpdate;
import com.kadirgurturk.QuizApp.database.PostRespository;
import com.kadirgurturk.QuizApp.entity.Post;
import com.kadirgurturk.QuizApp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private PostRespository postRepository;
    private UserService userService;

    public List<PostDto> findAll(Optional<Long> userId) {

        List<Post> posts;

        if(userId.isPresent()){
            posts =  postRepository.findByUserId(userId.get());
        }

        posts = postRepository.findAll();

        return posts.stream().map(i -> new PostDto(i)).collect(Collectors.toList());

    }

    public Post save( PostSave postSave) {

        User user = userService.findById(postSave.getUser_id()).get();
        if(user == null) return null;

        Post newPost = new Post();
        newPost.setTitle(postSave.getTitle());
        newPost.setText(postSave.getText());
        newPost.setUser(user);

        postRepository.save(newPost);

        return newPost;
    }

    public Optional<Post> findById(Long postId) {

        return postRepository.findById(postId);
    }

    public void deleteById(Long postId) {

        postRepository.deleteById(postId);
    }

    public Post updatePostById(Long postId, PostUpdate postUpdate)
    {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            var updatePost = post.get();
            updatePost.setText(postUpdate.getText());
            updatePost.setTitle(postUpdate.getTitle());

            postRepository.save(updatePost);

            return updatePost;
        }


        return null;
    }
}
