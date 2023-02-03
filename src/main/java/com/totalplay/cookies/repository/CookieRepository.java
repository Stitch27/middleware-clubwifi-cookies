package com.totalplay.cookies.repository;

import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.totalplay.cookies.entity.CookieEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@Transactional
public interface CookieRepository extends JpaRepository<CookieEntity, Long>{

    @Query(value = "SELECT * FROM MICROWNER.REGISTRATION_COOKIES_CLUB_WIFI WHERE RCCW_MAC = :mac", nativeQuery = true)
    CookieEntity getCookie(@Param("mac") String mac);

    @Modifying
    @Query(value = "UPDATE MICROWNER.REGISTRATION_COOKIES_CLUB_WIFI SET RCCW_CODE = :code, RCCW_ADDRESS = :address, RCCW_SUBSCRIBER = :subscriber, RCCW_CREATION_DATE = :creationDate, RCCW_FIRST_COOKIE = :firstCookie, RCCW_SECOND_COOKIE = :secondCookie WHERE RCCW_MAC = :mac", nativeQuery = true)
    Integer updateCookie(@Param("code") String code, @Param("address") String address, @Param("subscriber") String subscriber, @Param("creationDate") LocalDateTime creationDate, @Param("firstCookie") String firstCookie, @Param("secondCookie") String secondCookie, @Param("mac") String mac);

    @Modifying
    @Query(value = "INSERT INTO MICROWNER.REGISTRATION_COOKIES_CLUB_WIFI(RCCW_IDENTIFIER, RCCW_MAC, RCCW_CODE, RCCW_ADDRESS, RCCW_SUBSCRIBER, RCCW_FIRST_COOKIE, RCCW_SECOND_COOKIE, RCCW_CREATION_DATE) VALUES(:identifier, :mac, :code, :address, :subscriber, :firstCookie, :secondCookie, :creationDate)", nativeQuery = true)
    void registerCookie(@Param("identifier") Long identifier, @Param("mac") String mac, @Param("code") String code, @Param("address") String address, @Param("subscriber") String subscriber, @Param("firstCookie") String firstCookie, @Param("secondCookie") String secondCookie, @Param("creationDate") LocalDateTime creationDate);

    @Query(value = "SELECT MAX(RCCW_IDENTIFIER) FROM MICROWNER.REGISTRATION_COOKIES_CLUB_WIFI", nativeQuery = true)
    Long maxValue();

}
