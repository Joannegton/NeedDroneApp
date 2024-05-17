package com.example.needdroneapp.ui.piloto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.needdroneapp.ComentarActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.databinding.ActivityProjetoBinding;
import com.example.needdroneapp.models.ProjetoViewModel;
import com.example.needdroneapp.ui.ChatActivity;

public class ProjetoActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProjetoBinding binding;
    private ProjetoViewModel projetoViewModel;

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
                    binding.btAceitar.setVisibility(View.GONE);
                    // Atualize a interface do usuário conforme necessário
                }
            }
        });

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userType = preferences.getString("userType", "");

        Integer projetoId = getIntent().getIntExtra("projetoId", 0);
        informacoesProjeto(projetoId);

        if(userType.equals("piloto")){
            binding.btEnviarProposta.setVisibility(View.VISIBLE);
            binding.btEnviarProposta.setOnClickListener(this);
        } else {
            binding.btEnviarProposta.setVisibility(View.GONE);
        }

        binding.btRecusar.setOnClickListener(this);
        binding.btAceitar.setOnClickListener(this);
        binding.btMensagem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btEnviarProposta) {
            Integer projetoId = getIntent().getIntExtra("projetoId", 0);
            Intent enviarProposta = new Intent(ProjetoActivity.this, PropostaPilotoActivity.class);
            enviarProposta.putExtra("projetoId", projetoId);
            startActivity(enviarProposta);
        }  else if (v.getId() == R.id.btAceitar) {
            Integer projetoId = getIntent().getIntExtra("projetoId", 0);
            ProjetoController projetoController = new ProjetoController(this);
            if (projetoController.projetoExiste(projetoId)) {
                projetoController.atualizarStatusProjeto(projetoId, "Andamento");
                Toast.makeText(this, "Status atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                binding.btAceitar.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Projeto não encontrado!", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btRecusar) {
            //Mudar a img, tirar todos os botões
            Integer projetoId = getIntent().getIntExtra("projetoId", 0);
            ProjetoController projetoController = new ProjetoController(this);
            if(projetoController.projetoExiste(projetoId)){
                projetoController.atualizarStatusProjeto(projetoId, "Cancelado");
                Toast.makeText(this, "Recusado com sucesso!", Toast.LENGTH_SHORT).show();

            } else{
                Toast.makeText(this, "Projeto não encontrado!", Toast.LENGTH_SHORT).show();

            }
            binding.btAceitar.setVisibility(View.GONE);
            binding.btRecusar.setVisibility(View.GONE);
            binding.btMensagem.setVisibility(View.GONE);

        } else if (v.getId() == R.id.btMensagem) {
            Integer projetoId = getIntent().getIntExtra("projetoId", 0);
            ProjetoController projetoController = new ProjetoController(this);
            Integer clienteId = projetoController.buscarClientePorProjeto(projetoId);
            Integer pilotoId = projetoController.buscarPilotoPorProjeto(projetoId);

            Intent mensagem = new Intent(ProjetoActivity.this, ComentarActivity.class);
            mensagem.putExtra("pilotoId", pilotoId);
            mensagem.putExtra("clienteId", clienteId);
            startActivity(mensagem);
        }
    }

    @SuppressLint("Range")
    public void informacoesProjeto(Integer projetoId){
        ClienteController clienteController = new ClienteController(this);
        String clienteNome = clienteController.pegarNomePorId(projetoId);

        ProjetoController projetoController = new ProjetoController(this);
        try (Cursor dados = projetoController.buscarProjeto(projetoId)) {
            if (dados.getCount() > 0) {
                dados.moveToFirst();
                String rua = dados.getString(dados.getColumnIndex("rua"));
                String cidadeEstado = dados.getString(dados.getColumnIndex("cidadeEstado"));

                binding.titulo.setText(dados.getString(dados.getColumnIndex("titulo")));
                binding.descricao.setText(dados.getString(dados.getColumnIndex("descricao")));
                binding.dataEvento.setText("Data do Evento: " + dados.getString(dados.getColumnIndex("dataEvento")));
                binding.localizacao.setText("Local: " + rua + " - " + cidadeEstado);
                binding.cliente.setText("De: " + clienteNome);
            }
        }

    }
}
