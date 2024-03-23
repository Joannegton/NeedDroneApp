package com.example.needdroneapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.needdroneapp.R;
import com.example.needdroneapp.databinding.FragmentLoginBinding;
import com.example.needdroneapp.databinding.FragmentProjetosBinding;
import com.example.needdroneapp.ui.piloto.ProjetoActivity;


public class ProjetosFragment extends Fragment implements View.OnClickListener {

    private FragmentProjetosBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate o layout para esse fragment
        binding = FragmentProjetosBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Acessar o elemento raiz do layout usando rootView

        TextView projeto = rootView.findViewById(R.id.tvTitulo);
        TextView projeto1 = rootView.findViewById(R.id.tvTitulo1);
        TextView projeto2 = rootView.findViewById(R.id.tvTitulo2);

        projeto.setOnClickListener(this);
        projeto1.setOnClickListener(this);
        projeto2.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvTitulo || v.getId() == R.id.tvTitulo1 || v.getId() == R.id.tvTitulo2){
            Intent projeto = new Intent(getContext(), ProjetoActivity.class);
            startActivity(projeto);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}