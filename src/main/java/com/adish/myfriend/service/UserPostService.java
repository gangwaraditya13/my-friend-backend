package com.adish.myfriend.service;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.entities.UserPost;
import com.adish.myfriend.repository.UserPostRepository;
import com.adish.myfriend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPostRepository userPostRepository;

    //first complite user
    @Transactional
    public void saveNewPost(UserPost userPost, String userName){
        User byUserName = userRepository.findByUserName(userName);
        userPost.setDate(LocalDateTime.now());
        userPostRepository.save(userPost);
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
                        if(!userPost.getPhotoURL().equals(userExistingPost.getPhotoURL())){
                            userExistingPost.setPhotoURL(userPost.getPhotoURL());
                        }
                        userPostRepository.save(userExistingPost);
                    }
                }
            }
    }

}
