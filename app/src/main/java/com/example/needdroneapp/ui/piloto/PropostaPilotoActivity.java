package com.example.needdroneapp.ui.piloto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.data.DroneController;
import com.example.needdroneapp.databinding.ActivityPropostaPilotoBinding;
import com.example.needdroneapp.models.Drone;
import com.example.needdroneapp.models.DroneAdapter;

import java.util.List;

public class PropostaPilotoActivity extends AppCompatActivity {

    private ActivityPropostaPilotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropostaPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DroneController db = new DroneController(getApplicationContext());
        List<Drone> droneList = db.pegarTodosDrones();
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