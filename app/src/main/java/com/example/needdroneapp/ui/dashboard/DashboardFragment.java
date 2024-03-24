package com.example.needdroneapp.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.FragmentDashboardBinding;
import com.example.needdroneapp.ui.PerfilActivity;
import com.example.needdroneapp.ui.ProjetosFragment;
import com.example.needdroneapp.ui.cadastros.CriarDroneActivity;
import com.example.needdroneapp.ui.edicao.EditDroneActivity;
import com.example.needdroneapp.ui.edicao.EditPilotoActivity;
import com.example.needdroneapp.ui.login.LoginFragment;
import com.example.needdroneapp.ui.piloto.HistoricoActivity;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private FragmentDashboardBinding binding;
    private String userType;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btEditarDrone = root.findViewById(R.id.btEditarDrone);
        Button btEditarDrone2 = root.findViewById(R.id.btEditarDrone2);
        btEditarDrone.setOnClickListener(this);
        btEditarDrone2.setOnClickListener(this);

        TextView linkEditPerfil = root.findViewById(R.id.linkEditar);
        TextView linkVerPerfil = root.findViewById(R.id.linkVer);
        TextView linkAdcDrone = root.findViewById(R.id.linkAdcDrone);

        linkEditPerfil.setOnClickListener(this);
        linkVerPerfil.setOnClickListener(this);
        linkAdcDrone.setOnClickListener(this);

        // Obtém as SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        // Recupera o tipo de usuário
        userType = sharedPreferences.getString(LoginFragment.PREF_USER_TYPE, "");

        LinearLayout container_fragment = root.findViewById(R.id.container_fragment);
        ConstraintLayout container_proposta = root.findViewById(R.id.container_propostas);
        if (userType.equals("cliente")) {
            container_proposta.setVisibility(View.GONE);
        } else if (userType.equals("piloto")){
            container_fragment.setVisibility(View.GONE);
        }

        return root;

    }
    @Override
    public void onClick(@NonNull View v){
        if (v.getId() == R.id.btEditarDrone || v.getId() == R.id.btEditarDrone2){
            Intent editDrone = new Intent(getContext(), EditDroneActivity.class);
            startActivity(editDrone);
        } else if (v.getId() == R.id.linkVer) {
            Intent verPerfil = new Intent(getContext(), PerfilActivity.class);
            startActivity(verPerfil);
        } else if (v.getId() == R.id.linkEditar) { //organizar por tipo de perfil
            Intent editPerfil = new Intent(getContext(), EditPilotoActivity.class);
            startActivity(editPerfil);
        }else if (v.getId() == R.id.linkAdcDrone) { //organizar por tipo de perfil
            Intent adcDrone = new Intent(getContext(), CriarDroneActivity.class);
            startActivity(adcDrone);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}