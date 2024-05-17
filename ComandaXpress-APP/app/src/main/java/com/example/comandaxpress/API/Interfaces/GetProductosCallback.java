package com.example.comandaxpress.API.Interfaces;

import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.API.Clases.Producto;

import java.util.List;

public interface GetProductosCallback {
    void onSuccess(List<Producto> productos);
    void onError(String error);
}
