package com.example.needdroneapp;

import android.content.SharedPreferences;
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
        Integer avaliadorId = preferences.getInt("userId", 0);
        Integer avaliadoId = getIntent().getIntExtra("pilotoId", 0);
        String userType = preferences.getString("userType", "");

        Toast.makeText(this, avaliadoId.toString(), Toast.LENGTH_SHORT).show();


        // Formatar a data em uma string
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(date);

        if (userType.equals("cliente")){
            binding.tvAvaliado.setText(carregarDados(avaliadoId, "piloto"));
        } else {
            binding.tvAvaliado.setText(carregarDados(avaliadorId, "cliente"));
        }

        binding.btnEnviar.setOnClickListener(v ->{
            RatingBar ratingBar = binding.ratingBar;
            float avaliacao = ratingBar.getRating();
            String comentario = binding.etComentario.getText().toString();
            AvaliacaoController avaliacaoController = new AvaliacaoController(this);
            /*String retorno = "";
            if (userType.equals("cliente")){
                retorno = avaliacaoController.insereDados(clienteId, pilotoId, comentario, dateString, avaliacao);
            } else {
                retorno = avaliacaoController.insereDados(pilotoId, clienteId, comentario, dateString, avaliacao);
            }
            Toast.makeText(this, retorno, Toast.LENGTH_SHORT).show();
            finish();*/

            //Toast.makeText(this, pilotoId, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, comentario, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, (int) avaliacao, Toast.LENGTH_SHORT).show();
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