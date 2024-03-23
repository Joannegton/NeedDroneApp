package com.example.needdroneapp.ui.cadastros;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.ActivityCriarProjetoBinding;

import java.util.Calendar;

public class CriarProjetoActivity extends AppCompatActivity {

    private ActivityCriarProjetoBinding binding;

    private Button btnSelectDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarProjetoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnSelectDateTime = findViewById(R.id.btnSelectDateTime);

        btnSelectDateTime.setOnClickListener(v -> showDateTimePickerDialog());
    }
    private void showDateTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Cria um DatePickerDialog para a seleção da data
        DatePickerDialog datePickerDialog = new DatePickerDialog(CriarProjetoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Ação a ser executada quando a data é selecionada
                        // Aqui você pode fazer algo com a data selecionada
                        Toast.makeText(CriarProjetoActivity.this, "Data selecionada: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year, Toast.LENGTH_SHORT).show();

                        // Cria um TimePickerDialog para a seleção da hora
                        TimePickerDialog timePickerDialog = new TimePickerDialog(CriarProjetoActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Ação a ser executada quando a hora é selecionada
                                        // Aqui você pode fazer algo com a hora selecionada
                                        Toast.makeText(CriarProjetoActivity.this, "Hora selecionada: " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                                    }
                                }, hourOfDay, minute, true); // true para usar o formato de 24 horas

                        timePickerDialog.show();
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
};
