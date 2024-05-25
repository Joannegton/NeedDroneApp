package com.example.needdroneapp.ui.cadastros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.needdroneapp.MainActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.databinding.ActivityCriarPilotoBinding;
import com.example.needdroneapp.ui.login.LoginFragment;

public class CriarPilotoActivity extends AppCompatActivity {
    private ActivityCriarPilotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarPilotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCriar.setOnClickListener(v -> validaDados());
    }

    private void validaDados(){
        String email = binding.etEmail.getText().toString().trim();
        String senha = binding.etSenha.getText().toString().trim();
        String confirmaSenha = binding.etConfirmaSenha.getText().toString().trim();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                if (senha.equals(confirmaSenha)) {
                    criarConta(email, senha);
                } else {
                    Toast.makeText(this, "Senhas são diferentes!", Toast.LENGTH_SHORT).show();
                }
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
        String licenca = binding.etLicanca.getText().toString().trim();

        PilotoController db = new PilotoController(getBaseContext());
        String resultado;
        resultado = db.insereDados(nome, email, senha, cpf, null, null, null, null, null, null, null, null, licenca);

        Toast.makeText(this, resultado, Toast.LENGTH_LONG).show();
        limpar();

        // Intent para retornar à atividade principal onde o LoginFragment pode ser exibido
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("OpenFragment", "LoginFragment"); // Identificador para o fragmento que deve ser aberto
        startActivity(intent);
        finish(); // Finaliza a atividade atual para que o usuário não retorne a ela ao pressionar o botão voltar

    }

    public  void limpar(){
        binding.etNome.setText("") ;
        binding.etEmail.setText("");
        binding.etSenha.setText("");
        binding.etCPF.setText("");
        binding.etLicanca.setText("");
    }
}