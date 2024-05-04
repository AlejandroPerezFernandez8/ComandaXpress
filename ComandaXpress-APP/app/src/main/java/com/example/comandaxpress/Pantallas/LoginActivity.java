package com.example.comandaxpress.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.LoginCallBack;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.R;

public class LoginActivity extends AppCompatActivity implements LoginCallBack {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences= getSharedPreferences("preferencias", MODE_PRIVATE);
        //Referencias a variables de actividad
        EditText nombreUsuario = findViewById(R.id.NombreUsuario);
        EditText contraseña = findViewById(R.id.Contraseña);
        Button btnRegistro = findViewById(R.id.btnRegistrarse);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Usuario",nombreUsuario.getText().toString());
                Log.d("contraseña",contraseña.getText().toString());
               UsuarioService.loginUsuario(getApplicationContext(),nombreUsuario.getText().toString(),contraseña.getText().toString(),LoginActivity.this);
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistro = new Intent(getApplicationContext(),RegistroActivity.class);
                someActivityResultLauncher.launch(intentRegistro);
            }});
    }

    ActivityResultLauncher someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                }
    });

    @Override
    public void onSuccess(Usuario usuario) {
        //SE GUARDA EL ESTADO DE LOGIN EN LAS PREFERENCIAS
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Logeado",true);
        editor.apply();
        //SE NOTIFICA AL USUARIO DE LA BIENVENIDA
        Toast.makeText(this,"Bienvenido " + usuario.getUsuario()+" !!",Toast.LENGTH_LONG).show();
        //SE PASA A LA PANTALLA DE MESAS
        Intent intentMesas = new Intent(getApplicationContext(),MesasActivity.class);
        someActivityResultLauncher.launch(intentMesas);
    }

    @Override
    public void onError(String message) {
        //SE NOTIFICA AL USUARIO DE QUE EL USUARIO NO EXISTE
        if (message.contains("AuthFailureError")){
            Toast.makeText(this,"El usuario no existe",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Error Inesperado",Toast.LENGTH_LONG).show();
        }
    }
}