package com.example.comandaxpress.API.Interfaces;

import com.example.comandaxpress.API.Clases.Usuario;

public interface LoginCallBack {
    void onSuccess(Usuario usuario);
    void onError(String message);
}
