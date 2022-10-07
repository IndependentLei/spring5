package com.ji.spring5.test.dispatcherServlet.init;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;

public class JsonReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        ResponseBody methodAnnotation = returnType.getMethodAnnotation(ResponseBody.class);
        return methodAnnotation != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        String s = JSONUtils.toJSONString(returnValue); // 获取返回值
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        if(response != null) {
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().print(s);
        }

        // 设置请求已经处理完毕
        mavContainer.setRequestHandled(Boolean.TRUE);
    }
}
