package com.example.comandaxpress.Pantallas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.comandaxpress.API.ApiMapSingleton;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.ModificacionCallback;
import com.example.comandaxpress.API.UsuarioService;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.BotonUtils;
import com.example.comandaxpress.Util.CryptoUtils;
import com.example.comandaxpress.Util.MensajeUtils;
import com.example.comandaxpress.Util.ImageUtils;
import com.example.comandaxpress.Util.LocaleUtil;
import com.example.comandaxpress.Util.SQLiteUtils;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Pantalla de ajustes
 * */
public class AjustesActivity extends AppCompatActivity implements ModificacionCallback {
    SharedPreferences sharedPreferences;
    private long usuario_id;
    private Usuario usuario = new Usuario();
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int REQUEST_TAKE_PHOTO = 101;
    ImageView fotoPerfilModificar;
    ImageView imagenBandera;
    boolean isCastellano;
    EditText nombreUsuarioModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Creacion de variables e instancias de componentes
         * */
        // Cargar el idioma persistido
        LocaleUtil.loadLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        EdgeToEdge.enable(this);
        sharedPreferences = getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfilAjustes);
        TextView nombreUsuario = findViewById(R.id.nombreUsuarioAjustes);
        EditText etCambiarIP = findViewById(R.id.etCambioIP);
        Button btnCambiarIP = findViewById(R.id.btnCambiarIP);
        ImageView logout = findViewById(R.id.imagen_logout);
        EditText nombreModificar = findViewById(R.id.NombreModificar);
        EditText apellidosModificar = findViewById(R.id.ApellidosModificar);
        EditText emailModificar = findViewById(R.id.EmailModificar);
        nombreUsuarioModificar = findViewById(R.id.NombreUsuarioModificar);
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



        /**
         * Obtención del usuario encriptado para mostrar sus datos
         * */
        String usuarioJson = null;
        try {
            Gson gson = new Gson();
            usuarioJson = CryptoUtils.desencriptar(sharedPreferences.getString("Usuario", ""), "abc123.");
            usuario = gson.fromJson(usuarioJson, Usuario.class);

            nombreModificar.setText(usuario.getNombre());
            apellidosModificar.setText(usuario.getApellido());
            emailModificar.setText(usuario.getEmail());
            nombreUsuarioModificar.setText(usuario.getUsuario());
            contraseñaModificar.setText(usuario.getContraseña());
            if(ImageUtils.decodeBase64ToBitmap(usuario.getFoto()) != null){
                fotoPerfilModificar.setImageBitmap(ImageUtils.decodeBase64ToBitmap(usuario.getFoto()));
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Metodo para sacar pedir permisos y abrir el intent de sacar la foto
         * */
        btnSacarFotoModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotonUtils.deshabilitarTemporalmente(btnSacarFotoModificar);
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

        /**
         * Logica de modificación del usuario
         * */
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotonUtils.deshabilitarTemporalmente(btnRegistrarse);
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
                        MensajeUtils.mostrarError(AjustesActivity.this, R.string.errorCorreo);
                    }
                    if (areFieldsEmpty) {
                        MensajeUtils.mostrarError(AjustesActivity.this, R.string.errorCamposVacios);
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
        //SE GUARDA EL NUEVO USR
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

        /**
         * Logica para cambiar la ip del servidor
         * */
        btnCambiarIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotonUtils.deshabilitarTemporalmente(btnCambiarIP);
                String ip = etCambiarIP.getText().toString();
                if (ip != null && !ip.isEmpty()) {
                    try {
                        if (SQLiteUtils.getIP(AjustesActivity.this) != null) {
                            SQLiteUtils.modificarIP(AjustesActivity.this, ip);
                        } else {
                            SQLiteUtils.insertarIP(AjustesActivity.this, ip);
                        }
                        ApiMapSingleton.getInstance().setIP(SQLiteUtils.getIP(AjustesActivity.this));
                        MensajeUtils.mostrarMensaje(AjustesActivity.this,AjustesActivity.this.getString(R.string.CambioIP) + ": " + ApiMapSingleton.getInstance().getIP());
                    } catch (Exception ex) {
                        MensajeUtils.mostrarError(AjustesActivity.this,R.string.errorCambioIP);
                        Log.d("Error IP", ex.getMessage());
                    }
                }
            }
        });

        /**
         * Logica para hacer un logout
         * */
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

    /**
     * Launcher para la recogida de la foto de perfil
     * */
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

    /**
     * Lanzamieto del intent de la camara
     * */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureLauncher.launch(takePictureIntent);
    }

    /**
     * CallBacks de modificacion del usuario
     * */
    @Override
    public void onRegistroSuccess(String response) {
        try {
            if(response.contains("Usuario duplicado")){
                MensajeUtils.mostrarError(AjustesActivity.this,R.string.errorUsuarioDuplicado);
                nombreUsuarioModificar.setError(AjustesActivity.this.getString(R.string.errorUsuarioDuplicado));
                return;
            }
            // GUARDAR EL USUARIO ENCRIPTADO EN LAS PREFERENCIAS
            sharedPreferences.edit().putString("Usuario", CryptoUtils.encriptar(new Gson().toJson(usuario), "abc123.")).apply();
            MensajeUtils.mostrarMensaje(AjustesActivity.this,R.string.ModificacionUsuario);
            // VOLVER AL LOGIN
            Intent intentLogin = new Intent(AjustesActivity.this, LoginActivity.class);
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            someActivityResultLauncher.launch(intentLogin);
        } catch (Exception ex) {
            MensajeUtils.mostrarError(AjustesActivity.this,R.string.errorModificacion);
            Log.d("Modificacion Error", ex.getMessage());
        }
    }

    @Override
    public void onRegistroFailed(String error) {
        if (error.contains("Usuario duplicado")) {
            MensajeUtils.mostrarError(AjustesActivity.this,R.string.errorUsuarioDuplicado);
            nombreUsuarioModificar.setError(AjustesActivity.this.getString(R.string.errorUsuarioDuplicado));
        } else {
            MensajeUtils.mostrarError(AjustesActivity.this,R.string.errorModificacion);
            Log.d("Modificacion Error", error);
        }
    }

    /**
     * Metodo para comprobar que los edittext no estén vacios
     * */
    public boolean comprobarVacio(List<EditText> campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.setError(AjustesActivity.this.getString((R.string.errorCamposVacios)));
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para animar el boton de cambiar el idioma
     * */
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                MensajeUtils.mostrarError(AjustesActivity.this,R.string.errorPemisoFoto);
            }
        }
    }


    /**
     * No perder la foto cuando se gira la pantalla
     * */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            if (fotoPerfilModificar.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) fotoPerfilModificar.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                outState.putByteArray("fotoPerfil", byteArray);
            }
        }catch (Exception ex){}
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            byte[] byteArray = savedInstanceState.getByteArray("fotoPerfil");
            if (byteArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                fotoPerfilModificar.setImageBitmap(bitmap);
            }
        }catch (Exception ex){}
    }


}
