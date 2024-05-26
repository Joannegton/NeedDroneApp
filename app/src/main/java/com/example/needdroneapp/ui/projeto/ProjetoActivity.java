package com.example.needdroneapp.ui.projeto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.ComentarActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.data.PropostaController;
import com.example.needdroneapp.databinding.ActivityProjetoBinding;
import com.example.needdroneapp.models.ProjetoViewModel;
import com.example.needdroneapp.models.Proposta;
import com.example.needdroneapp.models.PropostaAdapter;
import com.example.needdroneapp.ui.ChatActivity;
import com.example.needdroneapp.ui.PerfilActivity;
import com.example.needdroneapp.ui.piloto.PropostaPilotoActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ProjetoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityProjetoBinding binding;
    private ProjetoViewModel projetoViewModel;

    private Integer clienteId;
    private String userType;
    private Integer userId;

    private String rua;
    private String cidadeEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projetoViewModel = new ViewModelProvider(this).get(ProjetoViewModel.class);
        projetoViewModel.getStatusProjeto().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String status) {
                if ("Andamento".equals(status)) {
                   // binding.btAceitar.setVisibility(View.GONE);
                    // Atualize a interface do usuário conforme necessário
                }
            }
        });

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userType = preferences.getString("userType", "");
        userId = preferences.getInt("userId", 0);

        Integer projetoId = getIntent().getIntExtra("projetoId", 0);

        informacoesProjeto(projetoId);
        carregarPropostas(projetoId);

        // Mapa do Google
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if(userType.equals("piloto")){
            binding.btEnviarProposta.setVisibility(View.VISIBLE);
            binding.btEnviarProposta.setOnClickListener(v -> {
                Intent intent = new Intent(this, PropostaPilotoActivity.class);
                intent.putExtra("projetoId", projetoId);
                intent.putExtra("clienteId", clienteId);
                startActivity(intent);
            });
        } else {
            binding.btEnviarProposta.setVisibility(View.GONE);
        }

    }

    @SuppressLint("Range")
    public void informacoesProjeto(Integer projetoId){
        ProjetoController projetoController = new ProjetoController(this);
        try (Cursor dados = projetoController.buscarProjeto(projetoId)) {
            if (dados.getCount() > 0) {
                dados.moveToFirst();
                rua = dados.getString(dados.getColumnIndex("rua"));
                cidadeEstado = dados.getString(dados.getColumnIndex("cidadeEstado"));
                clienteId = dados.getInt(dados.getColumnIndex("clienteId"));

                binding.titulo.setText(dados.getString(dados.getColumnIndex("titulo")));
                binding.descricao.setText(dados.getString(dados.getColumnIndex("descricao")));
                binding.dataEvento.setText(dados.getString(dados.getColumnIndex("dataEvento")));
                binding.localizacao.setText(rua + " - " + cidadeEstado);

                ClienteController clienteController = new ClienteController(this);
                String clienteNome = clienteController.pegarNomePorId(clienteId);
                binding.cliente.setText(clienteNome);

                binding.cliente.setOnClickListener(v -> {
                    Intent intent = new Intent(this, PerfilActivity.class);
                    intent.putExtra("userType", "cliente");
                    intent.putExtra("userId", clienteId);
                    startActivity(intent);
                });
            }
        }

    }

    @SuppressLint("Range")
    public void carregarPropostas(Integer projetoId) {
        PropostaController propostaController = new PropostaController(getApplicationContext());

        if (userType.equals("piloto")) {
            binding.textViewPropostas.setText("Propostas enviadas");
            RecyclerView recyclerView = binding.recyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Proposta> listaPropostas = propostaController.buscarPropostasProjetoUserId(projetoId, userId);
            PropostaAdapter adapter = new PropostaAdapter(listaPropostas, this);
            recyclerView.setAdapter(adapter);

            if (listaPropostas.isEmpty()) {
                binding.textViewPropostas.setVisibility(View.GONE);
            }
        } else {
            RecyclerView recyclerView = binding.recyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Proposta> listaPropostas = propostaController.buscarPropostasProjeto(projetoId);
            PropostaAdapter adapter = new PropostaAdapter(listaPropostas, this);
            recyclerView.setAdapter(adapter);

            if (listaPropostas.isEmpty()) {
                binding.textViewPropostas.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Integer projetoId = getIntent().getIntExtra("projetoId", 0);
        carregarPropostas(projetoId);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        String address = rua + ", " + cidadeEstado;
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.setBuildingsEnabled(false);
                googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            }
        } catch (Exception e) {
            Log.e("Erro", e.getMessage());
        }

    }
}
