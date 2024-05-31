package com.example.comandaxpress.Pantallas.Fragment;

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
import com.example.comandaxpress.R;

import java.util.ArrayList;

/**
 * Pantalla usada para mostrar el modal de seleccion de categoria en la pantalla de ticket
 * */

public class DialogoCategoriasFragment extends DialogFragment {
    private static final String ARG_CATEGORIAS = "categorias";
    private CategoriaSeleccionadaListener mListener;

    public static DialogoCategoriasFragment newInstance(ArrayList<Categoria> categorias) {
        DialogoCategoriasFragment fragment = new DialogoCategoriasFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORIAS, categorias);
        fragment.setArguments(args);
        return fragment;
    }

    public interface CategoriaSeleccionadaListener {
        void onCategoriaSeleccionada(Categoria categoria);
    }

    public void setCategoriaSeleccionadaListener(CategoriaSeleccionadaListener listener) {
        mListener = listener;
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
                    if (mListener != null) {
                        mListener.onCategoriaSeleccionada(categoriaSeleccionada);
                    }
                    dismiss();
                }
            });
        }
        return view;
    }
}
