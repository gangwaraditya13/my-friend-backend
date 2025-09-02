package com.adish.myfriend.service;

import com.adish.myfriend.Component.NewPhotoURL;
import com.adish.myfriend.Component.PasswordReset;
import com.adish.myfriend.Component.UpdateGmailOrUserName;
import com.adish.myfriend.Component.responceUser;
import com.adish.myfriend.entities.User;
import com.adish.myfriend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Transactional
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

    public boolean updatePassword(PasswordReset passwordReset, String userName){
        User existingUser = userRepository.findByUserName(userName);
        boolean existingPassword = PASSWORD_ENCODER.matches(passwordReset.getOldPassword(),existingUser.getPassword());
        String encodeNewPassword = PASSWORD_ENCODER.encode(passwordReset.getNewPassword());
        if(existingPassword){
            existingUser.setPassword(encodeNewPassword);
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    public boolean updatePhotoURL(NewPhotoURL newPhotoURL, String userName){
        User existingUser = userRepository.findByUserName(userName);
        if(newPhotoURL.getOldPhoto() == null || newPhotoURL.getOldPhoto().isEmpty() || !existingUser.getProfilePhotoURL().equals(newPhotoURL.getNewPhoto()) ){
            if(existingUser.getProfileProductId() == null || existingUser.getProfileProductId().isEmpty()) {
                existingUser.setProfileProductId(newPhotoURL.getProductId());
                existingUser.setProfilePhotoURL(newPhotoURL.getNewPhoto());
            }else {
                cloudinaryImageService.deleteImage(existingUser.getProfileProductId());
                existingUser.setProfileProductId(newPhotoURL.getProductId());
                existingUser.setProfilePhotoURL(newPhotoURL.getNewPhoto());
            }
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    public boolean updateUser(UpdateGmailOrUserName user, String userName) {
        User existingUser = userRepository.findByUserName(userName);
        if(existingUser.getGmailId() == null || !existingUser.getGmailId().equals(user.getGmailId())){
            existingUser.setGmailId(user.getGmailId());
            userRepository.save(existingUser);
            return true;
        }
        if(!existingUser.getUserName().equals(user.getUserName())){
            User checkUser = userRepository.findByUserName(user.getUserName());
            if(checkUser == null){
                existingUser.setUserName(user.getUserName());
                userRepository.save(existingUser);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String password, String userName){
        User user = userRepository.findByUserName(userName);
        boolean userPassword = PASSWORD_ENCODER.matches(password,user.getPassword());
        if(userPassword){
            if(!user.getProductIds().isEmpty()){
                cloudinaryImageService.deleteAllImages(user.getProductIds());
            }
            userRepository.deleteByUserName(userName);
            return true;
        }else{
            return false;
        }
    }

    public boolean checkuser(String userName, String password) {
        boolean loginState = false;
        User checkUserExisist = userRepository.findByUserName(userName);
        boolean userPassword = PASSWORD_ENCODER.matches(password,checkUserExisist.getPassword());
        if(checkUserExisist.getUserName().equals(userName) && userPassword){
            loginState = true;
        }
        return loginState;
    }

    public responceUser getInfo(String userName) {
        User user = userRepository.findByUserName(userName);
        responceUser demoUser = new responceUser();
        if(user != null) {
            demoUser.setUserName(user.getUserName());
            demoUser.setId(user.getId());
            demoUser.setGmailId(user.getGmailId());
            demoUser.setProfilePhotoURL(user.getProfilePhotoURL());
        }
        return demoUser;
    }


}
