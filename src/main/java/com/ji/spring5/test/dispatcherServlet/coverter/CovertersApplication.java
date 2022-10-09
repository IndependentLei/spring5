package com.ji.spring5.test.dispatcherServlet.coverter;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;

import java.lang.reflect.Field;
import java.util.Date;

public class CovertersApplication {
    public static void main(String[] args) {

        /** 底层接口与实现
         * 第一套 ：spring
         * Printer 把其他类型转为 String
         * Parser 把 String 转为其他类型
         * Formatter综合 Printer 与 Parser 功能
         * Printer、Parser、Converter 经过适配转换成 GenericConverter 放入 Converters 集合中
         * FormattingConversionService 利用其它们实现转换
         *
         *
         * 第二套 ：jdk
         * PropertyEditor 把 String 与其他类型相互转换
         * PropertyEditorRegistry 可以注册多个 PropertyEditor 对象
         * 与第一套接口直接可以通过 FormatterPropertyEditorAdapter 来进行适配
         */

        /** 高层接口与实现
         * 他们都实现了
         * 这个高层转换接口，在转换时，会用到 TypeConverterDelegate 委派 ConversionService
         * 与 PropertyEditorRegistry 真正执行转换 （Facade 门面模式）
         *  - 首先看是否有自定义转换器，@InitBinder 添加的即属于这种（用了适配器模式把Formatter 转为需要的 PropertyEditor）
         *  - 再看有没有 Conversion Service
         *  - 再利用默认的 PropertyEditor 转换
         *  - 最后有一些特殊处理
         *  SimpleTypeConverter 仅做类型转换
         *  BeanWrapperImpl 为 bean 的属性赋值，当需要时做类型转换，走 Property
         *  DirectFieldAccessor 为 bean 的属性赋值，当需要时做类型转换，走 Field
         *  ServletRequestDataBinder 为 bean 的属性赋值，当需要时做类型转换，根据 directFieldAccess 选择走 Property 还是 Field ，具备校验与获取校验结果功能
         */
        // 仅有类型转换功能
        SimpleTypeConverter simpleTypeConverter = new SimpleTypeConverter();
        Integer integer = simpleTypeConverter.convertIfNecessary("13", int.class);
        Date date = simpleTypeConverter.convertIfNecessary("2022/02/03", Date.class);
        System.out.println(integer);
        System.out.println(date);

        // 利用反射原理，为 bean的属性赋值，使用get，set方法
        MyBean  myBean = new MyBean();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(myBean);
        beanWrapper.setPropertyValue("name","gg");
        beanWrapper.setPropertyValue("age","18");
        beanWrapper.setPropertyValue("birth","2020/10/13");
        System.out.println(myBean);

        // 利用反射原理，为 bean的属性赋值 ，不使用get，set方法
        MyBean1  myBean1 = new MyBean1();
        DirectFieldAccessor directFieldAccessor = new DirectFieldAccessor(myBean1);
        directFieldAccessor.setPropertyValue("name","gg");
        directFieldAccessor.setPropertyValue("age","18");
        directFieldAccessor.setPropertyValue("birth","2020/10/13");
        System.out.println(myBean1);

        // 执行数据绑定
        MyBean  myBean2 = new MyBean();
        DataBinder dataBinder = new DataBinder(myBean2);
//        dataBinder.initDirectFieldAccess(); // 走私有成员变量
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("name","data");
        mutablePropertyValues.add("age","18");
        mutablePropertyValues.add("birth","2020/10/13");
        dataBinder.bind(mutablePropertyValues);
        System.out.println(myBean2);

        // web环境下的数据绑定
        MyBean  myBean3 = new MyBean();
        ServletRequestDataBinder servletRequestDataBinder = new ServletRequestDataBinder(myBean3);
//        dataBinder.initDirectFieldAccess(); // 走私有成员变量
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name","web");
        request.setParameter("age","22");
        request.setParameter("birth","2020/10/13");
        servletRequestDataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(myBean3);



    }

    static class MyBean{
        private String name;
        private int age;
        private Date birth;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Date getBirth() {
            return birth;
        }

        public void setBirth(Date birth) {
            this.birth = birth;
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", birth=" + birth +
                    '}';
        }
    }

    static class MyBean1 {
        private String name;
        private int age;
        private Date birth;

        @Override
        public String toString() {
            return "MyBean1{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", birth=" + birth +
                    '}';
        }
    }

}
