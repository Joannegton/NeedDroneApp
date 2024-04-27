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

// Esta é a classe do fragmento do painel (Dashboard)
public class DashboardFragment extends Fragment implements View.OnClickListener {

    // Variável para armazenar a referência ao binding do fragmento
    private FragmentDashboardBinding binding;

    // Este método é chamado para criar a visualização do fragmento
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout do fragmento
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configura os botões para editar drones
        Button btEditarDrone = root.findViewById(R.id.btEditarDrone);
        Button btEditarDrone2 = root.findViewById(R.id.btEditarDrone2);
        btEditarDrone.setOnClickListener(this);
        btEditarDrone2.setOnClickListener(this);

        // Configura os links para editar perfil, ver perfil e adicionar drone
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

        // Obtém as referências para os containers do fragmento e da proposta
        LinearLayout container_fragment = root.findViewById(R.id.container_fragment);
        ConstraintLayout container_proposta = root.findViewById(R.id.container_propostas);

        // Verifica o tipo de usuário e configura a visualização de acordo
        if (userType.equals("cliente")) {
            // Se o usuário é um cliente, esconde o container do fragmento
            container_fragment.setVisibility(View.GONE);

            // Obtém as informações do cliente
            String[] informacoesCliente = pegarInformacoes(userId);
            String nome = informacoesCliente[0];
            String avaliacao = informacoesCliente[1];

            // Configura os textos de nome e avaliação
            TextView txtNome = root.findViewById(R.id.tvNome);
            TextView txtEmail = root.findViewById(R.id.tvAvaliacao);
            txtNome.setText(nome);
            txtEmail.setText("Avaliação: " + avaliacao + " estrelas");
        } else if (userType.equals("piloto")) {
            // Se o usuário é um piloto, esconde o container da proposta
            container_proposta.setVisibility(View.GONE);

            // Obtém as informações do piloto
            String[] informacoesCliente = pegarInformacoes(userId);
            String nome = informacoesCliente[0];
            String avaliacao = informacoesCliente[1];

            // Configura os textos de nome e avaliação
            TextView txtNome = root.findViewById(R.id.tvNome);
            TextView txtEmail = root.findViewById(R.id.tvAvaliacao);
            txtNome.setText(nome);
            txtEmail.setText("Avaliação: " + avaliacao + " estrelas");
        } else {
            // Se o usuário não é nem cliente nem piloto, esconde ambos os containers
            container_fragment.setVisibility(View.GONE);
            container_proposta.setVisibility(View.GONE);
        }

        // Retorna a visualização do fragmento
        return root;
    }

    // Este método é chamado quando um botão é clicado
    @Override
    public void onClick(@NonNull View v) {
        // Verifica qual botão foi clicado e realiza a ação correspondente
        if (v.getId() == R.id.btEditarDrone || v.getId() == R.id.btEditarDrone2) {
            // Se o botão para editar drone foi clicado, inicia a atividade para editar drone
            Intent editDrone = new Intent(getContext(), EditDroneActivity.class);
            startActivity(editDrone);
        } else if (v.getId() == R.id.linkVer) {
            // Se o link para ver perfil foi clicado, inicia a atividade para ver perfil
            Intent verPerfil = new Intent(getContext(), PerfilActivity.class);
            startActivity(verPerfil);
        } else if (v.getId() == R.id.linkEditar) {
            // Se o link para editar perfil foi clicado, verifica o tipo de usuário e inicia a atividade correspondente
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String userType = sharedPreferences.getString("userType", "");
            if (userType.equals("cliente")) {
                Intent editPerfil = new Intent(getContext(), EditClienteActivity.class);
                startActivity(editPerfil);
            } else if (userType.equals("piloto")) {
                Intent editPerfil = new Intent(getContext(), EditPilotoActivity.class);
                startActivity(editPerfil);
            }
        } else if (v.getId() == R.id.linkAdcDrone) {
            // Se o link para adicionar drone foi clicado, inicia a atividade para criar drone
            Intent adcDrone = new Intent(getContext(), CriarDroneActivity.class);
            startActivity(adcDrone);
        }
    }

    // Este método obtém as informações do cliente
    @SuppressLint("Range")
    public String[] pegarInformacoes(int id) {
        ClienteController clienteController = new ClienteController(getContext());
        Cursor dados = clienteController.carregaDadosPorId(id);
        String[] informacoesCliente = new String[2];
        if (dados != null && dados.getCount() > 0 && dados.moveToFirst()) {
            informacoesCliente[0] = dados.getString(dados.getColumnIndex("nome"));
            informacoesCliente[1] = String.valueOf(dados.getInt(dados.getColumnIndex("avaliacaoCliente")));
        }
        return informacoesCliente;
    }

    // Este método é chamado quando a visualização do fragmento é destruída
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpa a referência ao binding
        binding = null;
    }
}