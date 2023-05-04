package com.maid.cafe.c.maidcafec.DAO;

import com.maid.cafe.c.maidcafec.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

    List<Category> getAllCategory();
}
