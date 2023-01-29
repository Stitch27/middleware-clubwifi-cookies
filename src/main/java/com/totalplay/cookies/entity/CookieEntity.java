package com.totalplay.cookies.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REGISTRATION_COOKIES_CLUB_WIFI", schema = "TPBANKOWNER")
public class CookieEntity implements Serializable {

    @Id
    @Column(name = "RCCW_IDENTIFIER")
    private Long identifier;
    @Column(name = "RCCW_MAC")
    private String mac;
    @Column(name = "RCCW_CODE")
    private String code;
    @Column(name = "RCCW_ADDRESS")
    private String address;
    @Column(name = "RCCW_SUBSCRIBER")
    private String subscriber;
    @Column(name = "RCCW_FIRST_COOKIE")
    private String firstCookie;
    @Column(name = "RCCW_SECOND_COOKIE")
    private String secondCookie;
    @Column(name = "RCCW_CREATION_DATE")
    private LocalDateTime creationDate;

}