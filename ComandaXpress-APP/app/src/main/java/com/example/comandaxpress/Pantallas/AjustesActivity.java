package com.example.comandaxpress.Pantallas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.ApiMapSingleton;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.ModificacionCallback;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.ImageUtils;
import com.example.comandaxpress.Util.SQLiteUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AjustesActivity extends AppCompatActivity implements ModificacionCallback {
    SharedPreferences sharedPreferences;
    private long usuario_id;
    private Usuario usuario = new Usuario();
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int REQUEST_TAKE_PHOTO = 101;
    ImageView fotoPerfilModificar;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfilAjustes);
        TextView nombreUsuario = findViewById(R.id.nombreUsuarioAjustes);
        EditText etCambiarIP = findViewById(R.id.etCambioIP);
        Button btnCambiarIP = findViewById(R.id.btnCambiarIP);
        ImageView logout = findViewById(R.id.imagen_logout);

        EditText nombreModificar = findViewById(R.id.NombreModificar);
        EditText apellidosModificar = findViewById(R.id.ApellidosModificar);
        EditText emailModificar = findViewById(R.id.EmailModificar);
        EditText nombreUsuarioModificar = findViewById(R.id.NombreUsuarioModificar);
        EditText contraseñaModificar = findViewById(R.id.ContraseñaModificar);
        fotoPerfilModificar = findViewById(R.id.fotoPerfilModificar);
        Button btnSacarFotoModificar = findViewById(R.id.btnSacarFotoModificar);
        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);
        List<EditText> campos = new ArrayList<>();

        btnSacarFotoModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    // Si no, solicitar el permiso de la cámara
                    ActivityCompat.requestPermissions(AjustesActivity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //COMPROBAR LOS CAMPOS VACIOS
                campos.add(nombreModificar);
                campos.add(apellidosModificar);
                campos.add(emailModificar);
                campos.add(nombreUsuarioModificar);
                campos.add(contraseñaModificar);
                comprobarVacio(campos);
                //HACER LA PETICION (PASANDOLE EL ID DEL USUARIO ACTUAL)
                String usuarioJson = null;
                try {
                    Gson gson = new Gson();
                    usuarioJson = CryptoUtils.desencriptar(sharedPreferences.getString("Usuario", ""), "abc123.");
                    usuario = gson.fromJson(usuarioJson, Usuario.class);
                } catch (Exception e) {
                }
                //GUARDAR LOS DATOS DEL NUEVO USUARIO
                usuario.setNombre(nombreModificar.getText().toString().trim());
                usuario.setApellido(apellidosModificar.getText().toString().trim());
                usuario.setEmail(emailModificar.getText().toString().trim());
                usuario.setUsuario(nombreUsuarioModificar.getText().toString().trim());
                usuario.setContraseña(contraseñaModificar.getText().toString().trim());
                usuario.setFoto(ImageUtils.encodeImageViewToBase64(fotoPerfilModificar));
                //OBTENER EL USUARIO CON EL ID
                usuario_id = usuario.getUsuario_id();
                //HACER LA MODIFICACION
                UsuarioService.modificarUsuario(AjustesActivity.this,usuario_id,usuario,AjustesActivity.this);
            }
        });


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
            try {
                fotoPerfil.setImageBitmap(CryptoUtils.decodeBase64ToBitmap(usuario.getFoto()));
            }catch (Exception exception){}
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AjustesActivity.this);
                LayoutInflater inflater = AjustesActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.logout, null);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                btnConfirm.setOnClickListener(b -> {
                    //ELIMINAR EL USER DE LAS SHARED PREFERENCES;
                    sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    //LANZAR LA ACTIVIDAD DE LOGIN
                    Intent intentLogin = new Intent(AjustesActivity.this,LoginActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    someActivityResultLauncher.launch(intentLogin);
                    dialog.dismiss();
                });
                btnCancel.setOnClickListener(b -> {dialog.dismiss();});
                dialog.show();
            }
        });
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
                        fotoPerfilModificar.setImageBitmap(imageBitmap);
                    } else {
                        Toast.makeText(AjustesActivity.this, "Foto no capturada", Toast.LENGTH_SHORT).show();
                    }
                }
            });

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

    @Override
    public void onRegistroSuccess(String response) {
        try {
            //GUARDAR EL USUARIO ENCRIPTADO EN LAS PREFERENCIAS
            sharedPreferences.edit().putString("Usuario", CryptoUtils.encriptar(new Gson().toJson(usuario), "abc123."));
            Toast.makeText(this, "Usuario Modificado", Toast.LENGTH_SHORT).show();
            //VOLVER AL LOGIN
            Intent intentLogin = new Intent(AjustesActivity.this,LoginActivity.class);
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            someActivityResultLauncher.launch(intentLogin);
        }catch (Exception ex){
            Toast.makeText(this, "Error durante la modificacion", Toast.LENGTH_SHORT).show();
            Log.d("Modificacion Error",ex.getMessage());
        }
    }

    @Override
    public void onRegistroFailed(String error) {
        Toast.makeText(this, "Error al modificar usuario", Toast.LENGTH_SHORT).show();
        Log.d("Modificacion Error",error);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(intent);
    }

}