package com.example.comandaxpress.Pantallas;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.API.Clases.Producto;
import com.example.comandaxpress.API.Interfaces.GetProductosCallback;
import com.example.comandaxpress.API.ProductosService;
import com.example.comandaxpress.Adapters.ProductoAdapter;
import com.example.comandaxpress.R;

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

        try {
            int idCategoria = this.getIntent().getExtras().getInt("categoriaId");
            ProductosService.getProductosPorCategoria(ProductosActivity.this,Long.valueOf(idCategoria),ProductosActivity.this);
        }catch (Exception ex){
            Toast.makeText(this, "Error al cargar los productos", Toast.LENGTH_SHORT).show();
        }

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