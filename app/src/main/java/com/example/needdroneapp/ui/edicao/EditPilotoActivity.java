package com.example.needdroneapp.ui.edicao;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.databinding.ActivityEditPilotoBinding;

public class EditPilotoActivity extends AppCompatActivity {

    private ActivityEditPilotoBinding binding;

    SharedPreferences preferences;
    int userId;
    PilotoController pilotoController;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = preferences.getInt("userId", 0);

        pilotoController = new PilotoController(getBaseContext());
        cursor = pilotoController.carregaDadosPorId(userId);

        carregaDados();
        binding.btnAtualizar.setOnClickListener(v -> atualizarDados());
    }

    private void atualizarDados() {
        String nome = binding.username.getText().toString();
        String biografia = binding.biografia.getText().toString();
        //String dataNasc = cursor.getString(cursor.getColumnIndexOrThrow("dataNasc"));
        String tel = binding.telefone.getText().toString();
        Boolean whatsapp = binding.whatsapp.isChecked();
        String rua = binding.rua.getText().toString();
        String cidadeEstado = binding.cidadeEstado.getText().toString();
        String cep = binding.cep.getText().toString();
        //String foto = binding.foto.getText().toString();
        String licenca = binding.licenca.getText().toString();
        String experiencia = binding.experiencia.getText().toString();
        String especializacao = binding.especializacao.getText().toString();

        String dataNasc = cursor.getString(cursor.getColumnIndexOrThrow("dataNasc"));

        String resultado = pilotoController.atualizaDados(
                userId,
                nome,
                dataNasc,
                tel,
                whatsapp,
                rua,
                cidadeEstado,
                cep,
                null,
                biografia,
                experiencia,
                especializacao
        );

        if (resultado.equals("Registro atualizado com sucesso!")){
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        cursor = pilotoController.carregaDadosPorId(userId);

        carregaDados();
    }

    public void carregaDados(){
        binding.username.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        binding.email.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        binding.biografia.setText(cursor.getString(cursor.getColumnIndexOrThrow("biografia")));
        //binding.editDataNasc.setText(cursor.getString(cursor.getColumnIndexOrThrow("dataNasc")));
        binding.telefone.setText(cursor.getString(cursor.getColumnIndexOrThrow("tel")));
        binding.whatsapp.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("whatsapp")) > 0);
        binding.rua.setText(cursor.getString(cursor.getColumnIndexOrThrow("rua")));
        binding.cidadeEstado.setText(cursor.getString(cursor.getColumnIndexOrThrow("cidadeEstado")));
        binding.cep.setText(cursor.getString(cursor.getColumnIndexOrThrow("cep")));
        //binding.foto.setText(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
        binding.licenca.setText(cursor.getString(cursor.getColumnIndexOrThrow("licencaPilotagem")));
        binding.experiencia.setText(cursor.getString(cursor.getColumnIndexOrThrow("experiencia")));
        binding.especializacao.setText(cursor.getString(cursor.getColumnIndexOrThrow("especializacao")));
    }
}