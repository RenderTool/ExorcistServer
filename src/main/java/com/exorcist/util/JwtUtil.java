package com.exorcist.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
@Data
@Slf4j
public class JwtUtil {

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
}
