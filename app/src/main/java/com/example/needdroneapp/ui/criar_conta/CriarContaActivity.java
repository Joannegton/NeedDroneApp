package com.example.needdroneapp.ui.criar_conta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityCriarContaBinding;

public class CriarContaActivity extends AppCompatActivity {

    private ActivityCriarContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura o OnClickListener para os RadioButtons
        binding.rbCliente.setOnClickListener(this::onClick);
        binding.rbPiloto.setOnClickListener(this::onClick);
    }
    
    public void onClick(View v) {
        if (v.getId() == R.id.rbCliente) {
            // Inicia a CriarClienteActivity
            Intent clienteIntent = new Intent(this, CriarClienteActivity.class);
            startActivity(clienteIntent);
        } else if (v.getId() == R.id.rbPiloto) {
            // Inicia a CriarPilotoActivity
            Intent pilotoIntent = new Intent(this, CriarPilotoActivity.class);
            startActivity(pilotoIntent);
        }
    }
}
