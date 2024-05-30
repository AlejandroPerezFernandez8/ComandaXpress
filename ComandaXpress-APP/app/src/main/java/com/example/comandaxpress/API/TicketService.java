package com.example.comandaxpress.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.comandaxpress.API.Clases.FiltroTicket;
import com.example.comandaxpress.API.Clases.Ticket;
import com.example.comandaxpress.API.Clases.TicketDetalleSimplificado;
import com.example.comandaxpress.API.Interfaces.DeleteTicketDetalleCallback;
import com.example.comandaxpress.API.Interfaces.GetProductoCantidadCallback;
import com.example.comandaxpress.API.Interfaces.GetTicketsCallback;
import com.example.comandaxpress.API.Interfaces.InsertProductosCallback;
import com.example.comandaxpress.API.Interfaces.InsertTickectCallback;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Clase para la gestion de las peticiones sobre la tabla ticket y ticket detalle
 *
 * -insertTicket : Realiza la peticion para insertar un nuevo ticket
 * -getDetallesTicket : Realiza la peticion para obtener todos los objetos TicketDetalle de un ticket concreto
 * -insertTicketDetalle : Realiza la peticion para hacer el insert de un objeto TicketDetalle
 * -eliminarTicketDetalle :  Realiza la peticion para hacer la eliminacion de un objeto ticketDetalle
 * */
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
                            }else{
                                callback.onGetProductosError("No hay productos");
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

    public static void eliminarTicketDetalle(Context context, TicketDetalleSimplificado ticketDetalle, final DeleteTicketDetalleCallback callback) {
        String url = ApiMapSingleton.getInstance().getUrlTicketDetalleEliminar();

        Gson gson = new Gson();
        String jsonString = gson.toJson(ticketDetalle);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("EliminarTicketDetalle", "URL: " + url);
        Log.d("EliminarTicketDetalle", "JSON: " + jsonString);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,  // Usar POST temporalmente
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("EliminarTicketDetalle", "Response: " + response);
                        callback.onDeleteSuccess("Detalle eliminado con éxito!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error al eliminar detalle: ";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        } else {
                            errorMessage += error.toString();
                        }
                        Log.d("EliminarTicketDetalle", errorMessage);
                        callback.onDeleteError(errorMessage);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return jsonString == null ? null : jsonString.getBytes(StandardCharsets.UTF_8);
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


    public static void filtrarTickets(Context context, FiltroTicket filtros, final GetTicketsCallback callback) {
        String url = ApiMapSingleton.getInstance().getUrlTicketFiltros(filtros);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Ticket>>(){}.getType();
                            List<Ticket> ticketList = gson.fromJson(response.toString(), listType);
                            callback.onGetTicketsSuccess(ticketList);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onGetTicketsError(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onGetTicketsError(error.toString());
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }

}
