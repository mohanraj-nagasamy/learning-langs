package com.example;

import org.apache.commons.lang.StringUtils;

public class CallScala {
    public static void main(String[] args) {

        Integer i1 = 1000;
        Integer i2 = 1000;

        System.out.println(i1 == i2);

        printDiamond();

    }

    private static void printDiamond() {
        int size = 10;

        for (int i = 0; i < size; i++) {

            for (int j = size; j > 0; j++) {
                String a = String.format("%" + size + "s", StringUtils.repeat("*", i));
                System.out.println(a);
            }
        }
    }
}
