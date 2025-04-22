package com.xh.online_edu.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Slf4j
@Data
@Component
//@ConfigurationProperties(prefix = "hoj.jwt")
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire;

    private String header;

    private long checkRefreshExpire;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成jwt token
     */
    public String generateToken(String userId) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        long nowDate = System.currentTimeMillis();
        //过期时间
        Date expireDate = new Date(nowDate + expire * 1000);

        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(nowDate))
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
        redisUtils.set("token"+userId, token,expireDate.getTime());
        return token;
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

//    public void cleanToken(String uid) {
//        RedisUtils.delToken(uid);
//    }

//    public boolean hasToken(String uid) {
//        return RedisService.hasToken("tokenTable",uid);
//    }
}
