package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.database.LikeRepository;
import com.kadirgurturk.QuizApp.database.UserRepository;
import com.kadirgurturk.QuizApp.entity.Like;
import com.kadirgurturk.QuizApp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {

    private LikeRepository likeRepository;

    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    public Like save(Like newLike) {

        return likeRepository.save(newLike);
    }

    public Optional<Like> findById(Long LikeId) {

        return likeRepository.findById(LikeId);
    }

    public void deleteById(Long LikeId) {

        likeRepository.deleteById(LikeId);
    }

}
