package com.example.comandaxpress.API;

public class ApiMap {
    private static String IP = "";
    private static final String PORT = ":8080/";

    // Método para obtener la base URL
    public static String getBaseUrl() {
        return "http://" + getIP() + PORT;
    }

    // Métodos para obtener las URLs completas
    public static String getUrlUsuarioLogin() {
        return getBaseUrl() + "usuarios/login";
    }

    public static String getUrlUsuarioRegistro() {
        return getBaseUrl() + "usuarios/saveUsuarios";
    }

    public static String getUrlMesa() {
        return getBaseUrl() + "mesa";
    }

    public static void setIP(String ip) {
        IP = ip;
    }

    public static String getIP() {
        return IP;
    }
}
