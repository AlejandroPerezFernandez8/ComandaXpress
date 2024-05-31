package com.example.comandaxpress.API;

public class ApiMapSingleton {
    private static ApiMap instance;
    private ApiMapSingleton() {
    }
    public static synchronized ApiMap getInstance() {
        if (instance == null) {
            instance = new ApiMap();
        }
        return instance;
    }
}
