package br.com.planejizze.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
