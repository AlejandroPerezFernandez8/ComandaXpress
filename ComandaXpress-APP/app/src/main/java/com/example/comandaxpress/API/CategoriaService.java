package com.example.comandaxpress.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.API.Interfaces.GetAllCategoriasCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    public static void getAllCategorias(Context context, GetAllCategoriasCallback callback) {
        String urlCategorias = ApiMapSingleton.getInstance().getUrlCategoria();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlCategorias, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Categoria>>(){}.getType();
                        List<Categoria> categorias = gson.fromJson(response.toString(), listType);
                        callback.onSuccess(categorias);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Error al recuperar las categorías: " + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }
}
