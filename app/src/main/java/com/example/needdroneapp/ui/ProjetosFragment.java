package com.example.needdroneapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.databinding.FragmentLoginBinding;
import com.example.needdroneapp.databinding.FragmentProjetosBinding;
import com.example.needdroneapp.models.Projeto;
import com.example.needdroneapp.models.ProjetoCompletoAdapter;
import com.example.needdroneapp.ui.piloto.ProjetoActivity;

import java.util.List;


public class ProjetosFragment extends Fragment {

    private FragmentProjetosBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate o layout para esse fragment
        binding = FragmentProjetosBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();


        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProjetoController db = new ProjetoController(getContext());
        List<Projeto> listaProjetos = db.listarTodosProjetos();
        ProjetoCompletoAdapter projetoCompletoAdapter = new ProjetoCompletoAdapter(listaProjetos, getContext());
        recyclerView.setAdapter(projetoCompletoAdapter);

        binding.tvFiltro.setText(Integer.toString(listaProjetos.size()));

        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}