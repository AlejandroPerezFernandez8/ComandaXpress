package com.example.comandaxpress.Pantallas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.RegistroCallback;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.MensajeUtils;
import com.example.comandaxpress.Util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity implements RegistroCallback {
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int REQUEST_TAKE_PHOTO = 101;
    EditText nombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        EdgeToEdge.enable(this);
        EditText nombre = findViewById(R.id.Nombre);
        EditText apellidos = findViewById(R.id.Apellidos);
        EditText email= findViewById(R.id.Email);
        nombreUsuario = findViewById(R.id.NombreUsuario);
        EditText contraseña = findViewById(R.id.Contraseña);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfil);
        Button btnFoto = findViewById(R.id.btnSacarFoto);
        Button btnRegistro = findViewById(R.id.btnRegistrarse);
        List<EditText> editTexts = new ArrayList<>();

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    // Si no, solicitar el permiso de la cámara
                    ActivityCompat.requestPermissions(RegistroActivity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
            }
        });




        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTexts.add(nombre);
                editTexts.add(apellidos);
                editTexts.add(email);
                editTexts.add(nombreUsuario);
                editTexts.add(contraseña);
                // Comprobar campos vacíos
                if (comprobarVacio(editTexts)) {return;}
                // Validar correo electrónico
                if (!email.getText().toString().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    email.setError(RegistroActivity.this.getString(R.string.errorCorreo));
                    MensajeUtils.mostrarError(RegistroActivity.this, R.string.errorCorreo);
                    return;
                }
                Usuario usuario = new Usuario(
                    nombre.getText().toString().trim(),
                    apellidos.getText().toString().trim(),
                    email.getText().toString().trim(),
                    nombreUsuario.getText().toString().trim(),
                    contraseña.getText().toString().trim(),
                    ImageUtils.encodeImageViewToBase64(fotoPerfil)
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
        MensajeUtils.mostrarMensaje(RegistroActivity.this,R.string.UsuarioRegistrado);
        //Se pasa a la pantalla de login
        Intent intentLogin = new Intent(RegistroActivity.this,LoginActivity.class);
        intentLogin.putExtra("NombreUsuario",nombreUsuario.getText().toString().trim());
        setResult(RESULT_OK,intentLogin);
        finish();
    }
    @Override
    public void onRegistroFailed(String error) {
        Log.e("RegistroError",error);
        if(error.contains("El usuario ya existe")) {
            nombreUsuario.setError(RegistroActivity.this.getString(R.string.errorUsuarioDuplicado));
            MensajeUtils.mostrarError(RegistroActivity.this,R.string.errorUsuarioDuplicado);
        }else{
            MensajeUtils.mostrarError(RegistroActivity.this,R.string.errorRegistroUsuario);
        }
    }
    public boolean comprobarVacio(List<EditText> campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.setError(RegistroActivity.this.getString((R.string.errorCamposVacios)));
                return true;
            }
        }
        return false;
    }

    ActivityResultLauncher someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Obtener la miniatura
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ImageView imageView = findViewById(R.id.fotoPerfil);
                        imageView.setImageBitmap(imageBitmap);
                    } else {
                        MensajeUtils.mostrarError(RegistroActivity.this,R.string.errorFotoPerfil);
                    }
                }
            });
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Iniciar la cámara después de que el permiso ha sido concedido
                openCamera();
            } else {
                MensajeUtils.mostrarError(RegistroActivity.this,R.string.errorPermisoCamaraDenegado);}
        }
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(intent);
    }
}