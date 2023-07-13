package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.database.UserRepository;
import com.kadirgurturk.QuizApp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User newUser) {

        return userRepository.save(newUser);
    }

    public Optional<User> findById(Long userId) {

        return userRepository.findById(userId);
    }

    public Optional<User> findByName(String userName) {

        return userRepository.findByUserName(userName);
    }

    public void deleteById(Long userId) {

        userRepository.deleteById(userId);
    }

    public User updateAvatarById(Long userId, Integer avatarId){
        Optional<User> userFound = userRepository.findById(userId);
        if(userFound.isPresent()){
            var userNew = userFound.get();

            userNew.setAvatar_id(avatarId);

            userRepository.save(userNew);
            return userNew;
        }else {
            return null;
        }
    }

    public User updateUserById(Long userId, User user)
    {
        Optional<User> userFound = userRepository.findById(userId);
        if(userFound.isPresent()){
            var userNew = userFound.get();
            userNew.setUserName(user.getUserName());
            userNew.setPassword(user.getPassword());
            userNew.setAvatar_id(user.getAvatar_id());
            userRepository.save(userNew);
            return userNew;
        }else {
            return null;
        }
    }
}
