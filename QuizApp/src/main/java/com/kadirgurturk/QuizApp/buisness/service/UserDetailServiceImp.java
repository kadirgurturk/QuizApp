package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.database.UserRepository;
import com.kadirgurturk.QuizApp.entity.User;
import com.kadirgurturk.QuizApp.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));


        return JwtUserDetails.create(user);
    }

    public void userTest(){
        System.out.println("sadas");
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with user: " ));


        return JwtUserDetails.create(user);
    }
}
