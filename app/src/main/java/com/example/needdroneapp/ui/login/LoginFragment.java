package com.example.needdroneapp.ui.login;

import android.content.Intent;
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
import com.example.needdroneapp.databinding.FragmentLoginBinding;
import com.example.needdroneapp.ui.dashboard.DashboardFragment;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;
    private RadioButton rbCliente, rbPiloto;
    private EditText email, senha;
    public static final String PREF_USER_TYPE = "user_type";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        rbCliente = rootView.findViewById(R.id.radio_cliente);
        rbPiloto = rootView.findViewById(R.id.radio_piloto);
        email = rootView.findViewById(R.id.editTextEmailAddress);
        senha = rootView.findViewById(R.id.editTextPassword);

        rootView.findViewById(R.id.btnLogin).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.btnLogin) {
            if (rbCliente.isChecked()) {
                validarDados();

            } else if (rbPiloto.isChecked()) {
                validarDados();
            }
        }
    }

    public void validarDados() {
        if (email.getText().toString().equals("teste@teste.com") && senha.getText().toString().equals("123456")) {
            //Inicia dashboardFragment
            DashboardFragment dashboardFragment = new DashboardFragment();
            // Obtém o FragmentManager para iniciar a transação
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            // Inicia a transação para substituir o conteúdo do contêiner pelo LoginFragment
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, dashboardFragment)
                    .addToBackStack(null) // Adiciona a transação à pilha de fragmentos
                    .commit();
        } else {
            Toast.makeText(getContext(), "Email ou Senha incorretos!", Toast.LENGTH_SHORT).show();
        }
    }
}
