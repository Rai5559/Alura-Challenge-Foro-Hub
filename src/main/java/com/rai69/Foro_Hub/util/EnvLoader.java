package com.rai69.Foro_Hub.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    static {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}
