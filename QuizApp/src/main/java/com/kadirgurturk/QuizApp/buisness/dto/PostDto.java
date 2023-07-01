package com.kadirgurturk.QuizApp.buisness.dto;

import com.kadirgurturk.QuizApp.entity.Like;
import com.kadirgurturk.QuizApp.entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {

    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    List<LikeDto> likes;

    public PostDto(Post entity, List<LikeDto> likes){
        id = entity.getId();
        userId = entity.getUser().getId();
        userName = entity.getUser().getUserName();
        title = entity.getTitle();
        text = entity.getText();
        this.likes = likes;

    }
}
