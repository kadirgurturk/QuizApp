package com.kadirgurturk.QuizApp.buisness.request.CommentRequests;

import lombok.Data;

@Data
public class CommentSave {


    String text;
    Long post_ıd;
    Long user_id;

}
