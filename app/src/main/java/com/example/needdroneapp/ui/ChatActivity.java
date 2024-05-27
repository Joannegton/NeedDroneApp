package com.example.needdroneapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.ComentarActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.MensagemController;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.databinding.ActivityChatBinding;
import com.example.needdroneapp.models.Avaliacao;
import com.example.needdroneapp.models.Mensagem;
import com.example.needdroneapp.models.MensagemAdapter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private MensagemAdapter adapter;
    private List<Mensagem> mensagemList;
    private MensagemController mensagemController;
    private Integer clienteId;
    private Integer pilotoId;

    @Override
    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Integer projetoId = getIntent().getIntExtra("projetoId", 0);

        ProjetoController projetoController = new ProjetoController(this);
        Cursor cursor = projetoController.buscarProjeto(projetoId);
        pilotoId = cursor.getInt(cursor.getColumnIndex("pilotoId"));
        clienteId = cursor.getInt(cursor.getColumnIndex("clienteId"));

        SharedPreferences preferences = getSharedPreferences("My_prefs", MODE_PRIVATE);
        String userType = preferences.getString("userType", "");

        binding.tvChatTitle.setText(cursor.getString(cursor.getColumnIndex("titulo")));

        binding.spStatusProjeto.setText(cursor.getString(cursor.getColumnIndex("status")));

        binding.btCancelar.setOnClickListener(v -> {
            projetoController.atualizarStatusProjeto(projetoId, "Cancelado");
            Toast.makeText(this, "Projeto cancelado!", Toast.LENGTH_SHORT).show();
        });
        binding.btFinalizar.setOnClickListener(v ->{
            projetoController.atualizarStatusProjeto(projetoId, "Finalizado");
            Toast.makeText(this, "Projeto Finalizado com sucesso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ComentarActivity.class);
            intent.putExtra("projetoId", projetoId);
            startActivity(intent);
        });

        mensagemController = new MensagemController(this);
        mensagemList = mensagemController.listarMensagensEntreUsuarios(clienteId, pilotoId);

        RecyclerView rvMensagens = binding.recyclerView;
        adapter = new MensagemAdapter(mensagemList, clienteId, userType);        rvMensagens.setLayoutManager(new LinearLayoutManager(this));
        rvMensagens.setAdapter(adapter);

        binding.btnSendMessage.setOnClickListener(v -> {
            String texto = binding.etMessageInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String dataHora = sdf.format(new Date());

            if (!texto.isEmpty()) {
                if (userType.equals("cliente")){
                    mensagemController.insereDados(clienteId, pilotoId, texto, dataHora, true);
                } else {
                    mensagemController.insereDados(pilotoId, clienteId, texto, dataHora, true);
                }
                binding.etMessageInput.setText("");
                atualizarMensagens();
            }
        });
    }

    private void atualizarMensagens() {
        mensagemList.clear();
        List<Mensagem> mensagensChat = mensagemController.listarMensagensEntreUsuarios(clienteId, pilotoId);
        mensagemList.addAll(mensagensChat);
        Collections.sort(mensagemList, new Comparator<Mensagem>() {
            public int compare(Mensagem m1, Mensagem m2) {
                return m1.getDataHora().compareTo(m2.getDataHora());
            }
        });
        adapter.notifyDataSetChanged();
    }
}