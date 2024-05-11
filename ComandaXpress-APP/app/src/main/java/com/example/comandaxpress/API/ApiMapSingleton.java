package com.example.comandaxpress.API;

public class ApiMapSingleton {
    private static ApiMap instance;

    // Constructor privado para prevenir instancia directa
    private ApiMapSingleton() {
    }
    public static synchronized ApiMap getInstance() {
        if (instance == null) {
            instance = new ApiMap();
        }
        return instance;
    }
}
