package com.example.comandaxpress.Pantallas;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comandaxpress.API.ApiMapSingleton;
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
import com.example.comandaxpress.Util.SQLiteUtils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public class MesasActivity extends AppCompatActivity implements GetAllMesasCallback, ModificacionMesaCallback, InsertTickectCallback {
    SharedPreferences sharedPreferences ;
    SwipeRefreshLayout recarga;
    MesaAdapter adaptador;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        EdgeToEdge.enable(this);
        MesaService.getAllMesas(this,this);
        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfilMesas);
        recarga = findViewById(R.id.recarga);
        lista = findViewById(R.id.listaMesas);

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
                                            Intent intentTicket = new Intent(MesasActivity.this, Mesa_ticket_Activity.class);
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
                    Intent intentTicket = new Intent(MesasActivity.this,Mesa_ticket_Activity.class);
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
        Toast.makeText(this, "Error cargando Mesas", Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "Error al modificar estado de la mesa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInsertSuccess(String response) {

    }

    @Override
    public void onInsertFailed(String errorMessage) {
        Toast.makeText(this, "Error al generar un ticket nuevo", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MesasActivity.this, "Error al recargar datos: " + error, Toast.LENGTH_SHORT).show();
                recarga.setRefreshing(false);
            }
        });
    }

}