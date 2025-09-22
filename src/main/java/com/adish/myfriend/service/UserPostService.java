package com.adish.myfriend.service;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.entities.UserPost;
import com.adish.myfriend.repository.UserPostRepository;
import com.adish.myfriend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserPostService {

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPostRepository userPostRepository;

    @Transactional
    public boolean saveNewPost(UserPost userPost, String userName){
        boolean checkSave = false;
        try {
            User user = userRepository.findByUserName(userName);
            if(userPost.getDate().isEmpty()){
                userPost.setDate(LocalDateTime.now().toString());
            }
            if((userPost.getTitle() != null) && !userPost.getTitle().isEmpty()) {
                UserPost saved = userPostRepository.save(userPost);
                if(user.getProductIds().isEmpty()){

                }
                user.getProductIds().add( userPost.getProductId());
                user.getUserPostsList().add(saved);
                userRepository.save(user);
                checkSave = true;
            }
            else{
                log.error("title is empty in body");
            }
            return checkSave;
        } catch (Exception e) {
            log.error("Exception in user post service on {}",LocalDateTime.now(),e);
            throw new RuntimeException(e);
        }
    }

    public void updatePost(UserPost userPost, String userName, ObjectId userPostId){
        User checkUser = userRepository.findByUserName(userName);
            if (checkUser.getUserName().equals(userName)) {
                List<UserPost> collect = checkUser.getUserPostsList()
                        .stream()
                        .filter(x -> x.getId().equals(userPostId))
                        .collect(Collectors.toList());
                
                if(!collect.isEmpty()){
                    Optional<UserPost> userPostExisting = userPostRepository.findById(userPostId);
                    if(userPostExisting.isPresent()) {
                        UserPost userExistingPost = userPostExisting.get();
                        if(!userPost.getTitle().equals(userExistingPost.getTitle())){
                            userExistingPost.setTitle(userPost.getTitle());
                        }
                        if(!userPost.getContent().equals(userExistingPost.getContent())){
                            userExistingPost.setContent(userPost.getContent());
                        }
                        if(!userPost.getTitleColor().equals(userExistingPost.getTitleColor())){
                            userExistingPost.setTitleColor(userPost.getTitleColor());
                        }
                        if(!userPost.getPhotoURL().equals(userExistingPost.getPhotoURL())){
                            cloudinaryImageService.deleteImage(userExistingPost.getProductId());
                            userExistingPost.setProductId(userPost.getProductId());
                            userExistingPost.setPhotoURL(userPost.getPhotoURL());
                        }
                        userPostRepository.save(userExistingPost);
                    }
                }
            }
    }

    @Transactional
    public boolean deletePostById(ObjectId postId, String userName) {
        boolean removed = false;
        User user = userRepository.findByUserName(userName);

        try {
            removed = user.getUserPostsList().removeIf(x -> x.getId().equals(postId));
            if (removed) {
                userRepository.save(user);
                Optional<UserPost> userPost = userPostRepository.findById(postId);
                if(!userPost.get().getProductId().isEmpty()) {
                    cloudinaryImageService.deleteImage(userPost.get().getProductId());
                }
                userPostRepository.deleteById(postId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return removed;
    }

    public List<UserPost> getAllUserPost(String userName) {
        User user = userRepository.findByUserName(userName);
        List<UserPost> userAllPostsList = user.getUserPostsList();
        return userAllPostsList;
    }

    public Optional<UserPost> getUserPost(String userName, ObjectId postId) {
        User user = userRepository.findByUserName(userName);
        Optional<UserPost> userPost = userPostRepository.findById(postId);
        return userPost;
    }
}
