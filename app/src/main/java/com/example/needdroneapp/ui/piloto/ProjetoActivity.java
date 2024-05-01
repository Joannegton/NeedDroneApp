package com.example.needdroneapp.ui.piloto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

        Integer projetoId = getIntent().getIntExtra("projetoId", 0);
        informacoesProjeto(projetoId);
        Toast.makeText(this, String.valueOf(projetoId), Toast.LENGTH_SHORT).show();

        binding.btEnviarProposta.setOnClickListener(this);
        binding.btRecusar.setOnClickListener(this);
        binding.btAceitar.setOnClickListener(this);
        binding.btMensagem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btEnviarProposta) {
            Intent enviarProposta = new Intent(ProjetoActivity.this, PropostaPilotoActivity.class);
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
            //mudar a imagem e tirar os botões
            Intent mensagem = new Intent(ProjetoActivity.this, ChatActivity.class);
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
