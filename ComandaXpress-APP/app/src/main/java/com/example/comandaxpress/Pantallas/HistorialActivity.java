package com.example.comandaxpress.Pantallas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.Clases.FiltroTicket;
import com.example.comandaxpress.API.Clases.Ticket;
import com.example.comandaxpress.API.Clases.TicketDetalle;
import com.example.comandaxpress.API.Interfaces.GetProductoCantidadCallback;
import com.example.comandaxpress.API.Interfaces.GetTicketsCallback;
import com.example.comandaxpress.API.TicketService;
import com.example.comandaxpress.Adapters.ProductoAdapter;
import com.example.comandaxpress.Adapters.TicketAdapter;
import com.example.comandaxpress.Adapters.TicketProductoAdapter;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.example.comandaxpress.Pantallas.PantallasSecundarias.DialogoDeCarga;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.MensajeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HistorialActivity extends AppCompatActivity {
    TicketAdapter adapter;
    List<Ticket> listaTickets = new ArrayList<>();
    DialogoDeCarga dialogoDeCarga = new DialogoDeCarga(HistorialActivity.this);
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial);
        Button btnBusqueda =  findViewById(R.id.buttonBuscar);
        EditText etNumeroMesa = findViewById(R.id.editTextNumeroMesa);
        EditText etFecha = findViewById(R.id.editTextFecha);
        ListView lista = findViewById(R.id.listaTickets);
        DialogoDeCarga dialogoDeCarga = new DialogoDeCarga(HistorialActivity.this);
        adapter = new TicketAdapter(this,listaTickets);
        lista.setAdapter(adapter);
        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero, fecha;
                numero = etNumeroMesa.getText().toString().trim();
                fecha = etFecha.getText().toString().trim();
                numero = numero.isEmpty() ? null : numero;
                fecha = fecha.isEmpty() ? null : fecha;


                if (fecha!= null){
                    fecha = validarYConvertirFecha(fecha);
                    if(fecha == null){
                        MensajeUtils.mostrarError(HistorialActivity.this,R.string.errorFecha);
                        return;
                    }
                }
                TicketService.filtrarTickets(HistorialActivity.this, new FiltroTicket(numero, fecha), new GetTicketsCallback() {
                    @Override
                    public void onGetTicketsSuccess(List<Ticket> tickets) {
                        adapter.clear();
                        adapter.addAll(tickets);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onGetTicketsError(String error) {
                        MensajeUtils.mostrarError(HistorialActivity.this,R.string.errorHistorialTicket);
                    }
                });
            }
        });


    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            obtenerDetallesTicket(adapter.getItem(position));
        }
    });








    }


    private void obtenerDetallesTicket(Ticket ticket) {
        dialogoDeCarga.startLoadingDialog();
        TicketService.getDetallesDeTicket(this, ticket.getTicketId(), new GetProductoCantidadCallback() {
            @Override
            public void onGetProductosSuccess(List<ProductoCantidad> productoCantidadList) {
                mostrarDialogoDetalles(productoCantidadList);
            }

            @Override
            public void onGetProductosError(String error) {
                dialogoDeCarga.dismissDialog();
                MensajeUtils.mostrarError(HistorialActivity.this,"Error al recuperar los detalles del ticket");
            }
        });
    }

    private void mostrarDialogoDetalles(List<ProductoCantidad> detalles) {
        // Infla el layout del diálogo
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_ticket_detalles, null);

        // Configura el ListView del diálogo
        ListView listViewDetalles = dialogView.findViewById(R.id.list_view_detalles);
        TicketProductoAdapter detalleAdapter = new TicketProductoAdapter(this, detalles);
        listViewDetalles.setAdapter(detalleAdapter);


        // Muestra el diálogo
        AlertDialog dialogo = new AlertDialog.Builder(this)
                .setTitle("Detalles del Ticket")
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .show();
        dialogo.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogoDeCarga.dismissDialog();
            }
        });
    }

    // Método para validar y convertir la fecha
    public static String validarYConvertirFecha(String date) {
        String regexGuion = "^\\d{4}-\\d{2}-\\d{2}$";
        String regexBarra = "^\\d{4}/\\d{2}/\\d{2}$";

        Pattern patternGuion = Pattern.compile(regexGuion);
        Matcher patternBarra = patternGuion.matcher(date);
        Pattern patternSlash = Pattern.compile(regexBarra);
        Matcher matcherSlash = patternSlash.matcher(date);

        if (patternBarra.matches()) {
            return date;
        } else if (matcherSlash.matches()) {
            String convertedDate = date.replace('/', '-');
            if (patternGuion.matcher(convertedDate).matches()) {
                return convertedDate;
            }
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(listaTickets != null){
            String jsonTickets = gson.toJson(listaTickets);
            outState.putString("lista", jsonTickets);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String jsonTickets = savedInstanceState.getString("lista");
        if (jsonTickets != null) {
            Type type = new TypeToken<ArrayList<Ticket>>(){}.getType();
            listaTickets = gson.fromJson(jsonTickets, type);
            adapter.addAll(listaTickets);
            adapter.notifyDataSetChanged();
        }
    }



}