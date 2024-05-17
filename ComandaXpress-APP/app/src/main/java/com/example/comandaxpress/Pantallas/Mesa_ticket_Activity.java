package com.example.comandaxpress.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import com.example.comandaxpress.API.CategoriaService;
import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.GetAllCategoriasCallback;
import com.example.comandaxpress.Adapters.TicketProductoAdapter;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.example.comandaxpress.Pantallas.Fragment.DialogoCategoriasFragment;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mesa_ticket_Activity extends AppCompatActivity implements GetAllCategoriasCallback{
    SharedPreferences sharedPreferences;
    ArrayList<ProductoCantidad> pcList = new ArrayList<>();
    TicketProductoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa_ticket);
        EdgeToEdge.enable(this);
        sharedPreferences= getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        TextView numeroMesa = findViewById(R.id.tituloMesaTicket);
        TextView numeroComensales = findViewById(R.id.numComensales);
        ImageView fotoperfil = findViewById(R.id.fotoPerfilMesaTicket);
        ListView lista = findViewById(R.id.listaProductosAgregados);
        Button btnAñadirProducto = findViewById(R.id.btnAñadirProducto);

        adapter = new TicketProductoAdapter(Mesa_ticket_Activity.this,pcList);
        lista.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Mesa mesa = new Gson().fromJson(getIntent().getExtras().getString("Mesa"), Mesa.class);
        numeroMesa.setText("Mesa Número "+mesa.getNumero());
        numeroComensales.setText("Comensales: "+mesa.getCapacidad());

        btnAñadirProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriaService.getAllCategorias(Mesa_ticket_Activity.this,Mesa_ticket_Activity.this);
            }
        });

        try {
            Usuario usuario;
            String encriptedUser = sharedPreferences.getString("Usuario","");
            String jsonUsuario = CryptoUtils.desencriptar(encriptedUser,"abc123.");
            usuario = CryptoUtils.transformarJsonToUsuaro(jsonUsuario);
            if(!usuario.getFoto().isEmpty() || usuario.getFoto() == null){
                fotoperfil.setImageBitmap(CryptoUtils.decodeBase64ToBitmap(usuario.getFoto()));
            }
        }catch (Exception ex){}

        fotoperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjustes = new Intent(getApplicationContext(),AjustesActivity.class);
                someActivityResultLauncher.launch(intentAjustes);
            }
        });
    }
    ActivityResultLauncher someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    recreate();
                }
            });

    public void añadirProducto(ProductoCantidad productoCantidad){
        pcList.add(productoCantidad);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(List<Categoria> categorias) {
        DialogoCategoriasFragment dialogo = DialogoCategoriasFragment.newInstance(new ArrayList<>(categorias));
        dialogo.show(getSupportFragmentManager(), "dialogo_categorias");
    }

    @Override
    public void onError(String error) {
        Log.d("Error carga categorias",error);
        Toast.makeText(this, "Error al cargar categorias", Toast.LENGTH_SHORT).show();
    }
}