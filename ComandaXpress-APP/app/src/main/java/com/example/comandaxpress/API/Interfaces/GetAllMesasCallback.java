package com.example.comandaxpress.API.Interfaces;

import com.example.comandaxpress.API.Clases.Mesa;

import java.util.List;

public interface GetAllMesasCallback {
    void onSuccess(List<Mesa> mesas);
    void onError(String error);
}