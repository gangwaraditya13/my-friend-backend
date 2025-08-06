package com.adish.myfriend.service;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null){
            UserDetails userDetails = org.springframework.security.core.userdetails
                    .User
                    .builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoll().toArray(new String[0])).build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found"+username);
    }
}
