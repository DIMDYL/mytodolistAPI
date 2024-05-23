package org.example.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.example.api.content.InterceptorMsg;
import org.example.api.context.BaseContext;
import org.example.api.properties.JwtProperties;
import org.example.api.result.Result;
import org.example.api.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        对于静态请求，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 获取token
        String token = request.getHeader(jwtProperties.getUserToken());
        // 如果token为空
        if (token == "" || token == null){
            // 将错误信息对象转换为json
            String error = JSONObject.toJSONString(Result.error(InterceptorMsg.NoToken));
            // 响应给页面
            response.getWriter().write(error);
            return false;
        }
        // 解析token
        try {
            Claims claims = JwtUtils.ParseJWT(token, jwtProperties.getKey());
            // 获取用户id
            Integer id = (Integer) claims.get(jwtProperties.getUserId());
            // 用户id存入本地线程
            BaseContext.setUserId(id);
        }catch (Exception e){
            String error = JSONObject.toJSONString(Result.error(InterceptorMsg.TokenError));
            return false;
        }
        return true;
    }

    // 在目标方法运行后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 释放本地线程
        BaseContext.removeUserId();
    }
}
