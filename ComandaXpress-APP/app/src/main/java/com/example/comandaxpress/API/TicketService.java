package com.example.comandaxpress.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.comandaxpress.API.Clases.Ticket;
import com.example.comandaxpress.API.Clases.TicketDetalleSimplificado;
import com.example.comandaxpress.API.Interfaces.GetProductoCantidadCallback;
import com.example.comandaxpress.API.Interfaces.InsertProductosCallback;
import com.example.comandaxpress.API.Interfaces.InsertTickectCallback;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {
    public static void insertTicket(Context context, Ticket ticket, InsertTickectCallback callback) {
        String url = ApiMapSingleton.getInstance().getUrlTicket();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onInsertSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Error desconocido";
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                }
                callback.onInsertFailed(errorMessage);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String json = gson.toJson(ticket);
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




    public static void getDetallesDeTicket(Context context, Long ticketId, final GetProductoCantidadCallback callback) {
        String url = ApiMapSingleton.getInstance().getUrlTicketDetalle(ticketId);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                JSONObject jsonObject = response.getJSONObject(0);
                                JSONArray productosArray = jsonObject.getJSONArray("productos");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<ProductoCantidad>>(){}.getType();
                                List<ProductoCantidad> productoCantidadList = gson.fromJson(productosArray.toString(), listType);
                                callback.onGetProductosSuccess(productoCantidadList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onGetProductosError(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onGetProductosError(error.toString());
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }



    public static void insertarTicketDetalle(Context context, TicketDetalleSimplificado ticketDetalle, final InsertProductosCallback callback) {
        String url = ApiMapSingleton.getInstance().getUrlTicketDetalleGuardar();

        // Convertir TicketDetalleSimplificadoDTO a JSONObject
        Gson gson = new Gson();
        String jsonString = gson.toJson(ticketDetalle);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onInsertSuccess("Detalle guardado con éxito!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onInsertError("Error al guardar detalle: " + error.toString());
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }


}