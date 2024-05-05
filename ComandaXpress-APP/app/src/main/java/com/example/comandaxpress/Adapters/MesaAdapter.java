package com.example.comandaxpress.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comandaxpress.API.Clases.Mesa;
import com.example.comandaxpress.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

public class MesaAdapter extends ArrayAdapter<Mesa> {
    private List<Mesa> mesas;

    public MesaAdapter(Context context, List<Mesa> mesas) {
        super(context, R.layout.list_item_mesa, mesas);
        this.mesas = mesas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("MesaAdapter", "Getting view for position: " + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_mesa, parent, false);
        }
        TextView mesaText = convertView.findViewById(R.id.mesaText);
        Mesa mesa = getItem(position);  // Ahora esto funcionará correctamente
        mesaText.setText("Mesa " + mesa.getNumero());
        return convertView;
    }
}
