package com.example;


import java.io.Serializable;

public class VeryLargeObject implements Serializable {
    public static final int SIZE = 1 << 12;

    public String tag;
    public int[][] bigOne = new int[SIZE][SIZE];

    {
        // Initialize bigOne
        for(int i = 0; i < SIZE ; ++i) {
            for(int j = 0; j < SIZE; ++j) {
                bigOne[i][j] = (int) (Math.random() * 100);
            }
        }
    }

    public VeryLargeObject(String tag) {
        this.tag = tag;
    }

    public static void main(String args[]) {
        VeryLargeObject[] vla = new VeryLargeObject[1 << 12];
        for(int i = 0; i < Integer.MAX_VALUE; ++i) {
            vla[i] = new VeryLargeObject("aa");
        }
    }
}