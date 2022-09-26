package com.ji.spring5.test.aop.cglibPrxoy;

import org.springframework.cglib.core.Signature;
import org.springframework.cglib.reflect.FastClass;

import java.lang.reflect.InvocationTargetException;

public class ProxyFastClass  {
    private Signature sig1 = new Signature("getSuper","()V");
    private Signature sig2 = new Signature("getSuperInt","(I)I");
    private Signature sig3 = new Signature("getSuperLong","(J)J");

    public int getIndex(Signature signature){
        if(sig1.equals(signature)){
            return 0;
        }else if(sig2.equals(signature)){
            return 1;
        }else if(sig3.equals(signature)) {
            return 2;
        }else return -1;
    }

    public  Object invoke(int index, Object proxy, Object[] args) throws InvocationTargetException{
        if(index == 0){
            (($Proxy0)proxy).getSuper();
            return null;
        }else if(index == 1){
            return (($Proxy0)proxy).getSuperInt((int)args[0]);
        }else if(index == 2){
            return (($Proxy0)proxy).getSuperLong((long)args[0]);
        }else return null;
    }

    public static void main(String[] args) {
        try {
            ProxyFastClass fastClass = new ProxyFastClass();
            Object get = fastClass.invoke(fastClass.getIndex(new Signature("getSuper", "()V")), new $Proxy0(), null);
            System.out.println(get);

            Object getInt = fastClass.invoke(fastClass.getIndex(new Signature("getSuperInt","(I)I")), new $Proxy0(), new Object[]{100});
            System.out.println(getInt);

            Object getLong = fastClass.invoke(fastClass.getIndex(new Signature("getSuperLong","(J)J")), new $Proxy0(), new Object[]{50000L});
            System.out.println(getLong);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
