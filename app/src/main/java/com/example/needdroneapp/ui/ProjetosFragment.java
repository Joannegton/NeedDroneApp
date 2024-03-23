package com.example.needdroneapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.needdroneapp.databinding.FragmentLoginBinding;
import com.example.needdroneapp.databinding.FragmentProjetosBinding;


public class ProjetosFragment extends Fragment {

    private FragmentProjetosBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProjetosBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Agora você pode acessar o elemento raiz do layout usando rootView

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}