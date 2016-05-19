package com.java8;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntRange {
    public static boolean isPrime(int number) {
        boolean isPrime = number > 1 && IntStream.range(2, number).noneMatch(i -> number % i == 0);
        System.out.println(String.format("number %s is primme?: %s ", number, isPrime));
        return isPrime;
    }

    public static void main(String[] args) {
//        ex1();
//        ex2();
//        ex3();
        ex4();
    }

    private static void ex4() {
        List<Person> persons = Arrays.asList(
                new Person("Sara", 12),
                new Person("Mark", 43),
                new Person("Bob", 12),
                new Person("Jill", 64));

        System.out.println("persons = " + persons);

        persons.sort(Comparator.comparing(Person::getAge));

        System.out.println("persons = " + persons);

        persons.sort(Comparator.comparing(Person::getAge).thenComparing(Person::getName));

        System.out.println("persons = " + persons);

    }

    private static void ex3() {
        File file = new File(".");

        File[] files = file.listFiles();

        String collect = Stream.of(files)
                .map(File::getName)
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));
        System.out.println("files = " + collect);
    }

    private static void ex2() {
        System.out.println("Is Prime");
        List<Double> list = Stream.iterate(1, e -> e + 1)
                .filter(IntRange::isPrime)
                .map(Math::sqrt)
                .limit(10)
                .collect(Collectors.toList());
        System.out.println("list = " + list);

    }

    private static void ex1() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10)
                .forEach(i ->
                        executorService.submit(() ->
                                System.out.println("Running taks: " + i)));
        executorService.shutdown();
    }
}
