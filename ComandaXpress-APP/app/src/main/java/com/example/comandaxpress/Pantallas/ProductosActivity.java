package com.example.comandaxpress.Pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

import com.example.comandaxpress.API.Clases.Producto;
import com.example.comandaxpress.API.Interfaces.GetProductosCallback;
import com.example.comandaxpress.API.ProductosService;
import com.example.comandaxpress.Adapters.ProductoAdapter;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.example.comandaxpress.R;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity implements GetProductosCallback {
    List<Producto> productos;
    ProductoAdapter productoAdapter;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_productos);
        lista = findViewById(R.id.listaProductos);
        Button btnAñadir = findViewById(R.id.btnAgregar);

        try {
            int idCategoria = this.getIntent().getExtras().getInt("categoriaId");
            ProductosService.getProductosPorCategoria(ProductosActivity.this,Long.valueOf(idCategoria),ProductosActivity.this);
        }catch (Exception ex){
            Toast.makeText(this, "Error al cargar los productos", Toast.LENGTH_SHORT).show();
        }

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTicket = new Intent();
                List<ProductoCantidad> seleccionados = productoAdapter.getProductosSeleccionados();
                intentTicket.putExtra("productosSeleccionados", new Gson().toJson(seleccionados));
                setResult(RESULT_OK, intentTicket);
                finish();
            }
        });


    }

    @Override
    public void onSuccess(List<Producto> productos) {
        this.productos = productos;
        productoAdapter = new ProductoAdapter(ProductosActivity.this,productos);
        lista.setAdapter(productoAdapter);
    }

    @Override
    public void onError(String error) {
        Log.d("Producto Error",error);
    }
}