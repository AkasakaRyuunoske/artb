package com.maid.cafe.c.maidcafec.JWT;

import com.maid.cafe.c.maidcafec.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    private com.maid.cafe.c.maidcafec.POJO.User userDetails;  // To not confuse with spring security's user class
    // In this case email and username are the same
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetails = userDAO.findByEmailId(username);
        if (!Objects.isNull(userDetails)){
            return new User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public com.maid.cafe.c.maidcafec.POJO.User getUserDetails(){
        return userDetails;
    }
}
