package com.example.needdroneapp.ui.piloto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityProjetoBinding;
import com.example.needdroneapp.ui.ChatActivity;

public class ProjetoActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProjetoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        } else if (v.getId() == R.id.btAceitar) {
            //Mudar a img, tirar o botão recusar
            Intent aceitar = new Intent(ProjetoActivity.this, ChatActivity.class);
            startActivity(aceitar);
        } else if (v.getId() == R.id.btRecusar) {
            //Mudar a img, tirar todos os botões
            Intent recusar = new Intent(ProjetoActivity.this, ChatActivity.class);
            startActivity(recusar);
        } else if (v.getId() == R.id.btMensagem) {
            //mudar a imagem e tirar os botões
            Intent mensagem = new Intent(ProjetoActivity.this, ChatActivity.class);
            startActivity(mensagem);
        }
    }
}
