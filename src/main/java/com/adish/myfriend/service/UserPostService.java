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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserPostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPostRepository userPostRepository;

    @Transactional
    public void saveNewPost(UserPost userPost, String userName){
        try {
            User user = userRepository.findByUserName(userName);
            userPost.setDate(LocalDateTime.now());
            UserPost saved = userPostRepository.save(userPost);
            user.getUserPostsList().add(saved);
            userRepository.save(user);
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
                        if(!userPost.getPhotoURL().equals(userExistingPost.getPhotoURL())){
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
                userPostRepository.deleteById(postId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return removed;
    }
}
