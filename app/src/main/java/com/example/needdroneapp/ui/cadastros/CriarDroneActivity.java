package com.example.needdroneapp.ui.cadastros;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.R;

public class CriarDroneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_drone);

        setupSpinner(R.id.spTipo);
        setupSpinner(R.id.spQualidadeImagem);
        setupSpinner(R.id.spAutonomia);
        setupSpinner(R.id.spAreaCobertura);
        setupSpinner(R.id.spStatus);
    }

    private void setupSpinner(int spinnerId) {
        Spinner spinner = findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opcoes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcaoSelecionada = parent.getItemAtPosition(position).toString();
                Toast.makeText(CriarDroneActivity.this, "Opção selecionada: " + opcaoSelecionada, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação a ser executada quando nenhuma opção é selecionada
            }
        });
    }
}
