package com.example.needdroneapp.ui.edicao;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.needdroneapp.databinding.ActivityEditClienteBinding;

public class EditClienteActivity extends AppCompatActivity {

    private ActivityEditClienteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}