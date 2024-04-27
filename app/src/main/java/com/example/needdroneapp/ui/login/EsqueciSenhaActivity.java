package com.example.needdroneapp.ui.login;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.databinding.ActivityEsqueciSenhaBinding;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private EditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEsqueciSenhaBinding binding = ActivityEsqueciSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         emailText = binding.editTextEmail;

        binding.buttonRecuperarSenha.setOnClickListener(v -> onClick());
    }

    public void onClick() {
        if (emailText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Insira um e-mail!",Toast.LENGTH_LONG).show();

        } else {
            PilotoController db = new PilotoController(getApplicationContext());
            String senha = db.resetarSenha(String.valueOf(emailText.getText()));
            Toast.makeText(getApplicationContext(), senha,Toast.LENGTH_LONG).show();
        }
    }

}