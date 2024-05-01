package com.example.comandaxpress.Pantallas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.R;

public class RegistroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        EditText nombre = findViewById(R.id.Nombre);
        EditText apellidos = findViewById(R.id.Apellidos);
        EditText email= findViewById(R.id.Email);
        EditText nombreUsuario = findViewById(R.id.NombreUsuario);
        EditText Contraseña = findViewById(R.id.Contraseña);
        Button btnRegistro = findViewById(R.id.btnRegistrarse);
    }
}