package com.rogchen.www.client.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务鉴权拦截器
 *
 * @author tangxw
 */
@SuppressWarnings("ALL")
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(ServiceAuthRestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明不进行服务拦截

        String clientToken = request.getHeader("client-token");
        String ylzinfoCloudAuthorization = request.getHeader("YlzinfoCloudAuthorization");
        logger.info("clientToken**************************************");
        logger.info(clientToken);
        logger.info("ylzinfoCloudAuthorization**************************************");
        logger.info(ylzinfoCloudAuthorization);
        return true;
    }
}
