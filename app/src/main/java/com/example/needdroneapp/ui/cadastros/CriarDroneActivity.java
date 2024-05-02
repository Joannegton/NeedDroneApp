package com.example.needdroneapp.ui.cadastros;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.DroneController;
import com.example.needdroneapp.databinding.ActivityCriarDroneBinding;

public class CriarDroneActivity extends AppCompatActivity {
    private ActivityCriarDroneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarDroneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //criar o spinner para cada campo
        setupSpinner(R.id.spTipo, R.array.tipoDrone);
        setupSpinner(R.id.spQualidadeImagem, R.array.qualidadeDrone);
        setupSpinner(R.id.spAutonomia, R.array.autonomia);
        setupSpinner(R.id.spAreaCobertura, R.array.areaCobertura);

        binding.btnCadastrar.setOnClickListener(v -> criarDrone());
    }

    private void criarDrone() {
        String nome = binding.edtNome.getText().toString().trim(); //trim() remove espaços em branco
        Boolean imgSobreposicao = binding.cBImgSobreposicao.isChecked();
        //String foto = binding.edtDescricao.toString().trim();
        String tipo = valorSelecionado(R.id.spTipo);
        String qualidadeImagem = valorSelecionado(R.id.spQualidadeImagem);
        String autonomia = valorSelecionado(R.id.spAutonomia);
        String areaCobertura = valorSelecionado(R.id.spAreaCobertura);

        DroneController db = new DroneController(getBaseContext());
        String resultado;

        resultado = db.insereDados(nome, tipo, qualidadeImagem, autonomia, areaCobertura, null, imgSobreposicao, null, null);

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

    }

    private void setupSpinner(int spinnerId, int opcoesSpinner) {
        Spinner spinner = findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                opcoesSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcaoSelecionada = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação a ser executada quando nenhuma opção é selecionada
            }
        });
    }

    private String valorSelecionado(int spinnerId) {
        Spinner spinner = findViewById(spinnerId);
        return spinner.getSelectedItem().toString();
    }


}