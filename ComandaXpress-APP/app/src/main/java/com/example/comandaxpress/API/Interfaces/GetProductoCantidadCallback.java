package com.example.comandaxpress.API.Interfaces;

import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;

import java.util.List;

public interface GetProductoCantidadCallback {
    void onGetProductosSuccess(List<ProductoCantidad> productoCantidadList);
    void onGetProductosError(String error);
}
