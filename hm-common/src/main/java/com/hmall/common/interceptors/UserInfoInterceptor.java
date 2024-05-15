package com.hmall.common.interceptors;

import cn.hutool.core.util.StrUtil;
import com.hmall.common.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Autor：林建威
 * @DateTime：2024/5/15 15:50
 **/

public class UserInfoInterceptor implements HandlerInterceptor {
    /**
     * 在controller之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取登录用户信息，因为在网关拦截处已经获取到
        String userInfo = request.getHeader("user-info");
        //2. 判断是否获取的到用户信息,有的,则存入threadlocal
        if (StrUtil.isNotBlank(userInfo)) {
            //获取到,则将用户信息转换成long类型返回
            UserContext.setUser(Long.valueOf(userInfo));
        }
        //3.放行
        return true;
    }

    /**
     * 在threadLocal用完后，需要用aftercompletion完成用户清理
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除用户
        UserContext.removeUser();
    }
}
