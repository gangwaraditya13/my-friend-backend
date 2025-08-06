package com.adish.myfriend.service;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public boolean saveNewUser(User newUser){
        User checkUser = userRepository.findByUserName(newUser.getUserName());
        if (checkUser.getUserName().equals(newUser.getUserName())) {
            return false;
        }
        else {
            newUser.setPassword(PASSWORD_ENCODER.encode(newUser.getPassword()));
            newUser.setRoll(Arrays.asList("USER"));
            userRepository.save(newUser);
            return true;
        }
    }

    public boolean saveUser(User user){
        User checkUser = userRepository.findByUserName(user.getUserName());
        if(checkUser.getUserName().equals(user.getUserName())){
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void updateUser(User user,String userName){
        User existingUser = userRepository.findByUserName(userName);
        String existingPassword = PASSWORD_ENCODER.encode(existingUser.getPassword());
        if(!existingUser.getUserName().equals(user.getUserName())){
            existingUser.setUserName(user.getUserName());
        }
        if(!existingPassword.equals(user.getPassword())){
            existingUser.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        }
        userRepository.save(existingUser);
    }
}
