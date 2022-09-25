package com.ji.spring5.test.aop.cglibPrxoy;

public class CglibProxy {
    public void get(){
        System.out.println("CglibProxy >>>>>>>>>> get");
    }

    public int getInt(int i){
        System.out.println("CglibProxy >>>>>>>>>> getInt");
        return i;
    }

    public long getLong(long i){
        System.out.println("CglibProxy >>>>>>>>>> getLong");
        return i;
    }
}
