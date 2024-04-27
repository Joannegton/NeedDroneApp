package com.example.needdroneapp.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.databinding.FragmentDashboardBinding;
import com.example.needdroneapp.ui.PerfilActivity;
import com.example.needdroneapp.ui.cadastros.CriarDroneActivity;
import com.example.needdroneapp.ui.edicao.EditClienteActivity;
import com.example.needdroneapp.ui.edicao.EditDroneActivity;
import com.example.needdroneapp.ui.edicao.EditPilotoActivity;
import com.example.needdroneapp.ui.login.LoginFragment;


public class DashboardFragment extends Fragment implements View.OnClickListener{

    private FragmentDashboardBinding binding;

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
        // Recupera o tipo de usuário e o id do usuário
        String userType = sharedPreferences.getString("userType", "");
        int userId = sharedPreferences.getInt("userId", 0);
        LinearLayout container_fragment = root.findViewById(R.id.container_fragment);
        ConstraintLayout container_proposta = root.findViewById(R.id.container_propostas);

        if (userType.equals("cliente")) {
            container_fragment.setVisibility(View.GONE);
            String[] informacoesCliente = pegarInformacoes(userId);
            String nome = informacoesCliente[0];
            String avaliacao = informacoesCliente[1];

            TextView txtNome = root.findViewById(R.id.tvNome);
            TextView txtEmail = root.findViewById(R.id.tvAvaliacao);
            txtNome.setText(nome);
            txtEmail.setText("Avaliação: " + avaliacao + " estrelas");
        } else if (userType.equals("piloto")){
            container_proposta.setVisibility(View.GONE);
            String[] informacoesCliente = pegarInformacoes(userId);
            String nome = informacoesCliente[0];
            String avaliacao = informacoesCliente[1];

            TextView txtNome = root.findViewById(R.id.tvNome);
            TextView txtEmail = root.findViewById(R.id.tvAvaliacao);
            //TextView premium = root.findViewById(R.id.linkSejaPremium);
            txtNome.setText(nome);
            txtEmail.setText("Avaliação: " + avaliacao + " estrelas");
        } else {
            container_fragment.setVisibility(View.GONE);
            container_proposta.setVisibility(View.GONE);
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
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String userType = sharedPreferences.getString("userType", "");
            if (userType.equals("cliente")) {
                Intent editPerfil = new Intent(getContext(), EditClienteActivity.class);
                startActivity(editPerfil);
            }else if (userType.equals("piloto")) {
                Intent editPerfil = new Intent(getContext(), EditPilotoActivity.class);
                startActivity(editPerfil);
            }
        }else if (v.getId() == R.id.linkAdcDrone) { //organizar por tipo de perfil
            Intent adcDrone = new Intent(getContext(), CriarDroneActivity.class);
            startActivity(adcDrone);
        }
    }


    @SuppressLint("Range")
    public String[] pegarInformacoes(int id){
        ClienteController clienteController = new ClienteController(getContext());
        Cursor dados = clienteController.carregaDadosPorId(id);
        String[] informacoesCliente = new String[2];
        if (dados != null && dados.getCount() > 0 && dados.moveToFirst()){
            informacoesCliente[0] = dados.getString(dados.getColumnIndex("nome"));
            informacoesCliente[1] = String.valueOf(dados.getInt(dados.getColumnIndex("avaliacaoCliente")));
        }
        return informacoesCliente;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}