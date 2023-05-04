package com.maid.cafe.c.maidcafec.Service;

import com.maid.cafe.c.maidcafec.POJO.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {
    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPDF(Map<String, Object> requestMap);

    ResponseEntity<String> deleteBill(Integer id);
}
