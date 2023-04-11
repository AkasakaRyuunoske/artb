package com.maid.cafe.c.maidcafec.RESTImplementation;

import com.maid.cafe.c.maidcafec.Constants.CafeConstant;
import com.maid.cafe.c.maidcafec.POJO.Category;
import com.maid.cafe.c.maidcafec.REST.CategoryRest;
import com.maid.cafe.c.maidcafec.Service.CategoryService;
import com.maid.cafe.c.maidcafec.Utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {
    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            return categoryService.addNewCategory(requestMap);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); //todo change error
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            return categoryService.getAllCategory(filterValue);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR); //todo change error
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            return categoryService.updateCategory(requestMap);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); //todo change error
    }
}
