package com.example.needdroneapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.needdroneapp.LoginActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.FragmentHomeBinding;
import com.example.needdroneapp.ui.cadastros.CriarContaActivity;
import com.example.needdroneapp.ui.cadastros.CriarPilotoActivity;
import com.example.needdroneapp.ui.cadastros.CriarProjetoActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configura o OnClickListener para os botões btnLogin e btnCriar
        Button btnLogin = root.findViewById(R.id.btnLogin);
        Button btnCriar = root.findViewById(R.id.btnCriar);
        Button btnPublicar = root.findViewById(R.id.btPublicar);
        btnLogin.setOnClickListener(this);
        btnCriar.setOnClickListener(this);
        btnPublicar.setOnClickListener(this);

        TextView tvCadastrese = binding.tvCadastrese;
        tvCadastrese.setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.btnLogin) {
            // Inicia a LoginActivity
            Intent loginIntent = new Intent(getContext(), LoginActivity.class);
            startActivity(loginIntent);
        } else if (v.getId() == R.id.btnCriar){
            // Inicia a CriarClienteActivity
            Intent criarIntent = new Intent(getContext(), CriarContaActivity.class);
            startActivity(criarIntent);
        } else if (v.getId() == R.id.tvCadastrese) {
            Intent cadastrarPiloto = new Intent(getContext(), CriarPilotoActivity.class);
            startActivity(cadastrarPiloto);
        } else if (v.getId() == R.id.btPublicar) {
            Intent publicarProjeto = new Intent(getContext(), CriarProjetoActivity.class);
            startActivity(publicarProjeto);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
