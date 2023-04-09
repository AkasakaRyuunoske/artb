package com.maid.cafe.c.maidcafec.RESTImplementation;

import com.maid.cafe.c.maidcafec.Constants.CafeConstant;
import com.maid.cafe.c.maidcafec.REST.UserRest;
import com.maid.cafe.c.maidcafec.Service.UserService;
import com.maid.cafe.c.maidcafec.Utils.CafeUtils;
import com.maid.cafe.c.maidcafec.Wrapper.UserWrapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{

            return userService.signUp(requestMap);

        } catch (Exception exception){
            exception.printStackTrace();
        }

        // Todo return something meaningful
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        // Todo return something meaningful
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            return userService.getAllUser();
        } catch (Exception exception){
            exception.printStackTrace();
        } //Todo return something meaningful
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
