package com.maid.cafe.c.maidcafec.Utils;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class CafeUtils {
    private CafeUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"", httpStatus);
    }

    public static String getUUID() {
        Date date = new Date();
        long time = date.getTime();

        return "BILL-" + time; //todo change name
    }

    public static JSONArray getJSONArrayFromString(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        log.info("Json array {}", jsonArray);
        return jsonArray;
    }

    public static Map<String, Object> getMapFromJson(String data) {
        log.info("getMapFromJSON: " + data);
        if (!Strings.isNullOrEmpty(data)) {
            log.info("Strings.isNullOrEmpty(data) true");
            return new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        return new HashMap<>();
    }
}
