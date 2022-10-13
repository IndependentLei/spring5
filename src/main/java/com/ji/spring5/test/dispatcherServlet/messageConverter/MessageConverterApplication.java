package com.ji.spring5.test.dispatcherServlet.messageConverter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MessageConverterApplication {
    public static void main(String[] args) {
        try {
            // 消息转换器
            MockHttpOutputMessage message = new MockHttpOutputMessage();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            if(converter.canWrite(User.class, MediaType.APPLICATION_JSON)){
                converter.write(new User("1"),MediaType.APPLICATION_JSON,message);
                System.out.println("message.getBodyAsString(StandardCharsets.UTF_8) = " + message.getBodyAsString(StandardCharsets.UTF_8));
                // message.getBodyAsString(StandardCharsets.UTF_8) = {"name":"1"}
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * 1、MessageConverter 的作用，@ResponseBody 是返回值处理器解析的，但具体转换工作是 MessageConverter 做的
         * 2、如何选择 MediaType
         *  - 首先看 @RequestMapping 上有没有指定
         *  - 其次看 request 的 Accept 头有没有指定
         *  - 最后按 MessageConverter 的顺序，谁在前谁能转换
         */
    }

    static class User{
        private String name;

        public User(String name){
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
