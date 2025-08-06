package com.adish.myfriend.service;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    
    public boolean saveNewUser(User newUser){
        try {
            newUser.setPassword(PASSWORD_ENCODER.encode(newUser.getPassword()));
            newUser.setRoll(Arrays.asList("USER"));
            userRepository.save(newUser);
            return true;
        } catch (Exception e) {
            log.error("Exception in User Service",e);
            return false;
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

    public boolean deleteUser(String password, String userName){
        password = PASSWORD_ENCODER.encode(password);
        User byUserName = userRepository.findByUserName(userName);
        if(password.equals(byUserName.getPassword())){
            userRepository.deleteByUserName(userName);
            return true;
        }else{
            return false;
        }
    }
}
