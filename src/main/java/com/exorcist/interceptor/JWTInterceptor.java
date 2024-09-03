package com.exorcist.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.exorcist.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authorization");

        try {
            // 解析 token
            Map<String, Object> claims = JwtTokenUtil.parseToken(token);
            String userID = claims.get("ID").toString();

            System.out.println("用户id:"+userID);

            // 拿到 Redis 中的 token

            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            System.out.println(ops.get(userID));

            String redisToken = ops.get(userID);
            if(redisToken == null || !redisToken.equals(token)) {
                throw new Exception();
            }
            return true; // 放行请求

        } catch (Exception e) {
            // 捕获特定的 JWT 异常并设置消息
            if (e instanceof SignatureVerificationException) {
                map.put("msg", "无效签名");
            } else if (e instanceof TokenExpiredException) {
                map.put("msg", "token过期");
            } else if (e instanceof AlgorithmMismatchException) {
                map.put("msg", "token算法不一致");
            } else {
                map.put("msg", "token失效");
            }

            map.put("state", false); // 设置状态

            // 将 map 转化成 JSON，返回给客户端
            String json = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(json);

            return false; // 拦截请求
        }
    }
}
