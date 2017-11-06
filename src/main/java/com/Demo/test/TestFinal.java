package com.Demo.test;

/**
 * Created by tangjialiang on 2017/10/28.
 */

class TestDemo {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("do Something") ;
    }
}


public class TestFinal {

    public static void main(String[] args) {
        TestDemo td = new TestDemo() ;

        while(true) {
            System.out.println("hello ") ;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
