package com.ji.spring5.test.aop.aspectj;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

public class ExecutionTest {
    public static void main(String[] args) {
        try {
            // 切点
            AspectJExpressionPointcut pt1 = new AspectJExpressionPointcut();
            pt1.setExpression("execution(* get())"); // 所有get方法
            boolean getMatch = pt1.matches(Tone.class.getMethod("get"), Tone.class);
            System.out.println("getMatch >>>>>>>>"+getMatch);
            boolean setMatch = pt1.matches(Tone.class.getMethod("set"), Tone.class);
            System.out.println("setMatch >>>>>>>>"+setMatch);


            AspectJExpressionPointcut pt2 = new AspectJExpressionPointcut();
            pt2.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
            boolean annGetMatch = pt2.matches(Tone.class.getMethod("get"), Tone.class);
            System.out.println("annGetMatch >>>>>>>>"+annGetMatch);
            boolean annSetMatch = pt2.matches(Tone.class.getMethod("set"), Tone.class);
            System.out.println("annSetMatch >>>>>>>>"+annSetMatch);


            StaticMethodMatcherPointcut pt3 = new StaticMethodMatcherPointcut() {
                @Override
                public boolean matches(Method method, Class<?> aClass) {
                    MergedAnnotations annotations = MergedAnnotations.from(method); // 查找方法上的注解信息
                    if(annotations.isPresent(Transactional.class)){
                        return true;
                    }
                    annotations = MergedAnnotations.from(aClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY); // 查找当前类或者父类或者接口中所有注解信息
                    return annotations.isPresent(Transactional.class);
                }
            };

            boolean threeFlag = pt3.matches(Three.class.getMethod("get"), Three.class);
            System.out.println("threeFlag >>>>>>>>>"+threeFlag);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    static class Tone implements ToneInter{

        public void get(){
            System.out.println("get");
        }
        @Transactional
        public void set(){
            System.out.println("set");
        }
    }

    @Transactional
    static class TWO implements ToneInter{

        public void get(){
            System.out.println("get");
        }
        public void set(){
            System.out.println("set");
        }
    }

    static class Three implements ToneInter{

        public void get(){
            System.out.println("get");
        }
        public void set(){
            System.out.println("set");
        }
    }

    @Transactional
    interface ToneInter{
        void get();
        void set();
    }
}



