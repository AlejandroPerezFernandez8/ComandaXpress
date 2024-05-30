package com.example.comandaxpress.API.Interfaces;

import com.example.comandaxpress.API.Clases.Ticket;

import java.util.List;

public interface GetTicketsCallback {
    public void onGetTicketsSuccess(List<Ticket> tickets);
    public void onGetTicketsError(String error);
}
