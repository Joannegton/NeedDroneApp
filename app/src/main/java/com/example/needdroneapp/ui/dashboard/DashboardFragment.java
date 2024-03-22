package com.example.needdroneapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.needdroneapp.CriarDroneActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.FragmentDashboardBinding;
import com.example.needdroneapp.ui.edicao.EditDroneActivity;
import com.example.needdroneapp.ui.edicao.EditPilotoActivity;
import com.example.needdroneapp.ui.piloto.HistoricoActivity;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btEditarDrone.setOnClickListener(this::onClick);
        binding.btEditarDrone2.setOnClickListener(this::onClick);
        binding.linkEditar.setOnClickListener(this::onClick);
        binding.linkVer.setOnClickListener(this::onClick);
        return root;

    }

    private void onClick(View v){
        if (v.getId() == R.id.btEditarDrone || v.getId() == R.id.btEditarDrone2){
            Intent editDrone = new Intent(getContext(), EditDroneActivity.class);
            startActivity(editDrone);
        } else if (v.getId() == R.id.linkVer) {
            Intent verPerfil = new Intent(getContext(), HistoricoActivity.class);
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