package com.example.cinema.Dops;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import com.example.cinema.ReqResContextSettings.ReqResContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StaticMethods {


    /**
     * Создание ответа
     * @param status - статус ответа
     * @param info - инфорация, которая будет прописана под полем "info"
     */
    public static void createResponse(int status, String info){

        ReqResContext contex = ReqResContext.getCurrentInstance();
        HttpServletRequest request = contex.getRequest();
        HttpServletResponse response = contex.getResponse();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
//        response.addHeader("Access-Control-Allow-Origin", "*" );

        final Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("info", info);
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getOutputStream(), body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private static HttpServletResponse getCurrentResponse(){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if(requestAttributes == null)
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
//        if(response == null)
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        return response;
//    }
//
//    private static HttpServletRequest getCurrentRequest(){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if(requestAttributes == null)
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        return ((ServletRequestAttributes)requestAttributes).getRequest();
//    }


    /**
     * Создание ответа для передачи всех ошибок(данных), которые были обнаружены при валидации
     * @param field лист полей, к которым относятся ошибки
     * @param info характеристика каждой ошибки
     *
     * @code 469 - Incorrect validation
     */
    public static void createBadResponseDueToValidation(HttpServletRequest request,
                                      HttpServletResponse response,
                                      List<String> field,
                                      List<String> info){
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(469);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", 469);
        body.put("field", field);
        body.put("info", info);
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getOutputStream(), body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Получение конкретного поля из json
     * @param body - изначальный json
     * @param field - поле, которое необходимо получить из этого json
     * @return field/null
     *
     * @code 400 - Incorrect JSON
     */
    public static String parsingStringFromJson(String body, String field) {

        try {
            JSONObject jsonObject = new JSONObject(body);
            field = jsonObject.getString(field);
        } catch (JSONException e) {
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Incorrect JSON");
            return null;
        }
        return field;
    }


    public static Long parsingLongFromJson (String body, String field) {
        long res;
        try {
            JSONObject jsonObject = new JSONObject(body);
            res = jsonObject.getLong(field);
        } catch (JSONException e) {
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Incorrect JSON");
            return null;
        }
        return res;
    }

    /** метод определения расширения файла */
    public static String getFileExtension(String fileName) {
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return null;
    }

    public static Date convertStringToDate(String d){

        if (d==null) {
            StaticMethods.createResponse(400, "Date is null");
            return null;
        }
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ITALIAN);
        Date date;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            StaticMethods.createResponse(400, "Incorrect date");
            return null;
        }
        return date;
    }

}
