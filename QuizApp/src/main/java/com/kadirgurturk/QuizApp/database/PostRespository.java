package com.kadirgurturk.QuizApp.database;

import com.kadirgurturk.QuizApp.entity.Like;
import com.kadirgurturk.QuizApp.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public interface PostRespository extends JpaRepository<Post, Long> {

    @Override
    Optional<Post> findById(Long userId);

    List<Post> findByUserId(Long userId);
}
