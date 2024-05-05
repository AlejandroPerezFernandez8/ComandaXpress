package com.example.comandaxpress.Pantallas;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Interfaces.GetAllMesasCallback;
import com.example.comandaxpress.API.MesaService;
import com.example.comandaxpress.Adapters.MesaAdapter;
import com.example.comandaxpress.R;

import java.util.List;

public class MesasActivity extends AppCompatActivity implements GetAllMesasCallback {

    MesaAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        MesaService.getAllMesas(this,this);
    }

    @Override
    public void onSuccess(List<Mesa> mesas) {
        Toast.makeText(this, "Cargado de mesas", Toast.LENGTH_LONG).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adaptador == null) {
                    adaptador = new MesaAdapter(MesasActivity.this, mesas);
                    ListView lista = findViewById(R.id.listaMesas);
                    lista.setAdapter(adaptador);
                } else {
                    adaptador.clear();
                    adaptador.addAll(mesas);
                    adaptador.notifyDataSetChanged();
                }
                Toast.makeText(MesasActivity.this, "Cargado de mesas: " + mesas.size(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, "Error cargando Mesas", Toast.LENGTH_LONG).show();
    }
}