package com.example.needdroneapp.ui.edicao;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.databinding.ActivityEditPilotoBinding;

public class EditPilotoActivity extends AppCompatActivity {

    private ActivityEditPilotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}