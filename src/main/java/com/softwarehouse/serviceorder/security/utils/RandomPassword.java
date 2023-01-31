package com.softwarehouse.serviceorder.security.utils;

import java.util.Random;

public class RandomPassword {
    public static String generate() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            password.append(random.nextInt(10));
        }

        return password.toString();
    }
}
