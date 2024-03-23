package com.example.needdroneapp.ui.piloto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.databinding.ActivityProjetoBinding;

public class ProjetoActivity extends AppCompatActivity {

    private ActivityProjetoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}