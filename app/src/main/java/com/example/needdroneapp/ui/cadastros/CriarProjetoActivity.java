package com.example.needdroneapp.ui.cadastros;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.databinding.ActivityCriarProjetoBinding;

import java.util.Calendar;
import java.util.Objects;

public class CriarProjetoActivity extends AppCompatActivity {

    private ActivityCriarProjetoBinding binding;

    private Button btnSelectDateTime;
    private String data;
    private Integer clienteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarProjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnSelectDateTime = findViewById(R.id.btnSelectDateTime);
        btnSelectDateTime.setOnClickListener(v -> showDateTimePickerDialog());
        binding.btnEnviar.setOnClickListener(v -> enviarProjeto());

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        clienteId = prefs.getInt("userId", 0);

        binding.TextViewDate.setVisibility(View.GONE);
        binding.TextViewHora.setVisibility(View.GONE);

    }

    private void enviarProjeto() {
        String titulo = binding.titulo.getText().toString();
        String descricao = Objects.requireNonNull(binding.descricao.getText()).toString();
        String rua = binding.editTextRua.getText().toString();
        String cidadeEstado = binding.editTextCidadeEstado.getText().toString();
        ProjetoController db = new ProjetoController(getBaseContext());
        String resultado = db.insereDados(titulo, descricao, data, rua, cidadeEstado, clienteId, null);
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();

        finish();

    }

    private void showDateTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CriarProjetoActivity.this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    data = dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1;
                    TimePickerDialog timePickerDialog = new TimePickerDialog(CriarProjetoActivity.this,
                            (view1, hourOfDay1, minute1) -> {
                                String hora = hourOfDay1 + ":" + minute1;
                                binding.TextViewDate.setText("Data: " + data);
                                binding.TextViewHora.setText("Horário: " + hora);
                                binding.TextViewDate.setVisibility(View.VISIBLE);
                                binding.TextViewHora.setVisibility(View.VISIBLE);
                            }, hourOfDay, minute, true);
                    timePickerDialog.show();
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}

