package com.example.needdroneapp.ui.cadastros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needdroneapp.MainActivity;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.databinding.ActivityCriarClienteBinding;
import com.example.needdroneapp.ui.login.LoginFragment;

public class CriarClienteActivity extends AppCompatActivity {
    private ActivityCriarClienteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCriar.setOnClickListener(v -> validaDados()); //cria o evento de click
    }

    private void validaDados(){
        String email = binding.etEmail.getText().toString().trim();
        String senha = binding.etSenha.getText().toString().trim();
        String confirmaSenha = binding.etConfirmaSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                if (senha.equals(confirmaSenha)) {
                    criarConta(email, senha);
                } else {
                    Toast.makeText(this, "Senhas são diferentes!", Toast.LENGTH_SHORT).show();
                }

            } else{
                    Toast.makeText(this, "Informe uma senha!", Toast.LENGTH_SHORT).show();
            }

        } else {
                Toast.makeText(this, "Informe um email!", Toast.LENGTH_SHORT).show();
        }
    }

    private  void criarConta(String email, String senha){
        String nome = binding.etNome.getText().toString().trim();
        String cpf = binding.etCPF.getText().toString().trim();

        //conexão com o banco de dados
        ClienteController db = new ClienteController(getBaseContext());
        String resultado;
        resultado = db.insereDados(nome, email, senha, cpf, null, null, null, null, null, null, null, null, 3 );

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

        // Intent para retornar à atividade principal onde o LoginFragment pode ser exibido
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("OpenFragment", "LoginFragment"); // Identificador para o fragmento que deve ser aberto
        startActivity(intent);
        finish(); // Finaliza a atividade atual para que o usuário não retorne a ela ao pressionar o botão voltar

    }



}