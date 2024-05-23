package com.example.needdroneapp.ui.edicao;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.DroneController;
import com.example.needdroneapp.databinding.ActivityEditDroneBinding;
import com.example.needdroneapp.models.Drone;

import java.util.Arrays;

public class EditDroneActivity extends AppCompatActivity {


    private ActivityEditDroneBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDroneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setupSpinner(R.id.spTipo, R.array.tipoDrone);
        setupSpinner(R.id.spQualidadeImagem, R.array.qualidadeDrone);
        setupSpinner(R.id.spAutonomia, R.array.autonomia);
        setupSpinner(R.id.spAreaCobertura, R.array.areaCobertura);
        setupSpinner(R.id.spStatus, R.array.status);

        carregarDadosDrone();
        int idDrone = getIntent().getIntExtra("idDrone", 5);
        Toast.makeText(this, "ID do drone: " + idDrone, Toast.LENGTH_SHORT).show();


        binding.btnAtualizar.setOnClickListener(this::onClick);
        binding.btnExcluir.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        if (v.getId() == R.id.btnAtualizar) {
            int idDrone = getIntent().getIntExtra("idDrone", 0);
            String atualiza = atualizarDrone(idDrone);
            Toast.makeText(this, atualiza, Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btnExcluir) {
            int idDrone = getIntent().getIntExtra("idDrone", 0);
            String exclui = excluirDrone(idDrone);
            Toast.makeText(this, exclui, Toast.LENGTH_SHORT).show();
        }
    }

    private String atualizarDrone(int idDrone) {
        DroneController db = new DroneController(this);
        try {
            boolean checkSobreposica = binding.checkBox.isChecked();
            db.atualizarDrone(
                    idDrone,
                    binding.edtNome.getText().toString(),
                    binding.spTipo.getSelectedItem().toString(),
                    binding.spQualidadeImagem.getSelectedItem().toString(),
                    binding.spAutonomia.getSelectedItem().toString(),
                    binding.spAreaCobertura.getSelectedItem().toString(),
                    binding.spStatus.getSelectedItem().toString(),
                    checkSobreposica,
                    null)
            //binding.etFoto.getText().toString(),
            ;
        } catch (Exception e) {
            return "Erro ao atualizar drone: " + e.getMessage();
        }

        finish();
        return "Drone atualizado com sucesso!";
    }

    private String excluirDrone(int idDrone) {
        DroneController db = new DroneController(this);
        db.excluirDrone(idDrone);
        finish();
        return "Drone excluído com sucesso!";
    }

    private void carregarDadosDrone() {
        int idDrone = getIntent().getIntExtra("idDrone", 0);
        DroneController db = new DroneController(this);
        Cursor cursor = db.carregarDadosDrone(idDrone);

        if (cursor.moveToFirst()) {
            binding.edtNome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

            String tipoDrone = cursor.getString(cursor.getColumnIndexOrThrow("tipoDrone"));
            String[] tipoDroneOptions = getResources().getStringArray(R.array.tipoDrone);
            int tipoDroneIndex = Arrays.asList(tipoDroneOptions).indexOf(tipoDrone);
            binding.spTipo.setSelection(tipoDroneIndex);

            String qualidadeImagem = cursor.getString(cursor.getColumnIndexOrThrow("imgQualidade"));
            String[] qualidadeImagemOptions = getResources().getStringArray(R.array.qualidadeDrone);
            int qualidadeImagemIndex = Arrays.asList(qualidadeImagemOptions).indexOf(qualidadeImagem);
            binding.spQualidadeImagem.setSelection(qualidadeImagemIndex);

            String autonomia = cursor.getString(cursor.getColumnIndexOrThrow("autonomia"));
            String[] autonomiaOptions = getResources().getStringArray(R.array.autonomia);
            int autonomiaIndex = Arrays.asList(autonomiaOptions).indexOf(autonomia);
            binding.spAutonomia.setSelection(autonomiaIndex);

            String areaCobertura = cursor.getString(cursor.getColumnIndexOrThrow("areaCobertura"));
            String[] areaCoberturaOptions = getResources().getStringArray(R.array.areaCobertura);
            int areaCoberturaIndex = Arrays.asList(areaCoberturaOptions).indexOf(areaCobertura);
            binding.spAreaCobertura.setSelection(areaCoberturaIndex);

            String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
            String[] statusOptions = getResources().getStringArray(R.array.status);
            int statusIndex = Arrays.asList(statusOptions).indexOf(status);
            binding.spStatus.setSelection(statusIndex);

            binding.checkBox.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("imgSobreposicao"))> 0);
            //binding.etFoto.setText(cursor.getColumnIndexOrThrow("foto"));
        } else {
            Toast.makeText(this, "Erro ao carregar dados do drone", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinner(int spinnerId, int opcoes) {
        Spinner spinner = findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                opcoes, android.R.layout.simple_spinner_item);
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
}
