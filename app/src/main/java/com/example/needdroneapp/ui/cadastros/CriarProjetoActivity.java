package com.example.needdroneapp.ui.cadastros;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityCriarProjetoBinding;

public class CriarProjetoActivity extends AppCompatActivity {

    private ActivityCriarProjetoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarProjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        };
}