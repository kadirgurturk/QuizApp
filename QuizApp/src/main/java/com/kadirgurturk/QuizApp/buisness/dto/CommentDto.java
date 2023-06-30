package com.kadirgurturk.QuizApp.buisness.dto;

import com.kadirgurturk.QuizApp.entity.Comment;
import lombok.Data;

@Data
public class CommentDto {

    Long id;
    Long userId;
    String userName;
    Long postId;
    String text;

    public CommentDto(Comment entity) {
        id = entity.getId();
        userId = entity.getUser().getId();
        userName = entity.getUser().getUserName();
        postId = entity.getPost().getId();
        text = entity.getText();

    }
}
