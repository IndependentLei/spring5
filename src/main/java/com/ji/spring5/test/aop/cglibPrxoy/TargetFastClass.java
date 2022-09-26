package com.ji.spring5.test.aop.cglibPrxoy;

import org.springframework.cglib.core.Signature;

import java.lang.reflect.InvocationTargetException;

public class TargetFastClass {
    private Signature sig1 = new Signature("get","()V");
    private Signature sig2 = new Signature("getInt","(I)I");
    private Signature sig3 = new Signature("getLong","(J)J");

    public int getIndex(Signature signature){
        if(sig1.equals(signature)){
            return 0;
        }else if(sig2.equals(signature)){
            return 1;
        }else if(sig3.equals(signature)) {
            return 2;
        }else return -1;
    }

    public  Object invoke(int index, Object target, Object[] args) throws InvocationTargetException {
        if(index == 0){
            ((CglibProxy)target).get();
            return null;
        }else if(index == 1){
            return ((CglibProxy)target).getInt((int)args[0]);
        }else if(index == 2){
            return ((CglibProxy)target).getLong((long)args[0]);
        }else return null;
    }

    public static void main(String[] args) {
        try {
            TargetFastClass fastClass = new TargetFastClass();
            Object get = fastClass.invoke(fastClass.getIndex(new Signature("get", "()V")), new CglibProxy(), null);
            System.out.println(get);

            Object getInt = fastClass.invoke(fastClass.getIndex(new Signature("getInt","(I)I")), new CglibProxy(), new Object[]{100});
            System.out.println(getInt);

            Object getLong = fastClass.invoke(fastClass.getIndex(new Signature("getLong","(J)J")), new CglibProxy(), new Object[]{50000L});
            System.out.println(getLong);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
