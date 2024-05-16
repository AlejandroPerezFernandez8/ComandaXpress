package com.example.comandaxpress.API.Interfaces;

import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.API.Clases.Mesa;

import java.util.List;

public interface GetAllCategoriasCallback {
    void onSuccess(List<Categoria> categorias);
    void onError(String error);
}
