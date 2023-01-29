package com.totalplay.cookies.controller;

import org.springframework.http.ResponseEntity;
import com.totalplay.cookies.model.RequestModel;
import org.springframework.web.bind.annotation.*;
import com.totalplay.cookies.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/middleware")
public class CookieController {

    @Autowired
    private CookieService cookieService;

    @GetMapping({"/clubwifi/cookies/{mac}", "/clubwifi/cookies/"})
    public ResponseEntity<Object> getResponse(@PathVariable(value = "mac", required = false) String mac){
        return cookieService.getInformation(mac);
    }

    @PostMapping("/clubwifi/cookies/")
    public ResponseEntity<Object> regUpdResp(@RequestBody(required = false) RequestModel requestModel){
        return cookieService.regUpdInf(requestModel);
    }

}
