package com.example.comandaxpress.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.ClasesHelper.ProductoCantidad;
import com.example.comandaxpress.R;

import java.util.List;

public class TicketProductoAdapter extends ArrayAdapter<ProductoCantidad> {
    private List<ProductoCantidad> productoCantidadList;

    public TicketProductoAdapter(Context context, List<ProductoCantidad> productoCantidadList) {
        super(context, R.layout.list_item_producto_cantidad, productoCantidadList);
        this.productoCantidadList = productoCantidadList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ProductoCantidad", "Getting view for position: " + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_producto_cantidad, parent, false);
        }
        TextView nombreProducto = convertView.findViewById(R.id.textNombre);
        TextView cantidadProducto = convertView.findViewById(R.id.textCantidad);
        ProductoCantidad producto = getItem(position);

        nombreProducto.setText(producto.getProducto().getNombre());
        cantidadProducto.setText(String.valueOf(producto.getCantidad())+"x");

        return convertView;
    }
}
