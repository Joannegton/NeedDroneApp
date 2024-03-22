package com.example.needdroneapp.ui.piloto;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityHistoricoBinding;
import com.example.needdroneapp.databinding.ActivityPropostaPilotoBinding;

public class PropostaPilotoActivity extends AppCompatActivity {

    private ActivityPropostaPilotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropostaPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}