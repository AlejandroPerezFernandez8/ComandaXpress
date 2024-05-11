package com.example.comandaxpress.Pantallas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;

public class AjustesActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfilAjustes);
        TextView nombreUsuario = findViewById(R.id.nombreUsuarioAjustes);

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



    }
}