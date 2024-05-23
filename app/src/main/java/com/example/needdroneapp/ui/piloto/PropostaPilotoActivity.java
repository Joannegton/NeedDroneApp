package com.example.needdroneapp.ui.piloto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.data.DroneController;
import com.example.needdroneapp.data.PropostaController;
import com.example.needdroneapp.databinding.ActivityPropostaPilotoBinding;
import com.example.needdroneapp.models.Drone;
import com.example.needdroneapp.models.DroneAdapter;

import java.util.List;

public class PropostaPilotoActivity extends AppCompatActivity {

    private ActivityPropostaPilotoBinding binding;

    private int droneSelecionado = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropostaPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DroneController db = new DroneController(getApplicationContext());
        List<Drone> droneList = db.pegarTodosDrones();
        DroneAdapter droneAdapter = new DroneAdapter(droneList, true);
        droneAdapter.setSelecao(droneId -> {
            // acesso ao droneId que foi clicado
            droneSelecionado = droneId;
        });
        recyclerView.setAdapter(droneAdapter);


        binding.btnEnviar.setOnClickListener(v -> enviarProposta());
    }

    private void enviarProposta() {
        if (droneSelecionado == -1) {
            Toast.makeText(this, "Selecione um drone!", Toast.LENGTH_SHORT).show();
        } else {
            PropostaController propostaController = new PropostaController(this);
            String valor = binding.etOfertaInicial.getText().toString().replace("R$", "").replace(",", ".");
            Float ofertaInicial = Float.parseFloat(valor);

            String descricao = binding.etDescricao.getText().toString();
            Integer projetoId = getIntent().getIntExtra("projetoId", 0);
            Integer clienteId = getIntent().getIntExtra("clienteId", 0);

            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            int pilotoId = preferences.getInt("userId", 0);
            String proposta = propostaController.insereDados(projetoId, clienteId, pilotoId , ofertaInicial, descricao, "Pendente", droneSelecionado);

            Toast.makeText(this, proposta, Toast.LENGTH_SHORT).show();


        }

    }
}