package com.example;

public class Main {
    public static void main(String[] args) {
        String url = "stg.appreciatehub.com";
        if (url.matches(".*stage\\.appreciatehub\\.com.*") ||
                url.matches(".*stg\\.appreciatehub\\.com.*") ||
                url.matches(".*stage\\.perfork\\.appreciatehub\\.com.*") ||
                url.matches(".*stg\\.perfork\\.appreciatehub\\.com.*")) {

            System.out.println("https://stage.appreciatehub.com");
        }

    }
}
