package com.example.needdroneapp.ui.cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.needdroneapp.databinding.ActivityCriarPilotoBinding;

public class CriarPilotoActivity extends AppCompatActivity {
    private ActivityCriarPilotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCriar.setOnClickListener(v -> validaDados()); //cria o evento de click
    }

    private void validaDados(){
        String email = binding.etEmail.getText().toString().trim();
        String senha = binding.etSenha.getText().toString().trim();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                criarConta(email, senha);
            }else{
                Toast.makeText(this, "Informe uma senha!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Informe um email!", Toast.LENGTH_SHORT).show();
        }
    }

    private  void criarConta(String email, String senha){
        String nome = binding.etNome.getText().toString().trim();
        String cpf = binding.etCPF.getText().toString().trim();
        String cidade = binding.etCidade.getText().toString().trim();
        String rua = binding.etRua.getText().toString().trim();

        String dados = "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Senha: " + senha + "\n" +
                "CPF: " + cpf + "\n" +
                "Cidade: " + cidade + "\n" +
                "Rua: " + rua;

        Toast.makeText(this, dados, Toast.LENGTH_LONG).show();
        //Enviar para o banco de dados
       /* mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }else {
                Toast.makeText(this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}