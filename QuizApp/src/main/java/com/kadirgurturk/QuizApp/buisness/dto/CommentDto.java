package com.kadirgurturk.QuizApp.buisness.dto;

import com.kadirgurturk.QuizApp.entity.Comment;
import lombok.Data;

@Data
public class CommentDto {

    Long id;
    Long userId;
    Integer avatarId;
    String userName;
    Long postId;
    String text;

    public CommentDto(Comment entity) {
        id = entity.getId();
        userId = entity.getUser().getId();
        avatarId = entity.getUser().getAvatar_id();
        userName = entity.getUser().getUserName();
        postId = entity.getPost().getId();
        text = entity.getText();

    }
}
