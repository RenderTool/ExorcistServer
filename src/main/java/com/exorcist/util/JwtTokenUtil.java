package com.exorcist.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@Data
@Slf4j
public class JwtTokenUtil {

    private static String secret = "SiGaoHeExorcist";//令牌
    private static int expiration =2;//时间

    // 根据负载生成 token
    public static String createToken(Map<String, Object> claims) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,expiration);//过期时间，按具体业务定义
        return JWT.create()
                .withClaim("claims",claims)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(secret));
    }

    // 验证 token 是否有效
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
    // 获取 token 的过期时间
    public static Date getExpirationDate(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getExpiresAt();
    }
    // 获取 token 的剩余有效时间（以秒为单位）
    public static long getExpirationTimeInSeconds(String token) {
        Date expirationDate = getExpirationDate(token);
        long currentTimeInMillis = new Date().getTime();
        long expirationTimeInMillis = expirationDate.getTime();
        return (expirationTimeInMillis - currentTimeInMillis) / 1000;
    }
}
