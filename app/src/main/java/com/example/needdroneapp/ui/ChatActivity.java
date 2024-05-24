package com.example.needdroneapp.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.databinding.ActivityChatBinding;

import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    @Override
    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Integer projetoId = getIntent().getIntExtra("projetoId", 0);

        ProjetoController projetoController = new ProjetoController(this);
        Cursor cursor = projetoController.buscarProjeto(projetoId);

        binding.tvChatTitle.setText(cursor.getString(cursor.getColumnIndex("titulo")));

        binding.spStatusProjeto.setText(cursor.getString(cursor.getColumnIndex("status")));

        binding.btCancelar.setOnClickListener(v -> {
            projetoController.atualizarStatusProjeto(projetoId, "Cancelado");
            Toast.makeText(this, "Projeto cancelado!", Toast.LENGTH_SHORT).show();
        });
        binding.btFinalizar.setOnClickListener(v ->{
            projetoController.atualizarStatusProjeto(projetoId, "Finalizado");
            Toast.makeText(this, "Projeto Finalizado com sucesso", Toast.LENGTH_SHORT).show();



        });

    }
}