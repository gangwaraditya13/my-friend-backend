package com.adish.myfriend.repository;
import com.adish.myfriend.entities.UserPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPostRepository extends MongoRepository<UserPost,Object> {
}
