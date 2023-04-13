package com.maid.cafe.c.maidcafec.ServiceImplementation;

import com.maid.cafe.c.maidcafec.DAO.BillDAO;
import com.maid.cafe.c.maidcafec.DAO.CategoryDAO;
import com.maid.cafe.c.maidcafec.DAO.ProductDAO;
import com.maid.cafe.c.maidcafec.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    BillDAO billDAO;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDAO.count());
        map.put("product", productDAO.count());
        map.put("bill", billDAO.count());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
