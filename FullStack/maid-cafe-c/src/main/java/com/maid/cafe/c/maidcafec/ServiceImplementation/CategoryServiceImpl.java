package com.maid.cafe.c.maidcafec.ServiceImplementation;

import com.google.common.base.Strings;
import com.maid.cafe.c.maidcafec.Constants.CafeConstant;
import com.maid.cafe.c.maidcafec.DAO.CategoryDAO;
import com.maid.cafe.c.maidcafec.JWT.JWTFilter;
import com.maid.cafe.c.maidcafec.POJO.Category;
import com.maid.cafe.c.maidcafec.Service.CategoryService;
import com.maid.cafe.c.maidcafec.Utils.CafeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    JWTFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateCategoryMap(requestMap, false)){
                    categoryDAO.save(getCategoryFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); //todo change
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.UNAUTHORIZED); //todo change error
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); //todo change error
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Filter applied.");
                return new ResponseEntity<List<Category>>(categoryDAO.getAllCategory(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(categoryDAO.findAll(), HttpStatus.OK);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateCategoryMap(requestMap, true)){
                    Optional optional = categoryDAO.findById(Integer.parseInt(requestMap.get("id")));
                    if (optional.isPresent()){
                        categoryDAO.save(getCategoryFromMap(requestMap, true));
                        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); // todo successful update
                    } else {
                        return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.NOT_FOUND); //todo does not exists
                    }
                }
                return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.BAD_REQUEST);
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.FUSAGA_RE_TA_MICCHI_WO_HIRAKU_MONO_NI_NARU, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); //todo change error
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            } else  if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Category category = new Category();
        if (isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }

        category.setName(requestMap.get("name"));
        return category;
    }
}
