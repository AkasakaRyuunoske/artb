package com.maid.cafe.c.maidcafec.DAO;

import com.maid.cafe.c.maidcafec.POJO.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDAO extends JpaRepository<Bill, Integer> {

}
