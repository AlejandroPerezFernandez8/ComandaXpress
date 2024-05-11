package com.example.comandaxpress.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.GetAllMesasCallback;
import com.example.comandaxpress.API.MesaService;
import com.example.comandaxpress.Adapters.MesaAdapter;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.SQLiteUtils;

import java.util.List;

public class MesasActivity extends AppCompatActivity implements GetAllMesasCallback {
    SharedPreferences sharedPreferences ;
    MesaAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        MesaService.getAllMesas(this,this);
        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfilMesas);
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
                startActivity(intentAjustes);
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
                    ListView lista = findViewById(R.id.listaMesas);
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


}