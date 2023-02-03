package com.totalplay.cookies.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.time.LocalDateTime;

import org.apache.http.HttpResponse;
import com.totalplay.cookies.model.*;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPost;
import org.springframework.http.ResponseEntity;
import org.springframework.core.env.Environment;
import com.totalplay.cookies.entity.CookieEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.totalplay.cookies.repository.CookieRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CookieService {

    @Autowired
    private Environment environment;

    @Autowired
    private CookieRepository cookieRepository;

    private String identifierGeneral;
    private String macGeneral;
    private String codeGeneral;
    private String addressGeneral;
    private String subscriberGeneral;
    private String firstCookieGeneral;
    private String secondCookieGeneral;
    private String creationDateGeneral;

    public ResponseEntity<ResponseGet> getInformation(String mac) {

        Result result = new Result();
        ResponseGet responseGet = new ResponseGet();
        Information information = new Information();

        if (mac != null && !mac.trim().isEmpty()) {

            String resultDecrypt = decrypt(mac);

            if (!resultDecrypt.equals("0")) {

                CookieEntity cookieEntity = cookieRepository.getCookie(resultDecrypt);

                if (cookieEntity != null) {

                    if (!validateResponse(cookieEntity.getIdentifier().toString(),
                            cookieEntity.getCode(), cookieEntity.getAddress(), cookieEntity.getSubscriber(),
                            cookieEntity.getFirstCookie(), cookieEntity.getSecondCookie(),
                            cookieEntity.getCreationDate().toString()).equals("0")) {

                        result.setCode("100");
                        result.setDescription("Petición realizada con éxito.");

                        information.setIdentifier(identifierGeneral);
                        information.setCode(codeGeneral);
                        information.setAddress(addressGeneral);
                        information.setSubscriber(subscriberGeneral);
                        information.setFirstCookie(firstCookieGeneral);
                        information.setSecondCookie(secondCookieGeneral);
                        information.setCreationDate(creationDateGeneral);

                        responseGet.setResult(result);
                        responseGet.setInformation(information);

                        return new ResponseEntity(responseGet, HttpStatus.OK);


                    } else {

                        result.setCode("500");
                        result.setDescription("Excepción al tratar de encriptar los valores de respuesta.");

                        responseGet.setResult(result);
                        responseGet.setInformation(information);

                        return new ResponseEntity(responseGet, HttpStatus.NOT_FOUND);

                    }

                } else {

                    result.setCode("400");
                    result.setDescription("No se encontrarón coincidencias.");

                    responseGet.setResult(result);
                    responseGet.setInformation(information);

                    return new ResponseEntity(responseGet, HttpStatus.NOT_FOUND);

                }

            } else {

                result.setCode("300");
                result.setDescription("Excepción al tratar de desencriptar mac.");

                responseGet.setResult(result);
                responseGet.setInformation(information);

                return new ResponseEntity(responseGet, HttpStatus.BAD_REQUEST);

            }

        } else {
            result.setCode("200");
            result.setDescription("Ingresar mac.");
            responseGet.setResult(result);
            responseGet.setInformation(information);

            return new ResponseEntity(responseGet, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ResponsePost> regUpdInf(RequestPost requestPost) {

        boolean registerFlag = false;

        Result result = new Result();
        ResponsePost responsePost = new ResponsePost();

        try {

            if (!requestPost.getMac().trim().isEmpty() && !requestPost.getCode().trim().isEmpty() &&
                    !requestPost.getAddress().trim().isEmpty() && !requestPost.getSubscriber().trim().isEmpty() &&
                    !requestPost.getFirstCookie().trim().isEmpty() && !requestPost.getSecondCookie().trim().isEmpty()) {
                String validateResult = validateRequest(requestPost);

                if (validateResult.equals("1")) {

                    registerFlag = true;

                    LocalDateTime date = LocalDateTime.now();

                    CookieEntity cookieEntity = cookieRepository.getCookie(macGeneral);

                    if (cookieEntity != null) {

                        Integer consult = cookieRepository.updateCookie(codeGeneral, addressGeneral, subscriberGeneral,
                                date, firstCookieGeneral, secondCookieGeneral, macGeneral);

                        if (consult == 1) {

                            result.setCode("100");
                            result.setDescription("Petición realizada con éxito.");
                            responsePost.setResult(result);

                            return new ResponseEntity(responsePost, HttpStatus.ACCEPTED);

                        } else {

                            result.setCode("400");
                            result.setDescription("Sin cambios realizados.");
                            responsePost.setResult(result);

                            return new ResponseEntity(responsePost, HttpStatus.NOT_FOUND);

                        }

                    } else {

                        Long identifier = cookieRepository.maxValue();

                        if (identifier != null) {

                            cookieRepository.registerCookie((identifier + 1), macGeneral, codeGeneral, addressGeneral,
                                    subscriberGeneral, firstCookieGeneral, secondCookieGeneral, date);

                            result.setCode("100");
                            result.setDescription("Petición realizada con éxito.");
                            responsePost.setResult(result);

                            return new ResponseEntity(responsePost, HttpStatus.ACCEPTED);

                        } else {

                            cookieRepository.registerCookie(Long.valueOf(1), macGeneral, codeGeneral, addressGeneral,
                                    subscriberGeneral, firstCookieGeneral, secondCookieGeneral, date);

                            result.setCode("100");
                            result.setDescription("Petición realizada con éxito.");
                            responsePost.setResult(result);

                            return new ResponseEntity(responsePost, HttpStatus.ACCEPTED);

                        }

                    }


                } else {

                    result.setCode("300");
                    result.setDescription("Excepción al tratar de desencriptar los parametros de entrada.");
                    responsePost.setResult(result);

                    return new ResponseEntity(responsePost, HttpStatus.BAD_REQUEST);
                }
            } else {

                result.setCode("200");
                result.setDescription("Ingresar toda la información solicitada.");
                responsePost.setResult(result);

                return new ResponseEntity(responsePost, HttpStatus.BAD_REQUEST);

            }


        } catch (Exception e) {

            if (registerFlag) {

                result.setCode("500");
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

    private String decrypt(String value) {

        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(environment.getProperty("decrypt.address.service"));

            httpPost.addHeader("content-Type", "application/json");
            httpPost.addHeader(environment.getProperty("service.header.name"), environment.getProperty("service.header.value"));

            JSONObject objectValue = new JSONObject();
            objectValue.put("value", value);

            List<JSONObject> list = new ArrayList<>();
            list.add(objectValue);

            JSONObject listValue = new JSONObject();
            listValue.put("values", list);

            StringEntity stringEntity = new StringEntity(listValue.toString());
            httpPost.setEntity(stringEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (statusValidate(httpResponse)) {

                String response = EntityUtils.toString(httpResponse.getEntity());

                JSONObject jsonResponse = new JSONObject(response);

                if (jsonResponse.has("result") && jsonResponse.has("values")) {

                    JSONObject jsonResult = new JSONObject(jsonResponse.getJSONObject("result").toString());

                    if (jsonResult.has("code") && !jsonResult.get("code").toString().trim().isEmpty()) {

                        if (jsonResult.get("code").equals("100")) {

                            JSONArray listValues = new JSONArray(jsonResponse.get("values").toString());

                            if (listValues.length() > 0) {

                                JSONObject jsonValue = new JSONObject(listValues.getJSONObject(0).toString());

                                if (jsonValue.has("value") && !jsonValue.get("value").toString().trim().isEmpty()) {

                                    return jsonValue.get("value").toString().trim();

                                } else {

                                    return "0";

                                }

                            } else {

                                return "0";
                            }

                        } else {

                            return "0";

                        }

                    } else {

                        return "0";

                    }

                } else {

                    return "0";

                }

            } else {

                return "0";

            }

        } catch (Exception e) {

            return "0";
        }
    }


    private String encrypt(String value) {

        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(environment.getProperty("encrypt.address.service"));

            httpPost.addHeader("content-Type", "application/json");
            httpPost.addHeader(environment.getProperty("service.header.name"), environment.getProperty("service.header.value"));

            JSONObject objectValue = new JSONObject();
            objectValue.put("value", value);

            List<JSONObject> list = new ArrayList<>();
            list.add(objectValue);

            JSONObject listValue = new JSONObject();
            listValue.put("values", list);

            StringEntity stringEntity = new StringEntity(listValue.toString());
            httpPost.setEntity(stringEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (statusValidate(httpResponse)) {

                String response = EntityUtils.toString(httpResponse.getEntity());

                JSONObject jsonResponse = new JSONObject(response);

                if (jsonResponse.has("result") && jsonResponse.has("values")) {

                    JSONObject jsonResult = new JSONObject(jsonResponse.getJSONObject("result").toString());

                    if (jsonResult.has("code") && !jsonResult.get("code").toString().trim().isEmpty()) {

                        if (jsonResult.get("code").equals("100")) {

                            JSONArray listValues = new JSONArray(jsonResponse.get("values").toString());

                            if (listValues.length() > 0) {

                                JSONObject jsonValue = new JSONObject(listValues.getJSONObject(0).toString());

                                if (jsonValue.has("value") && !jsonValue.get("value").toString().trim().isEmpty()) {

                                    return jsonValue.get("value").toString().trim();

                                } else {

                                    return "0";

                                }

                            } else {

                                return "0";
                            }

                        } else {

                            return "0";

                        }

                    } else {

                        return "0";

                    }

                } else {

                    return "0";

                }

            } else {

                return "0";

            }

        } catch (Exception e) {

            return "0";
        }
    }


    private boolean statusValidate(HttpResponse httpResponse) {

        return httpResponse.getStatusLine().getStatusCode() == 202;

    }

    public String validateResponse(String identifier, String code, String address,
                                   String subscriber, String firstCookie, String secondCookie, String creationDate) {

        identifierGeneral = encrypt(identifier);

        if (!identifierGeneral.equals("0")) {

            codeGeneral = encrypt(code);

            if (!codeGeneral.equals("0")) {

                addressGeneral = encrypt(address);

                if (!addressGeneral.equals("0")) {

                    subscriberGeneral = encrypt(subscriber);

                    if (!subscriberGeneral.equals("0")) {

                        firstCookieGeneral = encrypt(firstCookie);

                        if (!firstCookieGeneral.equals("0")) {

                            secondCookieGeneral = encrypt(secondCookie);

                            if (!secondCookieGeneral.equals("0")) {

                                creationDateGeneral = encrypt(creationDate);

                                if (!creationDateGeneral.equals("0")) {

                                    return "1";

                                } else {
                                    return "0";
                                }

                            } else {
                                return "0";
                            }

                        } else {
                            return "0";
                        }

                    } else {
                        return "0";
                    }

                } else {
                    return "0";
                }
            } else {
                return "0";
            }

        } else {
            return "0";
        }
    }

    private String validateRequest(RequestPost requestPost) {

        macGeneral = decrypt(requestPost.getMac().trim());

        if (!macGeneral.equals("0")) {

            codeGeneral = decrypt(requestPost.getCode().trim());

            if (!codeGeneral.equals("0")) {

                addressGeneral = decrypt(requestPost.getAddress().trim());

                if (!addressGeneral.equals("0")) {

                    subscriberGeneral = decrypt(requestPost.getSubscriber().trim());

                    if (!subscriberGeneral.equals("0")) {

                        firstCookieGeneral = decrypt(requestPost.getFirstCookie().trim());

                        if (!firstCookieGeneral.equals("0")) {

                            secondCookieGeneral = decrypt(requestPost.getSecondCookie().trim());

                            if (!secondCookieGeneral.equals("0")) {

                                return "1";

                            } else {
                                return "0";
                            }

                        } else {
                            return "0";
                        }

                    } else {
                        return "0";
                    }

                } else {
                    return "0";
                }

            } else {
                return "0";
            }

        } else {
            return "0";
        }
    }
}
