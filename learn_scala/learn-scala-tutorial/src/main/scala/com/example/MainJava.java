package com.example;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by mohanraj.nagasamy on 11/6/15.
 */
public class MainJava {
    public static void main(String[] args) {

        Map<Integer, Integer> map = new TreeMap<>();
        map.put(1, 30);
        map.put(3, 20);
        map.put(2, 10);
        map.put(1, 49);

        System.out.println("map = " + map);
    }
}

abstract class Product{
    String name;

    Product(String name) {
        this.name = name;
    }

    public final void printProduct() {
        System.out.println(name);
    }

//    public void printLabel();
}