package com.example.comandaxpress.Pantallas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.RegistroCallback;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.R;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity implements RegistroCallback {
    EditText nombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        EditText nombre = findViewById(R.id.Nombre);
        EditText apellidos = findViewById(R.id.Apellidos);
        EditText email= findViewById(R.id.Email);
        nombreUsuario = findViewById(R.id.NombreUsuario);
        EditText contraseña = findViewById(R.id.Contraseña);
        Button btnRegistro = findViewById(R.id.btnRegistrarse);
        List<EditText> editTexts = new ArrayList<>();
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTexts.add(nombre);
                editTexts.add(apellidos);
                editTexts.add(email);
                editTexts.add(nombreUsuario);
                editTexts.add(contraseña);
                if(!comprobarVacio(editTexts)){return;}
                Usuario usuario = new Usuario(
                    nombre.getText().toString(),
                    apellidos.getText().toString(),
                    email.getText().toString(),
                    nombreUsuario.getText().toString(),
                    contraseña.getText().toString()
                );
                UsuarioService.registrarUsuario(getApplicationContext(),usuario,RegistroActivity.this);
            }
        });

        nombreUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                int color = getResources().getColor(R.color.colorLightBlue);
                nombreUsuario.setTextColor(color);
            }
        });
    }

    @Override
    public void onRegistroSuccess(String response) {
        Toast.makeText(this,response,Toast.LENGTH_LONG).show();
        //Se pasa a la pantalla de login
        Intent intentLogin = new Intent(getApplicationContext(),LoginActivity.class);
        someActivityResultLauncher.launch(intentLogin);
    }
    @Override
    public void onRegistroFailed(String error) {
        Toast.makeText(this,error, Toast.LENGTH_LONG).show();
        if(error.contains("Usuario duplicado")) {
            nombreUsuario.setTextColor(Color.RED);
        }
    }

    public boolean comprobarVacio(List<EditText> editTexts){
        for (EditText editText: editTexts) {
            if(editText.getText().toString().isEmpty()){
                editText.setHintTextColor(Color.RED);
                editText.requestFocus();
                return false;
            }
        }
        return true;
    }

    ActivityResultLauncher someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                }
            });
}