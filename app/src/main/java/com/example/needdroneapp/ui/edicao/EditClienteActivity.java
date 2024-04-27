package com.example.needdroneapp.ui.edicao;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.databinding.ActivityEditClienteBinding;

public class EditClienteActivity extends AppCompatActivity {

    private ActivityEditClienteBinding binding;

    SharedPreferences preferences;
    int userId;
    ClienteController clienteController;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = preferences.getInt("userId", 0);
        clienteController = new ClienteController(getBaseContext());
        cursor = clienteController.carregaDadosPorId(userId);

        carregaDados();
        binding.btnAtualizar.setOnClickListener(v -> atualizaDados());
    }


    public void carregaDados(){
        binding.username.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        binding.email.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        binding.biografia.setText(cursor.getString(cursor.getColumnIndexOrThrow("biografia")));
        //binding.editDataNasc.setText(cursor.getString(cursor.getColumnIndexOrThrow("dataNasc")));
        binding.telefone.setText(cursor.getString(cursor.getColumnIndexOrThrow("tel")));
        binding.rua.setText(cursor.getString(cursor.getColumnIndexOrThrow("rua")));
        binding.cidadeEstado.setText(cursor.getString(cursor.getColumnIndexOrThrow("cidadeEstado")));
        binding.cep.setText(cursor.getString(cursor.getColumnIndexOrThrow("cep")));
        //binding.foto.setText(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
    }
    public void atualizaDados() {
        String nome = binding.username.getText().toString();
        String biografia = binding.biografia.getText().toString();
        String dataNasc = cursor.getString(cursor.getColumnIndexOrThrow("dataNasc"));
        boolean whatsapp = cursor.getInt(cursor.getColumnIndexOrThrow("whatsapp")) > 0;
        String tel = binding.telefone.getText().toString();
        String rua = binding.rua.getText().toString();
        String cidadeEstado = cursor.getString(cursor.getColumnIndexOrThrow("cidadeEstado"));
        String cep = binding.cep.getText().toString();
        //String foto = binding.foto.getText().toString();

        String resultado = clienteController.alteraDados(userId ,nome, dataNasc, tel, whatsapp, rua, cidadeEstado, cep, null, biografia);
        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
    }
}