package com.kadirgurturk.QuizApp.buisness.request.PostRequests;

import lombok.Data;

@Data
public class PostSave {

    Long id;
    String text;
    String title;
    Long user_id;
}
