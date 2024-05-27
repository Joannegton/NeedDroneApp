package com.example.needdroneapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.data.AvaliacaoController;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.databinding.ActivityComentarBinding;
import com.example.needdroneapp.models.Avaliacao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ComentarActivity extends AppCompatActivity {

    ActivityComentarBinding binding;

    Integer avaliadoId;
    Integer avaliadorId;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComentarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Integer projetoId = getIntent().getIntExtra("projetoId", 0);
        String userType = preferences.getString("userType", "");

        ProjetoController projetoController = new ProjetoController(this);
        Cursor cursor = projetoController.buscarProjeto(projetoId);

        Toast.makeText(this, userType, Toast.LENGTH_SHORT).show();
        if (userType.equals("cliente")){
            avaliadoId = cursor.getInt(cursor.getColumnIndex("pilotoId"));
            avaliadorId = cursor.getInt(cursor.getColumnIndex("clienteId"));
            binding.tvAvaliado.setText(carregarDados(avaliadoId, "piloto"));

        } else {
            avaliadoId = cursor.getInt(cursor.getColumnIndex("clienteId"));
            avaliadorId = cursor.getInt(cursor.getColumnIndex("pilotoId"));
            binding.tvAvaliado.setText(carregarDados(avaliadorId, "cliente"));

        }

        // Formatar a data em uma string
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(date);

        binding.btnEnviar.setOnClickListener(v ->{
            RatingBar ratingBar = binding.ratingBar;
            float avaliacao = ratingBar.getRating();
            String comentario = binding.etComentario.getText().toString();

            AvaliacaoController avaliacaoController = new AvaliacaoController(this);
            String retorno = avaliacaoController.insereDados(avaliadorId, avaliadoId, comentario, dateString, avaliacao);

            if (userType.equals("cliente")){
                ClienteController clienteController = new ClienteController(this);
                clienteController.atualizarAvaliacao(avaliadoId, avaliacaoController.mediaAvaliacoes(avaliadoId));
            } else {
                PilotoController pilotoController = new PilotoController(this);
                pilotoController.atualizarAvaliacao(avaliadoId, avaliacaoController.mediaAvaliacoes(avaliadoId));
            }

            Toast.makeText(this, retorno, Toast.LENGTH_SHORT).show();
            finish();


        });
    }

    private String carregarDados(Integer userId, String userType){
        if (userType.equals("cliente")){
            ClienteController clienteController = new ClienteController(this);
            return clienteController.pegarNomePorId(userId);
        } else {
            PilotoController pilotoController = new PilotoController(this);
            return pilotoController.pegarNomePorId(userId);
        }
    }
}