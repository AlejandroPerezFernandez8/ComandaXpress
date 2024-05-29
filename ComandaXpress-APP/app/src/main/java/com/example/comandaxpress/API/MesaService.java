package com.example.comandaxpress.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Interfaces.GetAllMesasCallback;
import com.example.comandaxpress.API.Interfaces.ModificacionMesaCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * CLASE PARA LA GESTION DE LAS PETICIONES SOBRE LA TABLA MESAS
 * -getAllMesas : Devuelve una lista de todas las mesas
 * -updateMesa : Funcion para hacer una modificacion sobre una mesa
 * */
public class MesaService {
    public static void getAllMesas(Context context, GetAllMesasCallback callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiMapSingleton.getInstance().getUrlMesa(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("MesaService", "Respuesta de la API: " + response.toString());
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Mesa>>(){}.getType();
                        List<Mesa> mesas = gson.fromJson(response.toString(), listType);
                        Log.d("MesaService", "Mesas convertidas: " + mesas);
                        callback.onSuccess(mesas);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MesaService", "Error al recuperar las mesas: " + error.toString());
                callback.onError("Error al recuperar las mesas: " + error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }


    public static void updateMesa(Context context, Mesa mesa, ModificacionMesaCallback callback) {
        String url = ApiMapSingleton.getInstance().getUrlMesa() + "/" + mesa.getMesaId();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onModificacionSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Error desconocido";
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                }
                callback.onModificacionFailed(errorMessage);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String json = gson.toJson(mesa);
                return json.getBytes(StandardCharsets.UTF_8);
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
