package com.example.comandaxpress.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaptadorCategorias extends BaseAdapter {
    private Context context;
    private List<Categoria> categorias;

    public AdaptadorCategorias(Context context, List<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        }
        TextView nombre = convertView.findViewById(R.id.textoNombre);
        TextView descripcion = convertView.findViewById(R.id.textoDescripcion);
        Categoria categoria = categorias.get(position);
        nombre.setText(categoria.getNombre());
        descripcion.setText(categoria.getDescripcion());
        return convertView;
    }
}
