package org.example.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.api.properties.JwtProperties;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
  public static String CreateJWT(JwtProperties jwtProperties, Map<String, Object> data) {
    // 生成token
    return Jwts.builder()
      .signWith(SignatureAlgorithm.HS256, jwtProperties.getKey().getBytes()) // 加密方式，加密密钥
      .setClaims(data) // 添加参数
      .setExpiration(new Date(System.currentTimeMillis() + 7200000L)) // 过期时间
      .compact();
  }
  
  public static Claims ParseJWT(String token, String key) {
    return (Claims)Jwts.parser() // 解析token
      .setSigningKey(key.getBytes(StandardCharsets.UTF_8)) // 解密密钥
      .parseClaimsJws(token) // 解析的token字符串
      .getBody(); // 返回存储的数据对象
  }
}
