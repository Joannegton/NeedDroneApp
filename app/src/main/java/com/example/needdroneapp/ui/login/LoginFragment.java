package com.example.needdroneapp.ui.login;

import android.content.Context;
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
import com.example.needdroneapp.data.DbController;
import com.example.needdroneapp.databinding.FragmentLoginBinding;
import com.example.needdroneapp.ui.dashboard.DashboardFragment;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;
    private RadioButton rbCliente, rbPiloto;
    private EditText email, senha;
    public static final String PREF_USER_TYPE = "user_type";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        rbCliente = binding.radioCliente;
        rbPiloto = binding.radioPiloto;
        email = binding.editTextEmailAddress;
        senha = binding.editTextPassword;

        rootView.findViewById(R.id.btnLogin).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.btnLogin) {
            if (rbCliente.isChecked()) {
                validarDados("cliente");
            } else if (rbPiloto.isChecked()) {
                validarDados("piloto");
            }
        }
    }

    private void validarDados(String tipoUsuario) {
        String emailText = email.getText().toString();
        String senhaText = senha.getText().toString();

        if (emailText.isEmpty() || senhaText.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        DbController db = new DbController(getActivity().getBaseContext());
        try (Cursor dados = db.carregaDadosLogin(emailText, senhaText)) {
            if (dados.moveToFirst()) {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PREF_USER_TYPE, tipoUsuario);
                editor.apply();

                Toast.makeText(getContext(), "Login bem-sucedido como " + tipoUsuario, Toast.LENGTH_SHORT).show();

                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, dashboardFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Email ou Senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
