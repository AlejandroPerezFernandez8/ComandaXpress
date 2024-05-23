package com.example.comandaxpress.Pantallas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.comandaxpress.API.ApiMapSingleton;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.ModificacionCallback;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.MensajeUtils;
import com.example.comandaxpress.Util.ImageUtils;
import com.example.comandaxpress.Util.LocaleUtil;
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
    ImageView imagenBandera;
    boolean isCastellano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Cargar el idioma persistido
        LocaleUtil.loadLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        sharedPreferences = getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
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

        // Configurar el idioma y la bandera inicial
        imagenBandera = findViewById(R.id.btnCambiarIdioma);
        isCastellano = LocaleUtil.getCurrentLocale(this).equals("es");
        imagenBandera.setImageResource(isCastellano ? R.drawable.bandera_esp : R.drawable.bandera_gl);

        imagenBandera.setOnClickListener(v -> animacionFlip());

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

        emailModificar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                int color = getResources().getColor(R.color.colorLightBlue);
                emailModificar.setTextColor(color);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // COMPROBAR LOS CAMPOS VACIOS
                campos.add(nombreModificar);
                campos.add(apellidosModificar);
                campos.add(emailModificar);
                campos.add(nombreUsuarioModificar);
                campos.add(contraseñaModificar);

                boolean isEmailValid = emailModificar.getText().toString().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
                boolean areFieldsEmpty = comprobarVacio(campos);
                if (!isEmailValid || areFieldsEmpty) {
                    if (!isEmailValid) {
                        emailModificar.setError(AjustesActivity.this.getString(R.string.errorCorreo));
                        MensajeUtils.mostrarMensaje(AjustesActivity.this, R.string.errorCorreo);
                    }
                    if (areFieldsEmpty) {
                        MensajeUtils.mostrarMensaje(AjustesActivity.this, R.string.errorCamposVacios);
                    }
                    return;
                }
                // HACER LA PETICION (PASANDOLE EL ID DEL USUARIO ACTUAL)
                    String usuarioJson = null;
                    try {
                        Gson gson = new Gson();
                        usuarioJson = CryptoUtils.desencriptar(sharedPreferences.getString("Usuario", ""), "abc123.");
                        usuario = gson.fromJson(usuarioJson, Usuario.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // GUARDAR LOS DATOS DEL NUEVO USUARIO
                    usuario.setNombre(nombreModificar.getText().toString().trim());
                    usuario.setApellido(apellidosModificar.getText().toString().trim());
                    usuario.setEmail(emailModificar.getText().toString().trim());
                    usuario.setUsuario(nombreUsuarioModificar.getText().toString().trim());
                    usuario.setContraseña(contraseñaModificar.getText().toString().trim());
                    usuario.setFoto(ImageUtils.encodeImageViewToBase64(fotoPerfilModificar));
                    // OBTENER EL USUARIO CON EL ID
                    usuario_id = usuario.getUsuario_id();
                    // HACER LA MODIFICACION
                    UsuarioService.modificarUsuario(AjustesActivity.this, usuario_id, usuario, AjustesActivity.this);
                }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        Usuario usuario;
        try {
            usuario = CryptoUtils.transformarJsonToUsuaro(
                    CryptoUtils.desencriptar(sharedPreferences.getString("Usuario", ""), "abc123.")
            );
            try {
                fotoPerfil.setImageBitmap(CryptoUtils.decodeBase64ToBitmap(usuario.getFoto()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            nombreUsuario.setText(usuario.getNombre() + " " + usuario.getApellido());
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnCambiarIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = etCambiarIP.getText().toString();
                if (ip != null && !ip.isEmpty()) {
                    try {
                        if (SQLiteUtils.getIP(AjustesActivity.this) != null) {
                            SQLiteUtils.modificarIP(AjustesActivity.this, ip);
                        } else {
                            SQLiteUtils.insertarIP(AjustesActivity.this, ip);
                        }
                        ApiMapSingleton.getInstance().setIP(SQLiteUtils.getIP(AjustesActivity.this));
                        Toast.makeText(AjustesActivity.this, "IP cambiada", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        MensajeUtils.mostrarMensaje(AjustesActivity.this,R.string.errorCambioIP);
                        Log.d("Error IP", ex.getMessage());
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
                    // ELIMINAR EL USER DE LAS SHARED PREFERENCES;
                    sharedPreferences = getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    // LANZAR LA ACTIVIDAD DE LOGIN
                    Intent intentLogin = new Intent(AjustesActivity.this, LoginActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    someActivityResultLauncher.launch(intentLogin);
                    dialog.dismiss();
                });
                btnCancel.setOnClickListener(b -> {
                    dialog.dismiss();
                });
                dialog.show();
            }
        });
    }

    ActivityResultLauncher someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {}
            });

    ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        if (imageBitmap != null) {
                            fotoPerfilModificar.setImageBitmap(imageBitmap);
                        }
                    }
                }
            });

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        }
    }

    @Override
    public void onRegistroSuccess(String response) {
        try {
            // GUARDAR EL USUARIO ENCRIPTADO EN LAS PREFERENCIAS
            sharedPreferences.edit().putString("Usuario", CryptoUtils.encriptar(new Gson().toJson(usuario), "abc123.")).apply();
            Toast.makeText(this, "Usuario Modificado", Toast.LENGTH_SHORT).show();
            // VOLVER AL LOGIN
            Intent intentLogin = new Intent(AjustesActivity.this, LoginActivity.class);
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            someActivityResultLauncher.launch(intentLogin);
        } catch (Exception ex) {
            MensajeUtils.mostrarMensaje(AjustesActivity.this,R.string.errorModificacion);
            Log.d("Modificacion Error", ex.getMessage());
        }
    }

    @Override
    public void onRegistroFailed(String error) {
        if (error.contains("Usuario duplicado")) {
            MensajeUtils.mostrarMensaje(AjustesActivity.this,R.string.errorUsuarioDuplicado);
        } else {
            MensajeUtils.mostrarMensaje(AjustesActivity.this,R.string.errorModificacion);
            Log.d("Modificacion Error", error);
        }
    }

    public boolean comprobarVacio(List<EditText> campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.setError(AjustesActivity.this.getString((R.string.errorCamposVacios)));
                return true;
            }
        }
        return false;
    }

    private void animacionFlip() {
        Animation flipStart = AnimationUtils.loadAnimation(this, R.anim.flip_start);
        Animation flipEnd = AnimationUtils.loadAnimation(this, R.anim.flip_end);

        // Persistir el nuevo idioma antes de recrear la actividad
        flipStart.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LocaleUtil.persistLanguage(AjustesActivity.this, isCastellano ? "gl" : "es");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Cambiar la imagen de la bandera después de la primera mitad de la animación
                imagenBandera.setImageResource(isCastellano ? R.drawable.bandera_gl : R.drawable.bandera_esp);

                // Iniciar la segunda mitad de la animación
                imagenBandera.startAnimation(flipEnd);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        flipEnd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Cambiar el estado de isCastellano después de la animación
                isCastellano = !isCastellano;
                // Recrear la actividad para aplicar los cambios de idioma
                recreate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Iniciar la primera mitad de la animación
        imagenBandera.startAnimation(flipStart);
    }

}
