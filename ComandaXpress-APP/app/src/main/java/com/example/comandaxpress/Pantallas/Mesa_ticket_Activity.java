package com.example.comandaxpress.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import com.example.comandaxpress.API.CategoriaService;
import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.API.Clases.Usuario;
import com.example.comandaxpress.API.Interfaces.GetAllCategoriasCallback;
import com.example.comandaxpress.API.Interfaces.GetProductoCantidadCallback;
import com.example.comandaxpress.API.Interfaces.ModificacionMesaCallback;
import com.example.comandaxpress.API.MesaService;
import com.example.comandaxpress.API.TicketService;
import com.example.comandaxpress.Adapters.TicketProductoAdapter;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.example.comandaxpress.Pantallas.Fragment.DialogoCategoriasFragment;
import com.example.comandaxpress.R;
import com.example.comandaxpress.Util.CryptoUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Mesa_ticket_Activity extends AppCompatActivity implements GetAllCategoriasCallback, GetProductoCantidadCallback,DialogoCategoriasFragment.CategoriaSeleccionadaListener {
    SharedPreferences sharedPreferences;
    ArrayList<ProductoCantidad> pcList = new ArrayList<>();
    TicketProductoAdapter adapter;
    Mesa mesa;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa_ticket);
        EdgeToEdge.enable(this);
        sharedPreferences = getApplicationContext().getSharedPreferences("preferencias", MODE_PRIVATE);
        TextView numeroMesa = findViewById(R.id.tituloMesaTicket);
        TextView numeroComensales = findViewById(R.id.numComensales);
        ImageView fotoperfil = findViewById(R.id.fotoPerfilMesaTicket);
        ListView lista = findViewById(R.id.listaProductosAgregados);
        Button btnAñadirProducto = findViewById(R.id.btnAñadirProducto);
        Button btncobrarMesa = findViewById(R.id.btnCobrarMesa);
        tvTotal = findViewById(R.id.tvTotal);
        adapter = new TicketProductoAdapter(Mesa_ticket_Activity.this, pcList);
        lista.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        try {
            mesa = new Gson().fromJson(sharedPreferences.getString("Mesa", ""), Mesa.class);
            numeroMesa.setText("Mesa Número " + mesa.getNumero());
            numeroComensales.setText("Comensales: " + mesa.getCapacidad());
            if(!mesa.getTickets().isEmpty()){
                TicketService.getDetallesDeTicket(Mesa_ticket_Activity.this,mesa.getTickets().get(mesa.getTickets().size()-1).longValue(),Mesa_ticket_Activity.this);
            }
        } catch (Exception ex) {
            Log.d("Mesa_Ticket_Error",ex.getMessage());
        }

        btnAñadirProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriaService.getAllCategorias(Mesa_ticket_Activity.this, Mesa_ticket_Activity.this);
            }
        });

        try {
            Usuario usuario;
            String encriptedUser = sharedPreferences.getString("Usuario", "");
            String jsonUsuario = CryptoUtils.desencriptar(encriptedUser, "abc123.");
            usuario = CryptoUtils.transformarJsonToUsuaro(jsonUsuario);
            if (!usuario.getFoto().isEmpty() || usuario.getFoto() == null) {
                fotoperfil.setImageBitmap(CryptoUtils.decodeBase64ToBitmap(usuario.getFoto()));
            }
        } catch (Exception ex) {
        }

        fotoperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjustes = new Intent(getApplicationContext(), AjustesActivity.class);
                someActivityResultLauncher.launch(intentAjustes);
            }
        });

        btncobrarMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesa.setActiva(false);
                MesaService.updateMesa(Mesa_ticket_Activity.this, mesa, new ModificacionMesaCallback() {
                    @Override
                    public void onModificacionSuccess(String response) {
                        Log.d("Mesa_Ticket_activity_Response","Mesa cerrada"+mesa.getMesaId());
                        sharedPreferences.edit().remove("Mesa").apply();
                        finish();
                    }

                    @Override
                    public void onModificacionFailed(String errorMessage) {
                        Toast.makeText(Mesa_ticket_Activity.this, "Error en el cierre de la mesa", Toast.LENGTH_SHORT).show();
                        Log.d("Mesa_Ticket_activity_Response","Error en el cierre de mesa : "+errorMessage);
                    }
                });
            }
        });
        actualizarTotal();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String json = result.getData().getStringExtra("productosSeleccionados");
                        if (json != null) {
                            Type tipoLista = new TypeToken<List<ProductoCantidad>>() {
                            }.getType();
                            List<ProductoCantidad> listaProductosCantidad = new Gson().fromJson(json, tipoLista);
                            añadirProductos(listaProductosCantidad);
                        }
                    } else {
                        Log.d("ERROR", "RESULT CODE MAL o Intent vacío");
                    }
                }
            });

    public void añadirProducto(ProductoCantidad productoCantidad) {
        pcList.add(productoCantidad);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(List<Categoria> categorias) {
        DialogoCategoriasFragment dialogo = DialogoCategoriasFragment.newInstance(new ArrayList<>(categorias));
        dialogo.setCategoriaSeleccionadaListener(this);
        dialogo.show(getSupportFragmentManager(), "dialogo_categorias");
    }

    @Override
    public void onError(String error) {
        Log.d("Error carga categorias", error);
        Toast.makeText(this, "Error al cargar categorias", Toast.LENGTH_SHORT).show();
    }

    public void añadirProductos(List<ProductoCantidad> nuevosProductos) {
        for (ProductoCantidad nuevoProducto : nuevosProductos) {
            boolean encontrado = false;
            for (ProductoCantidad productoEnLista : pcList) {
                if (productoEnLista.getProducto().getProducto_id().equals(nuevoProducto.getProducto().getProducto_id())) {
                    int cantidadActualizada = productoEnLista.getCantidad() + nuevoProducto.getCantidad();
                    productoEnLista.setCantidad(cantidadActualizada);
                    encontrado = true;
                    Log.d("Actualizado 1", cantidadActualizada + "");
                    break;
                }
            }
            if (!encontrado) {
                pcList.add(nuevoProducto);
                Log.d("Nuevo", nuevoProducto.toString());
            }
        }
        adapter.notifyDataSetChanged();
        actualizarTotal();
    }
    @Override
    public void onCategoriaSeleccionada(Categoria categoria) {
        Intent intent = new Intent(Mesa_ticket_Activity.this, ProductosActivity.class);
        intent.putExtra("categoriaId", Integer.parseInt(categoria.getCategoriaId().toString()));
        someActivityResultLauncher.launch(intent);
    }

    @Override
    public void onGetProductosSuccess(List<ProductoCantidad> productoCantidadList) {
        try {
            adapter.addAll(new ArrayList<>(productoCantidadList));
            adapter.notifyDataSetChanged();
            actualizarTotal();
        }catch (Exception ex){
            Log.d("ErrorCARGA",ex.getMessage());
        }
    }

    @Override
    public void onGetProductosError(String error) {
        Log.d("GetProductosError",error);
        Toast.makeText(this, "Error al recuperar los detalles del ticket", Toast.LENGTH_SHORT).show();
    }

    private void actualizarTotal(){
        BigDecimal total = BigDecimal.ZERO;
        if(!adapter.getProductoCantidadList().isEmpty()){
            for (ProductoCantidad pc : adapter.getProductoCantidadList()) {
                BigDecimal precio = pc.getProducto().getPrecio();
                BigDecimal cantidad = BigDecimal.valueOf(pc.getCantidad());
                total = total.add(precio.multiply(cantidad));
            }
            tvTotal.setText("Total : "+total.toString() + "€");
        }
    }

}