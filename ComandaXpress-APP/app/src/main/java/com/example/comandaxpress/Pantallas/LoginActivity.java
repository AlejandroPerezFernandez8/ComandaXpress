package com.example.comandaxpress.Pantallas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comandaxpress.API.ApiMapSingleton;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.LoginCallBack;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.Pantallas.PantallasSecundarias.DialogoDeCarga;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.MensajeUtils;
import com.example.comandaxpress.Util.LocaleUtil;
import com.example.comandaxpress.Util.SQLiteUtils;

public class LoginActivity extends AppCompatActivity implements LoginCallBack {
    SharedPreferences sharedPreferences;
    DialogoDeCarga dialogoDeCarga = new DialogoDeCarga(this);
    EditText nombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleUtil.loadLocale(LoginActivity.this);
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
        nombreUsuario = findViewById(R.id.NombreUsuario);
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
                dialogoDeCarga.startLoadingDialog();
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
                    if(result.getResultCode() == RESULT_OK){
                        try {
                            nombreUsuario.setText(result.getData().getExtras().getString("NombreUsuario").trim());
                        }catch (Exception ex){}
                    }
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
        //SE PASA A LA PANTALLA DE MESAS
        dialogoDeCarga.dismissDialog();
        Intent intentMesas = new Intent(getApplicationContext(),MesasActivity.class);
        someActivityResultLauncher.launch(intentMesas);
        finish();
    }

    @Override
    public void onError(String message) {
        dialogoDeCarga.dismissDialog();
        //SE NOTIFICA AL USUARIO DE QUE EL USUARIO NO EXISTE
        Log.e("Login error",message);
        if (message.contains("AuthFailureError")){
            MensajeUtils.mostrarError(this,R.string.errorUsuarioNoExiste);
        }else if (message.contains("host")){
            MensajeUtils.mostrarError(this,R.string.errorServidor);
        }else{
            Log.d("Login activity", message+"a");
            MensajeUtils.mostrarError(this,R.string.errorInesperado);
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
                MensajeUtils.mostrarMensaje(LoginActivity.this,R.string.CambioIP + ": " + ApiMapSingleton.getInstance().getIP());
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