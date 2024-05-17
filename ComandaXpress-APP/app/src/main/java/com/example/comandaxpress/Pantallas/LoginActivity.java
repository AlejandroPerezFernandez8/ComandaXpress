package com.example.comandaxpress.Pantallas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

import com.example.comandaxpress.API.ApiMap;
import com.example.comandaxpress.API.ApiMapSingleton;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.LoginCallBack;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.R;
import com.example.comandaxpress.SQLite.FeedReaderDbHelper;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.SQLiteUtils;

public class LoginActivity extends AppCompatActivity implements LoginCallBack {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EdgeToEdge.enable(this);
        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);

        if (SQLiteUtils.getIP(this) == null) {
            solicitarIP();
        }else {
            ApiMapSingleton.getInstance().setIP(SQLiteUtils.getIP(this));
        }
        if("noExiste" != sharedPreferences.getString("Usuario","noExiste")){
            //SE PASA A LA PANTALLA DE MESAS
            Intent intentMesas = new Intent(getApplicationContext(),MesasActivity.class);
            someActivityResultLauncher.launch(intentMesas);
            finish();
        }
        //Referencias a variables de actividad
        EditText nombreUsuario = findViewById(R.id.NombreUsuario);
        EditText contraseña = findViewById(R.id.Contraseña);
        Button btnRegistro = findViewById(R.id.btnRegistrarse);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCambiarIP = findViewById(R.id.btnCambiarIPLogin);

        btnCambiarIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarIP();
                ApiMapSingleton.getInstance().setIP(SQLiteUtils.getIP(LoginActivity.this));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity", "IP actual al intentar login: " + ApiMapSingleton.getInstance().getIP());
                Log.d("LoginActivity", "get: " + ApiMapSingleton.getInstance().getIP());
                Log.d("LoginActivity", "IP actual al intentar login: " + ApiMapSingleton.getInstance().getUrlUsuarioLogin());
                UsuarioService.loginUsuario(getApplicationContext(),nombreUsuario.getText().toString().trim(),contraseña.getText().toString().trim(),LoginActivity.this);
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
        try {
            //SE GUARDA EL USUARIO EN LAS SHARED PREFERENCES
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String usuarioEncriptado = CryptoUtils.encriptar(CryptoUtils.transformarUsuarioToJson(usuario),"abc123.");
            editor.putString("Usuario",usuarioEncriptado);
            editor.apply();
        }catch (Exception ex){
            Log.d("ERROR",ex.getMessage());
        }

        //SE NOTIFICA AL USUARIO DE LA BIENVENIDA
        Toast.makeText(this,"Bienvenido " + usuario.getUsuario()+" !!",Toast.LENGTH_LONG).show();
        //SE PASA A LA PANTALLA DE MESAS
        Intent intentMesas = new Intent(getApplicationContext(),MesasActivity.class);
        someActivityResultLauncher.launch(intentMesas);
        finish();
    }

    @Override
    public void onError(String message) {
        //SE NOTIFICA AL USUARIO DE QUE EL USUARIO NO EXISTE
        if (message.contains("AuthFailureError")){
            Toast.makeText(this,"El usuario no existe",Toast.LENGTH_LONG).show();
        }else if (message.contains("host")){
            Toast.makeText(this,"No se encuentra el servidor",Toast.LENGTH_LONG).show();
        }else{
            Log.d("Login activity", message+"a");
            Toast.makeText(this, "error inesperado", Toast.LENGTH_SHORT).show();
        }
    }


    private void solicitarIP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Configurar IP");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        builder.setView(input);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ip = input.getText().toString();
                if(SQLiteUtils.getIP(LoginActivity.this) == null) {
                    SQLiteUtils.insertarIP(LoginActivity.this, ip);
                }else{
                    SQLiteUtils.modificarIP(LoginActivity.this, ip);
                }
                ApiMapSingleton.getInstance().setIP(ip);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}