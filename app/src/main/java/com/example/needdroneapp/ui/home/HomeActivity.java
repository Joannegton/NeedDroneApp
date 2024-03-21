package com.example.needdroneapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.needdroneapp.LoginActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityHomeBinding;
import com.example.needdroneapp.ui.cadastros.CriarPilotoActivity;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding.btnLogin.setOnClickListener((View.OnClickListener) this);
        binding.btnCriar.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v){
        // Verifica qual botão foi clicado
        if (v == binding.btnLogin) {
            // Inicia a LoginActivity
            Intent telaLogin = new Intent(this, LoginActivity.class);
            startActivity(telaLogin);
        } else if (v == binding.btnCriar) {
            // Inicia a CriarPilotoActivity
            Intent telaCriar = new Intent(this, CriarPilotoActivity.class);
            startActivity(telaCriar);
        }

    }

}