package com.example.needdroneapp.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.DroneController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.data.PropostaController;
import com.example.needdroneapp.databinding.FragmentDashboardBinding;
import com.example.needdroneapp.models.Projeto;
import com.example.needdroneapp.models.ProjetoAdapter;
import com.example.needdroneapp.ui.PerfilActivity;
import com.example.needdroneapp.ui.cadastros.CriarDroneActivity;
import com.example.needdroneapp.ui.cadastros.CriarProjetoActivity;
import com.example.needdroneapp.ui.edicao.EditClienteActivity;
import com.example.needdroneapp.ui.edicao.EditDroneActivity;
import com.example.needdroneapp.ui.edicao.EditPilotoActivity;
import com.example.needdroneapp.models.Drone;
import com.example.needdroneapp.models.DroneAdapter;
import com.example.needdroneapp.ui.login.LoginFragment;
import com.example.needdroneapp.ui.piloto.ProjetoActivity;

import java.util.List;

// Esta é a classe do fragmento do painel (Dashboard)
public class DashboardFragment extends Fragment implements View.OnClickListener {

    // Variável para armazenar a referência ao binding do fragmento
    private FragmentDashboardBinding binding;

    // Este método é chamado para criar a visualização do fragmento
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout do fragmento
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtém as SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        // Recupera o tipo de usuário e o id do usuário
        String userType = sharedPreferences.getString("userType", "");
        int userId = sharedPreferences.getInt("userId", 0);

        if (userType.isEmpty()) {
            LoginFragment loginFragment = new LoginFragment();
            // Obtém o FragmentManager para iniciar a transação
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            // Inicia a transação para substituir o conteúdo do contêiner pelo LoginFragment
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, loginFragment)
                    .addToBackStack(null)  // Adiciona a transação à pilha de fragmentos
                    .commit();
        } else {

            // Configura os links para editar perfil, ver perfil e adicionar drone
            TextView linkEditPerfil = root.findViewById(R.id.linkEditar);
            TextView linkVerPerfil = root.findViewById(R.id.linkVer);
            linkEditPerfil.setOnClickListener(this);
            linkVerPerfil.setOnClickListener(this);

            // Obtém as referências para os containers do fragmento e da proposta
            LinearLayout container_fragment = root.findViewById(R.id.container_fragment);
            LinearLayout container_projeto = root.findViewById(R.id.container_projeto);

            // Verifica o tipo de usuário e configura a visualização de acordo
            if (userType.equals("cliente")) {
                // Se o usuário é um cliente, esconde o container do fragmento
                container_fragment.setVisibility(View.GONE);

                TextView linkAdcProjeto = root.findViewById(R.id.linkAdcProjeto);
                linkAdcProjeto.setOnClickListener(this);

                // Obtém as informações do cliente
                String[] informacoesCliente = pegarInformacoesCliente(userId);
                String nome = informacoesCliente[1];
                String avaliacao = informacoesCliente[3];
                String fotoPath = informacoesCliente[2];

                // Configura a imagem do cliente
                Bitmap bitmap = EditClienteActivity.loadImageFromStorage(fotoPath);
                if (fotoPath != null) {
                    binding.imageViewProfile.setImageBitmap(bitmap);
                } else {
                    binding.imageViewProfile.setImageResource(android.R.drawable.ic_menu_camera);
                }

                // Configura os textos de nome e avaliação
                TextView txtNome = root.findViewById(R.id.tvNome);
                txtNome.setText(nome);
                RatingBar ratingBar = root.findViewById(R.id.ratingBar);
                if (avaliacao != null) {
                    ratingBar.setRating(Float.parseFloat(avaliacao));
                } else {
                    // Trate o caso em que avaliacao é nulo, talvez definindo um valor padrão para a classificação
                    ratingBar.setRating(0.0f);
                }

                //adicionar lista de projetos que o cliente tem cadastrado
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                RecyclerView listViewListaProjetos = root.findViewById(R.id.listaProjetos);
                listViewListaProjetos.setLayoutManager(layoutManager);
                ProjetoController db = new ProjetoController(getContext());
                List<Projeto> listaProjetos = db.listarProjetosPorIdCliente(userId);
                ProjetoAdapter projetoAdapter = new ProjetoAdapter(listaProjetos, getContext());
                listViewListaProjetos.setAdapter(projetoAdapter);

                TextView tvPropostas = root.findViewById(R.id.tvPropostas);
                tvPropostas.setText("Propostas enviadas\n" + listaProjetos.size());

                TextView tvDinheiro = root.findViewById(R.id.tvDinheiro);
                tvDinheiro.setText("Dinheiro gasto\nR$ 640,00");
            } else if (userType.equals("piloto")) {
                // Se o usuário é um piloto, esconde o container da proposta
                container_projeto.setVisibility(View.GONE);

                Button btEditarDrone = root.findViewById(R.id.btEditarDrone);
                if (btEditarDrone != null) {
                    btEditarDrone.setOnClickListener(this);
                }
                TextView linkAdcDrone = root.findViewById(R.id.linkAdcDrone);
                linkAdcDrone.setOnClickListener(this);

                // Obtém as informações do piloto
                String[] informacoesPiloto = pegarInformacoesPiloto(userId);
                String nome = informacoesPiloto[1];
                String avaliacao = informacoesPiloto[3];
                String fotoPath = informacoesPiloto[2];

                // Configura a imagem do cliente
                Bitmap bitmap = EditPilotoActivity.loadImageFromStorage(fotoPath);
                if (fotoPath != null) {
                    binding.imageViewProfile.setImageBitmap(bitmap);
                } else {
                    binding.imageViewProfile.setImageResource(android.R.drawable.ic_menu_camera);
                }

                // Configura os textos de nome e avaliação
                TextView txtNome = root.findViewById(R.id.tvNome);
                txtNome.setText(nome);
                RatingBar ratingBar = root.findViewById(R.id.ratingBar);
                ratingBar.setRating(Float.parseFloat(avaliacao));

                //adicionar lista de drones que o piloto tem cadastrado
                RecyclerView listViewListaDrones = root.findViewById(R.id.listaDrones);
                DroneController db = new DroneController(getContext());
                List<Drone> listaDrones = db.pegarDronesPorPiloto(userId);
                configRecyclerView(listViewListaDrones, listaDrones);
            } else {
                // Se o usuário não é nem cliente nem piloto, esconde ambos os containers
                container_fragment.setVisibility(View.GONE);
                container_projeto.setVisibility(View.GONE);
            }
        }

        // Retorna a visualização do fragmento
        return root;
    }



    // Este método é chamado quando um botão é clicado
    @Override
    public void onClick(@NonNull View v) {
        // Verifica qual botão foi clicado e realiza a ação correspondente
        if (v.getId() == R.id.btEditarDrone) {
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
        } else if (v.getId() == R.id.linkAdcProjeto) {
            Intent adcProjeto = new Intent(getContext(), CriarProjetoActivity.class);
            startActivity(adcProjeto);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userType = sharedPreferences.getString("userType", "");
        int userId = sharedPreferences.getInt("userId", 0);

        if (userType.equals("piloto")) {
            //adicionar lista de drones que o piloto tem cadastrado
            RecyclerView listViewListaDrones = getView().findViewById(R.id.listaDrones);
            DroneController db = new DroneController(getContext());
            List<Drone> listaDrones = db.pegarDronesPorPiloto(userId);
            configRecyclerView(listViewListaDrones, listaDrones);
        } else if (userType.equals("cliente")) {
            //adicionar lista de projetos que o cliente tem cadastrado
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
            RecyclerView listViewListaProjetos = getView().findViewById(R.id.listaProjetos);
            listViewListaProjetos.setLayoutManager(layoutManager);
            ProjetoController db = new ProjetoController(getContext());
            List<Projeto> listaProjetos = db.listarProjetosPorIdCliente(userId);
            ProjetoAdapter projetoAdapter = new ProjetoAdapter(listaProjetos, getContext());
            listViewListaProjetos.setAdapter(projetoAdapter);
        }
    }

    private void configRecyclerView(RecyclerView recyclerView, List<Drone> drones) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        DroneAdapter droneAdapter = new DroneAdapter(drones, false);
        recyclerView.setAdapter(droneAdapter);
    }

    // Este método obtém as informações do cliente
    @SuppressLint("Range")
    public String[] pegarInformacoesCliente(int id) {
        ClienteController clienteController = new ClienteController(getContext());
        Cursor dados = clienteController.carregaDadosPorId(id);
        String[] informacoesCliente = new String[4];
        if (dados != null && dados.getCount() > 0 && dados.moveToFirst()) {
            informacoesCliente[0] = dados.getString(dados.getColumnIndex("id"));
            informacoesCliente[1] = dados.getString(dados.getColumnIndex("nome"));
            informacoesCliente[2] = dados.getString(dados.getColumnIndex("foto"));
            informacoesCliente[3] = String.valueOf(dados.getInt(dados.getColumnIndex("avaliacaoCliente")));
        }
        return informacoesCliente;
    }

    //Obtem informações das propostas recebidas
    public void infromacoesPropostas(int projetoId){
        PropostaController proposta = new PropostaController(getContext());
        Cursor dados = proposta.recuperarInfosIdProjeto(projetoId);
    }

    //Obtem informações do piloto
    @SuppressLint("Range")
    private String[] pegarInformacoesPiloto(int id) {
        PilotoController pilotoController = new PilotoController(getContext());
        Cursor dados = pilotoController.carregaDadosPorId(id);
        String[] informacoesPiloto = new String[4];
        if (dados != null && dados.getCount() > 0 && dados.moveToFirst()) {
            informacoesPiloto[0] = dados.getString(dados.getColumnIndex("id"));
            informacoesPiloto[1] = dados.getString(dados.getColumnIndex("nome"));
            informacoesPiloto[2] = dados.getString(dados.getColumnIndex("foto"));
            informacoesPiloto[3] = String.valueOf(dados.getInt(dados.getColumnIndex("avaliacaoPiloto")));
        }
        return informacoesPiloto;
    }

    // Este método é chamado quando a visualização do fragmento é destruída
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpa a referência ao binding
        binding = null;
    }
}