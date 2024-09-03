package com.exorcist;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

class JwtTokenTool {

    @Test
    public void TestTokenGen() {

        HashMap<String,Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,2);//过期时间，按具体业务定义
        String token = JWT.create()
                .withHeader(map) //可以不设定，就是使用默认的
                .withClaim("ID",333333)//payload  //自定义用户名
                .withExpiresAt(instance.getTime()) //指定令牌过期时间
                .sign(Algorithm.HMAC256("SiGaoHeExorcist"));//签名

        System.out.println(token);

    }
    @Test
    public void TestTokenParse() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJJRCI6MzMzMzMzLCJleHAiOjE3MjUzNzA1NDl9.h_zdRi1xHYI2kCRM7tp2fbZFuraNtE5P7bX7RRut0-E";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("SiGaoHeExorcist")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        long currentTimeInMillis = new Date().getTime();
        long expirationTimeInMillis = decodedJWT.getExpiresAt().getTime();
        long sec =  (expirationTimeInMillis - currentTimeInMillis) / 1000;

        System.out.println(sec);
    }

}
