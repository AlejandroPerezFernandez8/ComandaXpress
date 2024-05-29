package com.example.comandaxpress.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.LoginCallBack;
import com.example.comandaxpress.API.Interfaces.ModificacionCallback;
import com.example.comandaxpress.API.Interfaces.RegistroCallback;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UsuarioService {
    /**
     * Clase para la gestion de las peticiones sobre la tabla usuario
     *
     * -loginUsuario: Realiza una peticion con cuerpo[usuario,contraseña] que devuelve el objeto usuario completo coincidente
     * -registro usuario: Realia un peticion para el registro de un nuevo usuario
     * -modificarUsuario: Realiza una peticion para la modificacion de un usuario existente
     * */

    public static void loginUsuario(Context context, String nombreUsuario, String contraseña, LoginCallBack callBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiMapSingleton.getInstance().getUrlUsuarioLogin(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Login Response", response);
                        Gson gson = new Gson();
                        Usuario usuario = gson.fromJson(response, Usuario.class);
                        Log.d("usuario",usuario.toString());
                        callBack.onSuccess(usuario);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login Error", "Error en la solicitud: " +ApiMapSingleton.getInstance().getUrlUsuarioLogin()+ error.toString()) ;
                callBack.onError(error.toString());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", nombreUsuario);
                params.put("contraseña", contraseña);
                return new Gson().toJson(params).getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }



    public static void registrarUsuario(Context context, Usuario usuario, RegistroCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiMapSingleton.getInstance().getUrlUsuarioRegistro(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onRegistroSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error desconocido";  // Mensaje por defecto
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        }
                        callback.onRegistroFailed(errorMessage);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String payload = gson.toJson(usuario);
                return payload.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
    public static void modificarUsuario(Context context, long usuarioId, Usuario usuario, ModificacionCallback callback) {
        String url = ApiMapSingleton.getInstance().getUrlUsuarioModificar(usuarioId);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onRegistroSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo del error
                        String errorMessage = "Error desconocido";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        }
                        callback.onRegistroFailed(errorMessage);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String payload = gson.toJson(usuario);
                return payload.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }




}
