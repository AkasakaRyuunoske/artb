package com.maid.cafe.c.maidcafec.ServiceImplementation;

import com.maid.cafe.c.maidcafec.Constants.CafeConstant;
import com.maid.cafe.c.maidcafec.DAO.ProductDAO;
import com.maid.cafe.c.maidcafec.JWT.JWTFilter;
import com.maid.cafe.c.maidcafec.POJO.Category;
import com.maid.cafe.c.maidcafec.POJO.Product;
import com.maid.cafe.c.maidcafec.Service.ProductService;
import com.maid.cafe.c.maidcafec.Utils.CafeUtils;
import com.maid.cafe.c.maidcafec.Wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    JWTFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDAO.save(getProductFromMap(requestMap, false));

                    return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); // todo product added successfully
                }

                return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.BAD_REQUEST); //todo invalid data

            } else {

                return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.UNAUTHORIZED); //todo change
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); // todo change
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDAO.getAllProduct(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR); //todo must be changed
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, true)) {
                    Optional<Product> optional = productDAO.findById(Integer.parseInt(requestMap.get("id")));
                    if (optional.isPresent()) {
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());

                        productDAO.save(product);

                        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); // todo
                    } else {
                        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.NOT_FOUND); // todo
                    }
                } else {
                    return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST); // todo
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.FUSAGA_RE_TA_MICCHI_WO_HIRAKU_MONO_NI_NARU, HttpStatus.UNAUTHORIZED); // todo
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); //todo must be changed
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Product> optional = productDAO.findById(id);

                if (optional.isPresent()) {
                    productDAO.deleteById(id);

                    return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); // todo
                } else {
                    return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.NOT_FOUND); // todo
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.UNAUTHORIZED); // todo
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); // todo
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Product> optional = productDAO.findById(Integer.parseInt(requestMap.get("id")));

                if (optional.isPresent()) {
                    productDAO.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));

                    return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.OK); // todo
                } else {
                    return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.NOT_FOUND); // todo
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.UNAUTHORIZED); // todo
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.INTERNAL_SERVER_ERROR); // todo
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(productDAO.getProductByCategory(id), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR); //todo must be changed
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDAO.getProductById(id), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR); //todo must be changed
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        Product product = new Product();

        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        return product;
    }

    //todo make this better
    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }
}
