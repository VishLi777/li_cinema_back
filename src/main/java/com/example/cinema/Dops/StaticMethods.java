package com.example.cinema.Dops;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class StaticMethods {


    /**
     * Создание ответа
     * @param status - статус ответа
     * @param info - инфорация, которая будет прописана под полем "info"
     */
    public static void createResponse(int status, String info){

        HttpServletResponse response = getCurrentResponse();
        HttpServletRequest request = getCurrentRequest();

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

    private static HttpServletResponse getCurrentResponse(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        if(response == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return response;
    }

    private static HttpServletRequest getCurrentRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return ((ServletRequestAttributes)requestAttributes).getRequest();
    }


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
    public static String parsingJson (String body, String field) {

        try {
            JSONObject jsonObject = new JSONObject(body);
            field = jsonObject.getString(field);
        } catch (JSONException e) {
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Incorrect JSON");
            return null;
        }
        return field;
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

}
