package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamDuplicate {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 4);
        Optional<Integer> any = integers.stream().findAny();
        System.out.println("any = " + any);
        Optional<Integer> reduce = integers.stream()
                .filter(i -> i == 4)
                .reduce((i1, i2) -> {
                    System.out.format("i1 = %s ,i2  = %s \n", i1, i2);
                    return i1;
                });

        System.out.println("reduce = " + reduce);
    }
}
