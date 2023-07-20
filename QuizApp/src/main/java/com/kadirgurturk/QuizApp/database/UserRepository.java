package com.kadirgurturk.QuizApp.database;

import com.kadirgurturk.QuizApp.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);


    Optional<User> findByUserName(String userName);
}
