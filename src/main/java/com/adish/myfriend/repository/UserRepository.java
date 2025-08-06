package com.adish.myfriend.repository;
import com.adish.myfriend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,Object> {
    User findByUserName(String username);
}
