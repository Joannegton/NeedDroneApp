package com.example.needdroneapp.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityEsqueciSenhaBinding;

public class EsqueciSenhaActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEsqueciSenhaBinding binding;
    private EditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEsqueciSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         emailText = binding.editTextEmail;

        binding.buttonRecuperarSenha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (emailText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Insira um e-mail!",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Enviamos um e-mail de recuperação!",Toast.LENGTH_LONG).show();
        }
    }
}