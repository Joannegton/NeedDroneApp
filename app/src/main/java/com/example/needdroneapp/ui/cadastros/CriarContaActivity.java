package com.example.needdroneapp.ui.cadastros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        binding.btContinuar.setOnClickListener(this::onClick);
    }
    
    public void onClick(View v) {
        if (binding.rbCliente.isChecked()) {
            // Inicia a CriarClienteActivity
            Intent clienteIntent = new Intent(this, CriarClienteActivity.class);
            startActivity(clienteIntent);
            finish();
        } else if (binding.rbPiloto.isChecked()) {
            // Inicia a CriarPilotoActivity
            Intent pilotoIntent = new Intent(this, CriarPilotoActivity.class);
            startActivity(pilotoIntent);
            finish();
        }
    }
}
