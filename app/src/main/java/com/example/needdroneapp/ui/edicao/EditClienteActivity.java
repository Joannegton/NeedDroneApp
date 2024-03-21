package com.example.needdroneapp.ui.edicao;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.ide.common.xml.ManifestData;
import com.example.needdroneapp.R;

public class EditClienteActivity extends AppCompatActivity {

    private ActivityEditClienteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getroot());
    }
}