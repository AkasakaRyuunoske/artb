package com.maid.cafe.c.maidcafec.ServiceImplementation;

import com.google.common.base.Strings;
import com.maid.cafe.c.maidcafec.Constants.CafeConstant;
import com.maid.cafe.c.maidcafec.DAO.UserDAO;
import com.maid.cafe.c.maidcafec.JWT.CustomUserDetailsService;
import com.maid.cafe.c.maidcafec.JWT.JWTFilter;
import com.maid.cafe.c.maidcafec.JWT.JWTUtils;
import com.maid.cafe.c.maidcafec.POJO.User;
import com.maid.cafe.c.maidcafec.Service.UserService;
import com.maid.cafe.c.maidcafec.Utils.CafeUtils;
import com.maid.cafe.c.maidcafec.Utils.EmailUtils;
import com.maid.cafe.c.maidcafec.Wrapper.UserWrapper;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

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
    EmailUtils emailUtils;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    JWTFilter jwtFilter;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Sign up: {}", requestMap);

        try {

            if (validateSignUpMap(requestMap)) {
                User user = userDAO.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDAO.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully registered.", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.BAD_REQUEST);
    }

    //todo make more precise what is missing in the request
    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestMap) {
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
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (authentication.isAuthenticated()) {
                if (customUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtils.generateToken(customUserDetailsService.getUserDetails().getEmail(), customUserDetailsService.getUserDetails().getRole()), HttpStatus.OK);
                }
                return new ResponseEntity<String>("{\"message\":\"" + "wait for Admin approval." + "\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            log.error("The issue is: " + exception);
        }

        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials." + "\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDAO.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // Todo same here
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optional = userDAO.findById(Integer.parseInt(requestMap.get("id")));
                if (optional.isPresent()) {
                    userDAO.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDAO.getAllAdmin());
                    return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.OK); // Todo yeh yeh this one too must be changed
                } else {
                    CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.NOT_FOUND); // Todo change
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.FUSAGA_RE_TA_MICCHI_WO_HIRAKU_MONO_NI_NARU, HttpStatus.UNAUTHORIZED); // todo change this one
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); // todo change this one too
    }

    // Not sure about this one. So if the token is valid returns true, if not user gets 403 and does not reach this one
    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("true", HttpStatus.OK); // Todo change change change change change change change change change change change change change change change change
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User user = userDAO.findByEmail(jwtFilter.getCurrentUser());

            if (user != null) {
                if (user.getPassword().equals(requestMap.get("oldPassword"))) {
                    user.setPassword(requestMap.get("newPassword"));
                    userDAO.save(user);

                    return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); // Password is ok // todo obvs
                }
                return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.BAD_REQUEST); // Incorrect old password // todo obvs
            }
            return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); // todo obvs

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.UNEXPECTED_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Todo set up mail
    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDAO.findByEmail(requestMap.get("email"));

            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
                emailUtils.forgotMail(user.getEmail(), "Credentials by cringe", user.getPassword());
            }

            // Even if email does not exists user must not know about it
            return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); // Check yo mail for credentials// Todo this one too must be changed later

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); // Todo this one too must be changed later
    }

    //Todo set up mail
    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());

        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),
                    "Account approved",
                    "USER:- " + user + "\n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(),
                    allAdmin); // Todo setup mail
        } else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),
                    "Account approved",
                    "USER:- " + user + "\n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(),
                    allAdmin);
        }
    }
}
