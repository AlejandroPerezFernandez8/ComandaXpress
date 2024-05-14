package com.example.comandaxpress.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.google.gson.Gson;

public class Mesa_ticket_Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa_ticket);
        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        TextView numeroMesa = findViewById(R.id.tituloMesaTicket);
        TextView numeroComensales = findViewById(R.id.numComensales);
        ImageView fotoperfil = findViewById(R.id.fotoPerfilMesaTicket);


        Mesa mesa = new Gson().fromJson(getIntent().getExtras().getString("Mesa"), Mesa.class);
        numeroMesa.setText("Mesa Número "+mesa.getNumero());
        numeroComensales.setText("Comensales: "+mesa.getCapacidad());
        try {
            Usuario usuario;
            String encriptedUser = sharedPreferences.getString("Usuario","");
            String jsonUsuario = CryptoUtils.desencriptar(encriptedUser,"abc123.");
            usuario = CryptoUtils.transformarJsonToUsuaro(jsonUsuario);
            if(!usuario.getFoto().isEmpty() || usuario.getFoto() == null){
                fotoperfil.setImageBitmap(CryptoUtils.decodeBase64ToBitmap(usuario.getFoto()));
            }
        }catch (Exception ex){}

        fotoperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjustes = new Intent(getApplicationContext(),AjustesActivity.class);
                someActivityResultLauncher.launch(intentAjustes);
            }
        });

    }


    ActivityResultLauncher someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    recreate();
                }
            });

}