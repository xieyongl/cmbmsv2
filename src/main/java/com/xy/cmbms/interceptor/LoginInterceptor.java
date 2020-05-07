package com.xy.cmbms.interceptor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author Xieyong
 * @date 2020/5/6 - 16:28
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate redisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        //检验用户登录状态
        String token = request.getHeader("token");
        token = token == null ? "" : token;
//        Object user =  redisTemplate.opsForValue().get("token");
        //查询这个token在redis的剩余时间
        Long expire = redisTemplate.getExpire(token);
        if (expire < 0) { //登录状态，放行
            flag = false;
        }
        //重置token时间
        redisTemplate.expire(token, 30L, TimeUnit.MINUTES);
        return flag;
    }

//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        //检验用户登录状态
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String token = request.getHeader("token");
//        token = token == null ? "" : token;
////        Object user =  redisTemplate.opsForValue().get("token");
//        //查询这个token在redis的剩余时间
//        Long expire = redisTemplate.getExpire(token);
//        if (expire > 0) { //登录状态，放行
//            //重置token时间
//            redisTemplate.expire(token, 30L, TimeUnit.MINUTES);
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            //未登录，响应数据
//            String str = "未登录，请先登录！";
//            response.setContentType("UTF-8");
//            PrintWriter out = response.getWriter();
//            out.write(str);
//        }
//
//    }

}
