package com.totalplay.cookies.service;

import java.time.LocalDateTime;
import com.totalplay.cookies.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.totalplay.cookies.entity.CookieEntity;
import com.totalplay.cookies.repository.CookieRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CookieService {

    @Autowired
    private CookieRepository cookieRepository;

    public ResponseEntity<ResponseGet> getInformation(String mac) {

        Result result = new Result();
        ResponseGet responseGet = new ResponseGet();
        Information information = new Information();

        CookieEntity cookieEntity = cookieRepository.getCookie(mac);

        if (cookieEntity != null) {


            result.setCode("100");
            result.setDescription("Petición realizada con éxito.");

            information.setIdentifier(cookieEntity.getIdentifier().toString());
            information.setCode(cookieEntity.getCode());
            information.setAddress(cookieEntity.getAddress());
            information.setSubscriber(cookieEntity.getSubscriber());
            information.setFirstCookie(cookieEntity.getFirstCookie());
            information.setSecondCookie(cookieEntity.getSecondCookie());
            information.setCreationDate(cookieEntity.getCreationDate().toString());

            responseGet.setResult(result);
            responseGet.setInformation(information);

            return new ResponseEntity(responseGet, HttpStatus.OK);

        } else {

            result.setCode("300");
            result.setDescription("No se encontrarón coincidencias.");

            responseGet.setResult(result);
            responseGet.setInformation(information);

            return new ResponseEntity(responseGet, HttpStatus.NOT_FOUND);

        }

    }

    public ResponseEntity<ResponsePost> regUpdInf(RequestPost requestPost) {

        boolean registerFlag = false;

        Result result = new Result();
        ResponsePost responsePost = new ResponsePost();

        try {

            LocalDateTime date = LocalDateTime.now();

            if (!requestPost.getMac().trim().isEmpty() && !requestPost.getCode().trim().isEmpty() &&
                    !requestPost.getAddress().trim().isEmpty() && !requestPost.getSubscriber().trim().isEmpty() &&
                    !requestPost.getFirstCookie().trim().isEmpty() && !requestPost.getSecondCookie().trim().isEmpty()) {

                registerFlag = true;

                CookieEntity cookieEntity = cookieRepository.getCookie(requestPost.getMac().trim());

                if (cookieEntity != null) {

                    Integer consult = cookieRepository.updateCookie(requestPost.getCode().trim(), requestPost.getAddress().trim(),
                            requestPost.getSubscriber().trim(), date, requestPost.getFirstCookie().trim(),
                            requestPost.getSecondCookie().trim(), requestPost.getMac().trim());

                    if (consult == 1) {

                        result.setCode("100");
                        result.setDescription("Petición realizada con éxito.");
                        responsePost.setResult(result);

                        return new ResponseEntity(responsePost, HttpStatus.ACCEPTED);

                    } else {

                        result.setCode("300");
                        result.setDescription("Sin cambios realizados.");
                        responsePost.setResult(result);

                        return new ResponseEntity(responsePost, HttpStatus.BAD_REQUEST);

                    }

                } else {

                    Long identifier = cookieRepository.maxValue();

                    if (identifier != null) {

                        cookieRepository.registerCookie((identifier + 1), requestPost.getMac().trim(), requestPost.getCode().trim(),
                                requestPost.getAddress().trim(), requestPost.getSubscriber().trim(),
                                requestPost.getFirstCookie().trim(), requestPost.getSecondCookie().trim(), date);

                        result.setCode("100");
                        result.setDescription("Petición realizada con éxito.");
                        responsePost.setResult(result);

                        return new ResponseEntity(responsePost, HttpStatus.ACCEPTED);

                    } else {

                        cookieRepository.registerCookie(Long.valueOf(1), requestPost.getMac().trim(), requestPost.getCode().trim(),
                                requestPost.getAddress().trim(), requestPost.getSubscriber().trim(),
                                requestPost.getFirstCookie().trim(), requestPost.getSecondCookie().trim(), date);

                        result.setCode("100");
                        result.setDescription("Petición realizada con éxito.");
                        responsePost.setResult(result);

                        return new ResponseEntity(responsePost, HttpStatus.ACCEPTED);

                    }

                }

            } else {
                result.setCode("200");
                result.setDescription("Ingresar toda la información solicitada.");
                responsePost.setResult(result);

                return new ResponseEntity(responsePost, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {

            if (registerFlag) {

                result.setCode("400");
                result.setDescription("Excepción al tratar de registrar o actualizar cookie.");
                responsePost.setResult(result);

                return new ResponseEntity(responsePost, HttpStatus.BAD_REQUEST);

            } else {

                result.setCode("-51");
                result.setDescription("Exepción en la solicitud.");

                responsePost.setResult(result);

                return new ResponseEntity(responsePost, HttpStatus.BAD_REQUEST);

            }

        }

    }

}
