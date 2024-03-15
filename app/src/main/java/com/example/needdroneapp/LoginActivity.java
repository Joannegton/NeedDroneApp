package com.example.needdroneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import com.example.needdroneapp.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        int itemSelecionado = binding.radioGroup.getCheckedRadioButtonId();

        if (itemSelecionado != -1) { // -1 significa que nada foi selecionado
            RadioButton rbUsuarioSelecionado = findViewById(itemSelecionado);

            String usuarioSelecionado = rbUsuarioSelecionado.getText().toString();
            binding.txtEsqueciSenha.setText(usuarioSelecionado);

        }

    }
}
