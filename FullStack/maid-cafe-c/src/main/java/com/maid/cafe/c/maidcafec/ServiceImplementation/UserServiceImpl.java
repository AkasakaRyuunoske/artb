package com.maid.cafe.c.maidcafec.ServiceImplementation;

import com.maid.cafe.c.maidcafec.Constants.CafeConstant;
import com.maid.cafe.c.maidcafec.DAO.UserDAO;
import com.maid.cafe.c.maidcafec.JWT.CustomUserDetailsService;
import com.maid.cafe.c.maidcafec.JWT.JWTFilter;
import com.maid.cafe.c.maidcafec.JWT.JWTUtils;
import com.maid.cafe.c.maidcafec.POJO.User;
import com.maid.cafe.c.maidcafec.Service.UserService;
import com.maid.cafe.c.maidcafec.Utils.CafeUtils;
import com.maid.cafe.c.maidcafec.Wrapper.UserWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    JWTFilter jwtFilter;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Sign up: {}" , requestMap);

        try {

            if (validateSignUpMap(requestMap)){
                User user = userDAO.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)){
                    userDAO.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully registered.", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.BAD_REQUEST);
    }

    //todo make more precise what is missing in the request
    private boolean validateSignUpMap(Map<String, String> requestMap){
        return requestMap.containsKey("name") &&
               requestMap.containsKey("contactNumber") &&
               requestMap.containsKey("email") &&
               requestMap.containsKey("password");
    }

    private User getUserFromMap(Map <String, String> requestMap){
        User user = new User();

        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");

        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (authentication.isAuthenticated()) {
                if (customUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtils.generateToken(customUserDetailsService.getUserDetails().getEmail(), customUserDetailsService.getUserDetails().getRole()), HttpStatus.OK);
                }
                return new ResponseEntity<String>("{\"message\":\"" + "wait for Admin approval." + "\"}", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception exception){
            log.error("The issue is: " + exception);
        }

        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials." + "\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDAO.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        // Todo same here
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
