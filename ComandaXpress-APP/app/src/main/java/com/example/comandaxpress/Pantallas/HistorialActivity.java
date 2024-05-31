package com.example.comandaxpress.Pantallas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comandaxpress.API.Clases.FiltroTicket;
import com.example.comandaxpress.API.Clases.Ticket;
import com.example.comandaxpress.API.Interfaces.GetProductoCantidadCallback;
import com.example.comandaxpress.API.Interfaces.GetTicketsCallback;
import com.example.comandaxpress.API.TicketService;
import com.example.comandaxpress.Adapters.TicketAdapter;
import com.example.comandaxpress.Adapters.TicketProductoAdapter;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.example.comandaxpress.Pantallas.PantallasSecundarias.DialogoDeCarga;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.LocaleUtil;
import com.example.comandaxpress.Util.MensajeUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HistorialActivity extends AppCompatActivity {
    TicketAdapter adapter;
    List<Ticket> listaTickets = new ArrayList<>();
    DialogoDeCarga dialogoDeCarga = new DialogoDeCarga(HistorialActivity.this);
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        Button btnBusqueda = findViewById(R.id.buttonBuscar);
        EditText etNumeroMesa = findViewById(R.id.editTextNumeroMesa);
        EditText etFecha = findViewById(R.id.editTextFecha);
        ListView lista = findViewById(R.id.listaTickets);

        adapter = new TicketAdapter(this, listaTickets);
        lista.setAdapter(adapter);

        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero, fecha;
                numero = etNumeroMesa.getText().toString().trim();
                fecha = etFecha.getText().toString().trim();
                numero = numero.isEmpty() ? null : numero;
                fecha = fecha.isEmpty() ? null : fecha;

                if (fecha != null) {
                    fecha = validarYConvertirFecha(fecha);
                    if (fecha == null) {
                        MensajeUtils.mostrarError(HistorialActivity.this, R.string.errorFecha);
                        return;
                    }
                }
                realizarBusqueda(numero, fecha);
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
                MensajeUtils.mostrarError(HistorialActivity.this, "Error al recuperar los detalles del ticket");
            }
        });
    }

    private void mostrarDialogoDetalles(List<ProductoCantidad> detalles) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_ticket_detalles, null);

        ListView listViewDetalles = dialogView.findViewById(R.id.list_view_detalles);
        TicketProductoAdapter detalleAdapter = new TicketProductoAdapter(this, detalles);
        listViewDetalles.setAdapter(detalleAdapter);

        AlertDialog dialogo = new AlertDialog.Builder(this)
                .setTitle("Detalles del Ticket")
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .show();
        dialogo.setOnDismissListener(dialog -> dialogoDeCarga.dismissDialog());
    }

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
            return date.replace('/', '-');
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        EditText etNumeroMesa = findViewById(R.id.editTextNumeroMesa);
        EditText etFecha = findViewById(R.id.editTextFecha);

        outState.putString("numero_mesa", etNumeroMesa.getText().toString());
        outState.putString("fecha", etFecha.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        EditText etNumeroMesa = findViewById(R.id.editTextNumeroMesa);
        EditText etFecha = findViewById(R.id.editTextFecha);

        String savedNumeroMesa = savedInstanceState.getString("numero_mesa");
        String savedFecha = savedInstanceState.getString("fecha");

        if (savedNumeroMesa != null) {
            etNumeroMesa.setText(savedNumeroMesa);
        }
        if (savedFecha != null) {
            etFecha.setText(savedFecha);
        }

        realizarBusqueda(savedNumeroMesa, savedFecha);
    }

    private void realizarBusqueda(String numeroMesa, String fecha) {
        if (fecha != null && !fecha.isEmpty()) {
            fecha = validarYConvertirFecha(fecha);
            if (fecha == null) {
                MensajeUtils.mostrarError(HistorialActivity.this, R.string.errorFecha);
                return;
            }
        }else {
            fecha = null;
        }
        Log.d("Realizada", "principio - Fecha: " + fecha + ", Numero Mesa: " + numeroMesa);

        long startTime = System.currentTimeMillis();

        TicketService.filtrarTickets(HistorialActivity.this, new FiltroTicket(numeroMesa, fecha), new GetTicketsCallback() {
            @Override
            public void onGetTicketsSuccess(List<Ticket> tickets) {
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;

                Log.d("Tiempo de respuesta", "Duración: " + duration + " ms");

                adapter.clear();
                adapter.addAll(tickets);
                adapter.notifyDataSetChanged();
                Log.d("Realizada", "final - tamaño de tickets: " + tickets.size());
            }

            @Override
            public void onGetTicketsError(String error) {
                MensajeUtils.mostrarError(HistorialActivity.this, R.string.errorHistorialTicket);
            }
        });
    }


    @Override
    protected void onDestroy() {
        LocaleUtil.loadLocale(HistorialActivity.this);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        LocaleUtil.loadLocale(HistorialActivity.this);
        super.onStop();
    }
}
