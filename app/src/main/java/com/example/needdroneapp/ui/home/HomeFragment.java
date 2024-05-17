package com.example.needdroneapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.AvaliacaoController;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.databinding.FragmentHomeBinding;
import com.example.needdroneapp.models.Avaliacao;
import com.example.needdroneapp.ui.cadastros.CriarContaActivity;
import com.example.needdroneapp.ui.cadastros.CriarPilotoActivity;
import com.example.needdroneapp.ui.cadastros.CriarProjetoActivity;
import com.example.needdroneapp.ui.login.LoginFragment;

import java.io.File;
import java.util.List;

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
        btnLogin.setOnClickListener(this); //Implementar implements View.OnClickListener na class
        btnCriar.setOnClickListener(this);
        btnPublicar.setOnClickListener(this);

        lancarAvaliacao();

        TextView tvCadastrese = binding.tvCadastrese;
        tvCadastrese.setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.btnLogin) {
            // Inicia a LoginActivity
            LoginFragment loginFragment = new LoginFragment();

            // Obtém o FragmentManager para iniciar a transação
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Inicia a transação para substituir o conteúdo do contêiner pelo LoginFragment
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, loginFragment)
                    .addToBackStack(null)  // Adiciona a transação à pilha de fragmentos
                    .commit();
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

    @SuppressLint("Range")
    public void lancarAvaliacao(){
        AvaliacaoController avaliacaoController = new AvaliacaoController(getContext());
        List<Avaliacao> avaliacoes = avaliacaoController.pegarAvaliacoes();

        if (!avaliacoes.isEmpty()) {
            avaliacoes.forEach(avaliacao -> {
                ClienteController clienteController = new ClienteController(getContext());
                try (Cursor dados = clienteController.carregaDadosPorId(avaliacao.getPilotoId())) {
                    if (dados != null && dados.moveToFirst()) {
                        String fotoPath = dados.getString(dados.getColumnIndex("foto"));
                        Glide.with(this)
                                .load(new File(fotoPath)) // Carrega a imagem a partir do arquivo
                                .into(binding.profileImg); // Define a imagem no ImageView
                        binding.tvNomeUsuario.setText(dados.getString(dados.getColumnIndex("nome")));
                        binding.tvComentario.setText(avaliacao.getComentario());
                        binding.ratingBar.setRating(avaliacao.getAvaliacao());
                    }
                } catch (Exception e) {
                    Log.e("DB_ERROR", "Erro ao carregar dados do cliente", e);
                }
            });
        }






    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
