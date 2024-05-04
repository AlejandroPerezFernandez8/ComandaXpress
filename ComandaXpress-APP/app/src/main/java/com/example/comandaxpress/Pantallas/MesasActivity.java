package com.example.comandaxpress.Pantallas;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comandaxpress.R;

public class MesasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        GridLayout gridLayout = findViewById(R.id.GridMesas);
        int orientacion = getResources().getConfiguration().orientation;
        if (orientacion == Configuration.ORIENTATION_LANDSCAPE){
            gridLayout.setColumnCount(5);
        }else {
            gridLayout.setColumnCount(3);
        }

    }
}