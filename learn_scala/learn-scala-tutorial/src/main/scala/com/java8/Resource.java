package com.java8;

import java.util.function.Consumer;


public class Resource {
    private Resource() {
        System.out.println("Creating...");
    }

    public Resource op1() {
        System.out.println("some op1");
        return this;
    }

    public Resource op2() {
        System.out.println("some op2");
        return this;
    }

    public int op3() {
        System.out.println("some op3");
        return 999;
    }

    private void close() {
        System.out.println("clean up");
    }

    public static void use(Consumer<Resource> consumer) {
        Resource resource = new Resource();
        try {
            consumer.accept(resource);
        } finally {
            resource.close();
        }
    }

    public static void main(String[] args) {
        Resource.use(resource -> {
            int i = resource.op1().op2().op3();
            System.out.println("i = " + i);
        });


    }
}
