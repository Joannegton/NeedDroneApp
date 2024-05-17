package com.example.needdroneapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.data.AvaliacaoController;
import com.example.needdroneapp.databinding.ActivityComentarBinding;
import com.example.needdroneapp.models.Avaliacao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ComentarActivity extends AppCompatActivity {

    ActivityComentarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComentarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Integer clienteId = preferences.getInt("userId", 0);
        Integer pilotoId = getIntent().getIntExtra("pilotoId", 0);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        // Formatar a data em uma string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(date);

        binding.btnEnviar.setOnClickListener(v ->{
            RatingBar ratingBar = binding.ratingBar;
            float avaliacao = ratingBar.getRating();
            String comentario = binding.etComentario.getText().toString();
            AvaliacaoController avaliacaoController = new AvaliacaoController(this);
            avaliacaoController.insereDados(pilotoId, clienteId, comentario, dateString, avaliacao);

        });

    }
}