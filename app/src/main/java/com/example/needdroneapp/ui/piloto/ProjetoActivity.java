package com.example.needdroneapp.ui.piloto;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityHistoricoBinding;
import com.example.needdroneapp.databinding.ActivityProjetoBinding;
import com.example.needdroneapp.databinding.ActivityProjetosBinding;

public class ProjetoActivity extends AppCompatActivity {

    private ActivityProjetoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}