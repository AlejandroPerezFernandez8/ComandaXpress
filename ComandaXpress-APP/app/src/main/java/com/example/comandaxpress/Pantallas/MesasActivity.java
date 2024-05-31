package com.example.comandaxpress.Pantallas;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Clases.Ticket;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.GetAllMesasCallback;
import com.example.comandaxpress.API.Interfaces.InsertTickectCallback;
import com.example.comandaxpress.API.Interfaces.ModificacionMesaCallback;
import com.example.comandaxpress.API.MesaService;
import com.example.comandaxpress.API.TicketService;
import com.example.comandaxpress.Adapters.MesaAdapter;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.LocaleUtil;
import com.example.comandaxpress.Util.MensajeUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MesasActivity extends AppCompatActivity implements GetAllMesasCallback, ModificacionMesaCallback, InsertTickectCallback {
    SharedPreferences sharedPreferences ;
    SwipeRefreshLayout recarga;
    MesaAdapter adaptador;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleUtil.loadLocale(MesasActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        EdgeToEdge.enable(this);
        MesaService.getAllMesas(this,this);
        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfilMesas);
        recarga = findViewById(R.id.recarga);
        lista = findViewById(R.id.listaMesas);
        ImageView historial = findViewById(R.id.historial);
        try {
            Usuario usuario;
            String encriptedUser = sharedPreferences.getString("Usuario","");
            String jsonUsuario = CryptoUtils.desencriptar(encriptedUser,"abc123.");
            usuario = CryptoUtils.transformarJsonToUsuaro(jsonUsuario);
            if(!usuario.getFoto().isEmpty() || usuario.getFoto() == null){
                fotoPerfil.setImageBitmap(CryptoUtils.decodeBase64ToBitmap(usuario.getFoto()));
            }
        }catch (Exception ex){}
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjustes = new Intent(getApplicationContext(),AjustesActivity.class);
                someActivityResultLauncher.launch(intentAjustes);
            }
        });

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historial = new Intent(getApplicationContext(),HistorialActivity.class);
                someActivityResultLauncher.launch(historial);
            }
        });

        recarga.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos();
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mesa mesaSeleccionada = adaptador.getItem(position);
                if(!mesaSeleccionada.getActiva()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MesasActivity.this);
                        LayoutInflater inflater = MesasActivity.this.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.abrir_mesa, null);
                        builder.setView(dialogView);

                        AlertDialog dialog = builder.create();

                        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
                        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                        btnConfirm.setOnClickListener(b -> {
                            mesaSeleccionada.setActiva(!mesaSeleccionada.getActiva());
                            MesaService.updateMesa(MesasActivity.this,mesaSeleccionada,MesasActivity.this);
                            Ticket ticket = new Ticket(mesaSeleccionada.getMesaId());
                            TicketService.insertTicket(MesasActivity.this, ticket, new InsertTickectCallback() {
                                @Override
                                public void onInsertSuccess(String response) {
                                    MesaService.getAllMesas(MesasActivity.this, new GetAllMesasCallback() {
                                        @Override
                                        public void onSuccess(List<Mesa> mesas) {
                                            Optional<Mesa> mesaOptional = mesas.stream()
                                                    .filter(mesax -> mesax.getMesaId().equals(mesaSeleccionada.getMesaId()))
                                                    .findFirst();
                                            Intent intentTicket = new Intent(MesasActivity.this, MesaTicketActivity.class);
                                            sharedPreferences.edit().putString("Mesa", new Gson().toJson(mesaOptional.get())).apply();
                                            someActivityResultLauncher.launch(intentTicket);
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onError(String error) {
                                            Log.e("MESA_SERVICE_ERROR", error);
                                        }
                                    });
                                }

                                @Override
                                public void onInsertFailed(String errorMessage) {
                                    Log.e("Ticket","Error de insercion de ticket" + errorMessage);
                                }
                            });
                        });
                        btnCancel.setOnClickListener(b -> {dialog.dismiss();});
                        dialog.show();
                }else{
                    Intent intentTicket = new Intent(MesasActivity.this, MesaTicketActivity.class);
                    sharedPreferences.edit().putString("Mesa",new Gson().toJson(mesaSeleccionada)).apply();
                    someActivityResultLauncher.launch(intentTicket);
                }
            }
        });

    }




    @Override
    public void onSuccess(List<Mesa> mesas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adaptador == null) {
                    adaptador = new MesaAdapter(MesasActivity.this, mesas);
                    lista.setAdapter(adaptador);
                } else {
                    adaptador.clear();
                    adaptador.addAll(mesas);
                    adaptador.notifyDataSetChanged();
                }
            }
        });
    }
    @Override
    public void onError(String error) {
        MensajeUtils.mostrarError(MesasActivity.this,R.string.errorCargaMesas);
    }


    ActivityResultLauncher someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    recreate();
                }
            });


    @Override
    public void onModificacionSuccess(String response) {

    }

    @Override
    public void onModificacionFailed(String errorMessage) {
        MensajeUtils.mostrarError(MesasActivity.this,R.string.errorModificacionEstadoMesa);
    }

    @Override
    public void onInsertSuccess(String response) {

    }

    @Override
    public void onInsertFailed(String errorMessage) {
        MensajeUtils.mostrarError(MesasActivity.this,R.string.errorNuevoTiket);
        Log.d("TicketError",errorMessage);
    }

    private void cargarDatos() {
        MesaService.getAllMesas(MesasActivity.this, new GetAllMesasCallback() {
            @Override
            public void onSuccess(List<Mesa> mesas) {
                adaptador.clear();
                adaptador.addAll(new ArrayList<>(mesas));
                adaptador.notifyDataSetChanged();
                recarga.setRefreshing(false);
            }
            @Override
            public void onError(String error) {
                MensajeUtils.mostrarError(MesasActivity.this,R.string.errorRecargaDatos);
                recarga.setRefreshing(false);
            }
        });
    }

}