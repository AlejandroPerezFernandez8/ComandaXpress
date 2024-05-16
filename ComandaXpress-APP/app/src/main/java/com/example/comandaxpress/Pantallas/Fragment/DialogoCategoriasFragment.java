package com.example.comandaxpress.Pantallas.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.comandaxpress.API.Clases.Categoria;
import com.example.comandaxpress.Adapters.AdaptadorCategorias;
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
        }
        return view;
    }
}
