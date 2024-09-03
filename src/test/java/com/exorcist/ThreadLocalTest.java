package com.exorcist;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testThreadLocal() {

        ThreadLocal tl = new ThreadLocal();

        new Thread(()->{

            tl.set("黑猴");
            System.out.println(Thread.currentThread().getName()+ ":"+ tl.get());

        },"线程1").start();
        new Thread(()->{

            tl.set("八戒");
            System.out.println(Thread.currentThread().getName()+ ":"+ tl.get());

        },"线程2").start();

    }
}
