package com.example.comandaxpress.Pantallas.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.Adapters.AdaptadorCategorias;
import com.example.comandaxpress.Pantallas.Mesa_ticket_Activity;
import com.example.comandaxpress.Pantallas.ProductosActivity;
import com.example.comandaxpress.R;

import java.util.ArrayList;
import java.util.List;

public class DialogoCategoriasFragment extends DialogFragment {
    private static final String ARG_CATEGORIAS = "categorias";

    public static DialogoCategoriasFragment newInstance(ArrayList<Categoria> categorias) {
        DialogoCategoriasFragment fragment = new DialogoCategoriasFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORIAS, categorias);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogo_categorias, container, false);
        GridView gridView = view.findViewById(R.id.gridCategorias);
        if (getArguments() != null) {
            ArrayList<Categoria> categorias = (ArrayList<Categoria>) getArguments().getSerializable(ARG_CATEGORIAS);
            AdaptadorCategorias adapter = new AdaptadorCategorias(getContext(), categorias);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Categoria categoriaSeleccionada = (Categoria) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), ProductosActivity.class);
                    intent.putExtra("categoriaId", Integer.parseInt(categoriaSeleccionada.getCategoriaId().toString()));
                    startActivity(intent);
                    dismiss();
                }
            });


        }
        return view;
    }
}
