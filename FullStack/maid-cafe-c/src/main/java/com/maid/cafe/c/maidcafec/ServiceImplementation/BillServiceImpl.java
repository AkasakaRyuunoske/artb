package com.maid.cafe.c.maidcafec.ServiceImplementation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.maid.cafe.c.maidcafec.Constants.CafeConstant;
import com.maid.cafe.c.maidcafec.DAO.BillDAO;
import com.maid.cafe.c.maidcafec.JWT.JWTFilter;
import com.maid.cafe.c.maidcafec.POJO.Bill;
import com.maid.cafe.c.maidcafec.Service.BillService;
import com.maid.cafe.c.maidcafec.Utils.CafeUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Log4j2
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    JWTFilter jwtFilter;

    @Autowired
    BillDAO billDAO;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("generate report is called");
        try {
            String fileName;
            log.info("validated: " + validateRequestMap(requestMap));

            if (validateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = CafeUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }
                //todo customize pdf
                String data = "Name: " + requestMap.get("name") + "\n"
                        + "Contact Number: " + requestMap.get("contactNumber") + "\n"
                        + "Email: " + requestMap.get("email") + "\n"
                        + "Payment Method: " + requestMap.get("paymentMethod");

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(CafeConstant.BILL_STORE_LOCATION + "/" + fileName + ".pdf"));

                document.open();
                setRectanglePdf(document);

                Paragraph header = new Paragraph("Maid Cafe C", getFont("Header"));
                header.setAlignment(Element.ALIGN_CENTER);
                document.add(header);

                Paragraph content = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(content);

                PdfPTable pdfPTable = new PdfPTable(5);
                pdfPTable.setWidthPercentage(100);
                addTableHeader(pdfPTable);

                JSONArray jsonArray = CafeUtils.getJSONArrayFromString((String) requestMap.get("productDetails"));

                log.info("Json array: " + jsonArray);
                int i;
                for (i = 0; i < jsonArray.length(); i++) {
                    log.info("CafeUtils.getMapFromJson(jsonArray.getString(i) " + CafeUtils.getMapFromJson(jsonArray.getString(i)));
                    addRows(pdfPTable, CafeUtils.getMapFromJson(jsonArray.getString(i)));
                }

                document.add(pdfPTable);

                Paragraph footer = new Paragraph("Total is: " + requestMap.get("totalAmount") + "\n"
                        + "tysm", getFont("Data"));

                document.add(footer);
                document.close();

                log.info("before return");
                return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity(CafeConstant.YUME, HttpStatus.BAD_REQUEST); // todo
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> billList = new ArrayList<>();
        if (jwtFilter.isAdmin()) {
            billList = billDAO.getAllBills();
        } else {
            billList = billDAO.getBillByUserName(jwtFilter.getCurrentUser());
        }

        return new ResponseEntity<>(billList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPDF(Map<String, Object> requestMap) {
        try {
            log.info("getPDF was called, map: {}", requestMap);
            byte[] byteArray = new byte[0];
            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap)) {
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST); //todo
            }

            String filePath = CafeConstant.BILL_STORE_LOCATION + "/" + (String) requestMap.get("uuid") + ".pdf";
            if (CafeUtils.doesFileExists(filePath)) {
                byteArray = getByteArray(filePath);

                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerate", false);
                generateReport(requestMap);

                byteArray = getByteArray(filePath);

                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null; // TODO MUST BE CHANGED
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            Optional optional = billDAO.findById(id);

            if (optional.isPresent()) {
                billDAO.deleteById(id);

                return CafeUtils.getResponseEntity("Bill deleted successfully.", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("Bill does not exists", HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private byte[] getByteArray(String filePath) throws Exception {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);

        byte[] byteArray = IOUtils.toByteArray(targetStream);

        targetStream.close();

        return byteArray;
    }

    private void addRows(PdfPTable pdfPTable, Map<String, Object> data) {
        log.info("addRows is called");

        log.info("(String) data.get(\"name\")  " + (String) data.get("name"));
        log.info("(String) data.get(\"name\")  " + (String) data.get("category"));
        log.info("(String) data.get(\"name\")  " + (String) data.get("quantity"));

        log.info("Data {}", data);
        pdfPTable.addCell((String) data.get("name"));
        pdfPTable.addCell((String) data.get("category"));
        pdfPTable.addCell((String) data.get("quantity"));
        pdfPTable.addCell(Double.toString((Double) data.get("price")));
        pdfPTable.addCell(Double.toString((Double) data.get("total")));

    }

    // todo customize pdf
    private void addTableHeader(PdfPTable pdfPTable) {
        log.info("addTableHeader was called");
        Stream.of("Name", "Category", "Quantity", "Price", "Total").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorder(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setBackgroundColor(BaseColor.CYAN);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);

            pdfPTable.addCell(header);
        });
    }

    // todo customize pdf
    private Font getFont(String type) {
        log.info("getFont is called");

        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.PINK);
                headerFont.setStyle(Font.BOLD);

                return headerFont;

            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);

                return dataFont;

            default:
                return new Font();
        }
    }

    private void setRectanglePdf(Document document) throws DocumentException {
        log.info("setRectanglePdf is called");

        Rectangle rectangle = new Rectangle(577, 825, 18, 15);

        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.MAGENTA);
        rectangle.setBorderWidth(1);

        document.add(rectangle);
    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
            Bill bill = new Bill();

            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
            bill.setProductDetails((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());

            billDAO.save(bill);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("totalAmount");
    }
}
