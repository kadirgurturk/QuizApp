package com.kadirgurturk.QuizApp.buisness.dto;

import com.kadirgurturk.QuizApp.entity.Post;
import lombok.Data;

@Data
public class PostDto {

    Long id;
    Long userId;
    String userName;
    String title;
    String text;

    public PostDto(Post entity){
        id = entity.getId();
        userId = entity.getUser().getId();
        userName = entity.getUser().getUserName();
        title = entity.getTitle();
        text = entity.getText();

    }
}
