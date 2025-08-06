package com.adish.myfriend.service;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.repository.UserPostRepository;
import com.adish.myfriend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPostRepository userPostRepository;

    public List<User> getAllUser(){
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    @Transactional
    public boolean updateUserRoll(String userName){
        User checkUser = userRepository.findByUserName(userName);
        try{
            if (checkUser.getUserName().equals(userName)) {
                    checkUser.setRoll(Arrays.asList("USER", "ADMIN"));
                    userRepository.save(checkUser);
                    return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            log.error("Exception in Admin Service {}", LocalDateTime.now(),e);
            return false;
        }
    }
}
