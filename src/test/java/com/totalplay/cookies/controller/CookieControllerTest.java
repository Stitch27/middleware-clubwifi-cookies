package com.totalplay.cookies.controller;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.jdbc.Sql;
import com.totalplay.cookies.entity.CookieEntity;
import org.springframework.test.context.ActiveProfiles;
import com.totalplay.cookies.repository.CookieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@ActiveProfiles("test")
class CookieControllerTest {

    @Autowired
    private CookieRepository cookieRepository;

    @Test
    public void register() {

        LocalDateTime date = LocalDateTime.now();

        CookieEntity cookieRegister = new CookieEntity();
        cookieRegister.setIdentifier(Long.valueOf(1));
        cookieRegister.setMac("54:14:f3:dc:c0:a3");
        cookieRegister.setCode("CF9F/2OD8iRH");
        cookieRegister.setAddress("10.188.12.57");
        cookieRegister.setSubscriber("0102058895@tpz");
        cookieRegister.setFirstCookie("4G/woJWQP8l7");
        cookieRegister.setSecondCookie("MSCAhy+q6W0/");
        cookieRegister.setCreationDate(date);

        CookieEntity cookieSave = cookieRepository.save(cookieRegister);

        Assertions.assertEquals(cookieSave.getAddress(), "10.188.12.57");

    }

    @Test
    @Sql("classpath:test-data.sql")
    public void consult() {
        CookieEntity cookieEntity = cookieRepository.getReferenceById(3L);
        Assertions.assertNotNull(cookieEntity);

    }

}