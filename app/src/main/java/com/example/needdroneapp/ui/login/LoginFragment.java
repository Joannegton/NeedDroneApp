package com.example.needdroneapp.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.data.UsuarioController;
import com.example.needdroneapp.databinding.FragmentLoginBinding;
import com.example.needdroneapp.ui.cadastros.CriarContaActivity;
import com.example.needdroneapp.ui.dashboard.DashboardFragment;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private RadioButton rbCliente, rbPiloto;
    private EditText email, senha;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Inicializando os componentes da interface do usuário
        rbCliente = binding.radioCliente;
        rbPiloto = binding.radioPiloto;
        email = binding.editTextEmailAddress;
        senha = binding.editTextPassword;

        // Configurando os listeners dos botões
        rootView.findViewById(R.id.btnLogin).setOnClickListener(this);
        rootView.findViewById(R.id.txtCadastro).setOnClickListener(this);
        rootView.findViewById(R.id.txtEsqueciSenha).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(@NonNull View v) {
        // Verificando qual botão foi clicado
        if (v.getId() == R.id.btnLogin) {
            // Se o botão de login foi clicado, verifica qual tipo de usuário está tentando fazer login
            if (rbCliente.isChecked()) {
                validarDados("cliente");
            } else if (rbPiloto.isChecked()) {
                validarDados("piloto");
            }
        } else if (v.getId() == R.id.txtCadastro) {
            // Se o botão de cadastro foi clicado, abre a atividade de cadastro
            Intent criarConta = new Intent(getContext(), CriarContaActivity.class);
            startActivity(criarConta);
        } else if (v.getId() == R.id.txtEsqueciSenha) {
            // Se o botão de esqueci minha senha foi clicado, abre a atividade de recuperação de senha
            Intent esqueciSenha = new Intent(getContext(), EsqueciSenhaActivity.class);
            startActivity(esqueciSenha);
        }
    }

    private void validarDados(String tipoUsuario) {
        // Obtendo os dados inseridos pelo usuário
        String emailText = email.getText().toString();
        String senhaText = senha.getText().toString();

        // Verificando se todos os campos foram preenchidos
        if (emailText.isEmpty() || senhaText.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicializando o controlador de usuário apropriado com base no tipo de usuário
        UsuarioController db = null;
        if (tipoUsuario.equals("cliente")) {
            db = new ClienteController(requireActivity().getBaseContext());
        }
        if (tipoUsuario.equals("piloto")) {
            db = new PilotoController(requireActivity().getBaseContext());
        }

        // Verificando se o controlador de usuário foi inicializado corretamente
        if (db != null) {
            // Tentando fazer login com os dados inseridos pelo usuário
            try (Cursor dados = db.carregaDadosLogin(emailText, senhaText)) {
                if (dados.moveToFirst()) {
                    // Se o login foi bem-sucedido, salva o id e o tipo do usuário logado nas SharedPreferences
                    @SuppressLint("Range") int id = dados.getInt(dados.getColumnIndex("id"));
                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userType", tipoUsuario);
                    editor.putInt("userId", id);
                    editor.apply();

                    // Mostra uma mensagem de sucesso e abre o fragmento do painel
                    Toast.makeText(getContext(), "Login bem-sucedido como " + tipoUsuario, Toast.LENGTH_SHORT).show();

                    DashboardFragment dashboardFragment = new DashboardFragment();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment_content_main, dashboardFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    // Se o login falhou, mostra uma mensagem de erro
                    Toast.makeText(getContext(), "Email ou Senha incorretos!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}