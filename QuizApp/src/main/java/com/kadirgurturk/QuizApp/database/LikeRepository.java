package com.kadirgurturk.QuizApp.database;

import com.kadirgurturk.QuizApp.entity.Like;
import com.kadirgurturk.QuizApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
