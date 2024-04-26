package com.example.needdroneapp.ui.piloto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.databinding.ActivityPropostaPilotoBinding;

import java.util.List;

public class PropostaPilotoActivity extends AppCompatActivity {

    private ActivityPropostaPilotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropostaPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerView;
        DroneItemActivity databaseHelper = new DroneItemActivity(this);
        List<Drone> droneList = databaseHelper.getAllDrones();
        DroneAdapter droneAdapter = new DroneAdapter(droneList);
        recyclerView.setAdapter(droneAdapter);

        binding.btnEnviar.setOnClickListener(v -> enviarProposta());
    }

    private void enviarProposta() {
        String valor = binding.etOfertaInicial.getText().toString();
        String descricao = binding.etDescricao.getText().toString();
        //String drone = binding.containerDrones.getSelectedItem().toString();
    }
}