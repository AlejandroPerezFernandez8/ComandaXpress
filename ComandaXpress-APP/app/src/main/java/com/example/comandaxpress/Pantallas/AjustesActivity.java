package com.example.comandaxpress.Pantallas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.ApiMapSingleton;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.SQLiteUtils;

public class AjustesActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfilAjustes);
        TextView nombreUsuario = findViewById(R.id.nombreUsuarioAjustes);
        EditText etCambiarIP = findViewById(R.id.etCambioIP);
        Button btnCambiarIP = findViewById(R.id.btnCambiarIP);
        Spinner languageSpinner = findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        languageSpinner.setAdapter(adapter);

        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        Usuario usuario;
        try {
            usuario = CryptoUtils.transformarJsonToUsuaro(
                    CryptoUtils.desencriptar(sharedPreferences.getString("Usuario",""), "abc123.")
            );
            fotoPerfil.setImageBitmap(CryptoUtils.decodeBase64ToBitmap(usuario.getFoto()));
            nombreUsuario.setText(usuario.getNombre() + " " + usuario.getApellido());
        } catch (Exception e) {}

        btnCambiarIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip =  etCambiarIP.getText().toString();
                if(ip != null || ip.isEmpty() == false){
                    try {
                        if(SQLiteUtils.getIP(AjustesActivity.this) != null) {
                            SQLiteUtils.modificarIP(AjustesActivity.this, ip);
                        }else{
                            SQLiteUtils.insertarIP(AjustesActivity.this,ip);
                        }
                        ApiMapSingleton.getInstance().setIP(SQLiteUtils.getIP(AjustesActivity.this));
                        Toast.makeText(AjustesActivity.this, "IP cambiada", Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(AjustesActivity.this, "Error al cambiar la IP", Toast.LENGTH_SHORT).show();
                        Log.d("Error IP",ex.getMessage());
                    }
                }
            }
        });



    }
}