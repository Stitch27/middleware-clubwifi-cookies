package com.totalplay.cookies.service;

import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.totalplay.cookies.model.RequestModel;
import com.totalplay.cookies.entity.CookieEntity;
import com.totalplay.cookies.repository.CookieRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CookieService {

    @Autowired
    private CookieRepository cookieRepository;

    public ResponseEntity<Object> getInformation(String mac) {

        HashMap<String, String> results = new LinkedHashMap<>();
        HashMap<String, Object> response = new LinkedHashMap<>();

        if (mac != null) {

            CookieEntity cookieEntity = cookieRepository.getCookie(mac);

            if (cookieEntity != null) {

                HashMap<String, String> information = new LinkedHashMap<>();

                results.put("code", "100");
                results.put("description", "Petición realizada con éxito.");

                information.put("identifier", cookieEntity.getIdentifier().toString());
                information.put("code", cookieEntity.getCode());
                information.put("address", cookieEntity.getAddress());
                information.put("subscriber", cookieEntity.getSubscriber());
                information.put("firstCookie", cookieEntity.getFirstCookie());
                information.put("secondCookie", cookieEntity.getSecondCookie());
                information.put("creationDate", cookieEntity.getCreationDate().toString());

                response.put("results", results);
                response.put("information", information);

                return new ResponseEntity(response, HttpStatus.OK);

            } else {

                results.put("code", "300");
                results.put("description", "No se encontrarón coincidencias.");

                response.put("results", results);

                return new ResponseEntity(response, HttpStatus.NOT_FOUND);


            }

        } else {

            results.put("code", "200");
            results.put("description", "Ingresar mac.");

            response.put("results", results);

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }

    }

    public ResponseEntity<Object> regUpdInf(RequestModel requestModel) {

        boolean registerFlag = false;

        HashMap<String, String> results = new LinkedHashMap<>();
        HashMap<String, Object> response = new LinkedHashMap<>();

        try {

            LocalDateTime time = LocalDateTime.now();

            if (!requestModel.getMac().trim().isEmpty() && !requestModel.getCode().trim().isEmpty() &&
                    !requestModel.getAddress().trim().isEmpty() && !requestModel.getSubscriber().trim().isEmpty() &&
                    !requestModel.getFirstCookie().trim().isEmpty() && !requestModel.getSecondCookie().trim().isEmpty()) {

                registerFlag = true;

                CookieEntity cookieEntity = cookieRepository.getCookie(requestModel.getMac().trim());

                if (cookieEntity != null) {

                    Integer result = cookieRepository.updateCookie(requestModel.getCode().trim(), requestModel.getAddress().trim(),
                            requestModel.getSubscriber().trim(), time, requestModel.getFirstCookie().trim(),
                            requestModel.getSecondCookie().trim(), requestModel.getMac().trim());

                    if (result == 1) {

                        results.put("code", "100");
                        results.put("description", "Petición realizada con éxito.");
                        response.put("results", results);

                        return new ResponseEntity(response, HttpStatus.ACCEPTED);

                    } else {

                        results.put("code", "300");
                        results.put("description", "Sin cambios realizados.");
                        response.put("results", results);

                        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

                    }

                } else {

                    Long identifier = cookieRepository.maxValue();

                    if (identifier != null) {

                        cookieRepository.registerCookie((identifier + 1), requestModel.getMac().trim(), requestModel.getCode().trim(),
                                requestModel.getAddress().trim(), requestModel.getSubscriber().trim(),
                                requestModel.getFirstCookie().trim(), requestModel.getSecondCookie().trim(), time);

                        results.put("code", "100");
                        results.put("description", "Petición realizada con éxito.");
                        response.put("results", results);

                        return new ResponseEntity(response, HttpStatus.ACCEPTED);

                    } else {

                        cookieRepository.registerCookie(Long.valueOf(1), requestModel.getMac().trim(), requestModel.getCode().trim(),
                                requestModel.getAddress().trim(), requestModel.getSubscriber().trim(),
                                requestModel.getFirstCookie().trim(), requestModel.getSecondCookie().trim(), time);

                        results.put("code", "100");
                        results.put("description", "Petición realizada con éxito.");
                        response.put("results", results);

                        return new ResponseEntity(response, HttpStatus.ACCEPTED);

                    }

                }

            } else {
                results.put("code", "200");
                results.put("description", "Ingresar toda la información solicitada.");
                response.put("results", results);

                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {

            if(registerFlag){

                results.put("code", "400");
                results.put("description", "Excepción al registrar o actualizar cookie.");
                response.put("results", results);

                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

            }else{

                results.put("code", "-51");
                results.put("description", "Exepción en la solicitud.");

                response.put("results", results);

                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

            }

        }

    }

}
