package com.concur;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class TestSync {
    public static void main(String[] args) throws InterruptedException {
        CounterGen gen = CounterGen.INSTANCE;
        gen.inc();
        gen.print();
    }
}

interface CounerGen {
    void inc();

    void print();
}

enum CounterGen implements CounerGen {
    INSTANCE;

    @Override
    public void inc() {
        System.out.println("CounterGen.inc");
    }

    @Override
    public void print() {
        System.out.println("CounterGen.print");
    }
}
