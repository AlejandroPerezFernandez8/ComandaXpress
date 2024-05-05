package com.example.comandaxpress.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Interfaces.GetAllMesasCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MesaService {
    public static void getAllMesas(Context context, GetAllMesasCallback callback) {
        String url = "http://192.168.1.131:8080/mesa";  // Asegúrate de que esta URL es correcta

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Mesa>>(){}.getType();
                        List<Mesa> mesas = gson.fromJson(response.toString(), listType);
                        callback.onSuccess(mesas);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Error al recuperar las mesas: " + error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }
}
