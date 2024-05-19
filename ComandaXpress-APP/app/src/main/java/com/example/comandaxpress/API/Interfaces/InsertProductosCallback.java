package com.example.comandaxpress.API.Interfaces;

import com.example.comandaxpress.API.Clases.Producto;

import java.util.List;

public interface InsertProductosCallback {
    void onInsertSuccess(String message);
    void onInsertError(String error);
}
