package com.example.comandaxpress.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.R;

import java.util.List;

public class AdaptadorCategorias extends BaseAdapter {
    private Context contexto;
    private List<Categoria> categorias;
    private int alturaMaxima = 0;
    private LayoutInflater inflater;

    public AdaptadorCategorias(Context contexto, List<Categoria> categorias) {
        this.contexto = contexto;
        this.categorias = categorias;
        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int posicion) {
        return categorias.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_categoria, parent, false);
            holder = new ViewHolder();
            holder.nombre = convertView.findViewById(R.id.textoNombre);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Categoria categoria = categorias.get(posicion);
        holder.nombre.setText(categoria.getNombre());
        final View convert = convertView;

        convertView.post(() -> {
            if (convert.getHeight() > alturaMaxima) {
                alturaMaxima = convert.getHeight();
                notifyDataSetChanged();
            }
        });

        if (alturaMaxima > 0) {
            convertView.getLayoutParams().height = alturaMaxima;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView nombre;
    }
}
