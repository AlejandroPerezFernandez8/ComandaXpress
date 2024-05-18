package com.example.comandaxpress.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.comandaxpress.API.Clases.Producto;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.example.comandaxpress.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoAdapter extends ArrayAdapter<Producto> {
    private LayoutInflater inflater;
    private List<Producto> productos;
    private HashMap<Long, Integer> cantidades;

    public ProductoAdapter(Context context, List<Producto> productos) {
        super(context, 0, productos);
        this.inflater = LayoutInflater.from(context);
        this.productos = productos;
        this.cantidades = new HashMap<>();
        for (Producto producto : productos) {
            cantidades.put(producto.getProducto_id(), 0);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_producto, parent, false);
            holder = new ViewHolder();
            holder.nombre = convertView.findViewById(R.id.tvNombreProducto);
            holder.precio = convertView.findViewById(R.id.tvPrecioProducto);
            holder.cantidad = convertView.findViewById(R.id.tvCantidad);
            holder.btnMenos = convertView.findViewById(R.id.btnMenos);
            holder.btnMas = convertView.findViewById(R.id.btnMas);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Producto producto = getItem(position);
        Log.d("Producto",producto.toString());
        holder.nombre.setText(producto.getNombre());
        holder.precio.setText("Precio: " + producto.getPrecio().toString()+"€");

        actualizarCantidad(holder, producto.getProducto_id());

        holder.btnMas.setOnClickListener(v -> {
            int count = cantidades.getOrDefault(producto.getProducto_id(), 0) + 1;
            cantidades.put(producto.getProducto_id(), count);
            actualizarCantidad(holder, producto.getProducto_id());
            imprimirCantidades();
        });

        holder.btnMenos.setOnClickListener(v -> {
            int count = cantidades.getOrDefault(producto.getProducto_id(), 0);
            if (count > 0) {
                count--;
                cantidades.put(producto.getProducto_id(), count);
                actualizarCantidad(holder, producto.getProducto_id());
                imprimirCantidades();
            }
        });

        return convertView;
    }

    private void actualizarCantidad(ViewHolder holder, Long productId) {
        int quantity = cantidades.getOrDefault(productId, 0);
        holder.cantidad.setText(String.valueOf(quantity));
    }

    static class ViewHolder {
        TextView nombre;
        TextView precio;
        TextView cantidad;
        Button btnMenos;
        Button btnMas;
    }


    private void imprimirCantidades() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Long, Integer> entry : cantidades.entrySet()) {
            builder.append("Producto ID: ").append(entry.getKey())
                    .append(", Cantidad: ").append(entry.getValue()).append("\n");
        }
        Log.d("ProductoCantidades", builder.toString());
    }

    public List<ProductoCantidad> getProductosSeleccionados() {
        List<ProductoCantidad> seleccionados = new ArrayList<>();
        for (Producto producto : productos) {
            int cantidad = cantidades.getOrDefault(producto.getProducto_id(), 0);
            if (cantidad > 0) {
                seleccionados.add(new ProductoCantidad(producto, cantidad));
            }
        }
        return seleccionados;
    }

}