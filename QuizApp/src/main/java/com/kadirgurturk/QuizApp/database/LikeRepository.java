package com.kadirgurturk.QuizApp.database;

import com.kadirgurturk.QuizApp.entity.Like;
import com.kadirgurturk.QuizApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);

}
