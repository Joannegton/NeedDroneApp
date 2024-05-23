package com.example.needdroneapp.models;

import static android.content.Intent.getIntent;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.ComentarActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.ui.piloto.ProjetoActivity;
import com.example.needdroneapp.ui.piloto.PropostaPilotoActivity;

import java.util.List;

public class PropostaAdapter extends RecyclerView.Adapter<PropostaAdapter.VisualizadorProposta> {

    private List<Proposta> listaPropostas;
    private Activity activity;

    public PropostaAdapter(List<Proposta> propostaList, Activity activity){
        this.listaPropostas = propostaList;
        this.activity = activity;

    }

    @NonNull
    @Override
    public VisualizadorProposta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemproposta, parent, false);
        return new VisualizadorProposta(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VisualizadorProposta holder, int position) {
        Proposta proposta = listaPropostas.get(position);
        holder.enviadorProposta.setText(String.valueOf(proposta.getPilotoId()));
        holder.valorProposta.setText(proposta.getOfertaInicial().toString());
        holder.detalhesProposta.setText(proposta.getDetalhesProposta());
        float valorFinal = proposta.getOfertaInicial() + proposta.getOfertaInicial() * 0.3f;
        holder.valorPropostaFinal.setText(Float.toString(valorFinal));
    }

    @Override
    public int getItemCount() {
        return listaPropostas.size();
    }

    class VisualizadorProposta extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView enviadorProposta, detalhesProposta, valorProposta, valorPropostaFinal;
        Button btEnviarProposta, btAceitar, btRecusar, btMensagem;
        public VisualizadorProposta(@NonNull View itemView){
            super(itemView);
            enviadorProposta = itemView.findViewById(R.id.enviadorProposta);
            detalhesProposta = itemView.findViewById(R.id.detalhesProposta);
            valorProposta = itemView.findViewById(R.id.valorProposta);
            valorPropostaFinal = itemView.findViewById(R.id.valorPropostaFinal);

            btEnviarProposta = itemView.findViewById(R.id.btEnviarProposta);
            btAceitar = itemView.findViewById(R.id.btAceitar);
            btRecusar = itemView.findViewById(R.id.btRecusar);
            btMensagem = itemView.findViewById(R.id.btMensagem);



        }

        @Override
        public void onClick(View v) {
          /*  if (v.getId() == R.id.btEnviarProposta){
                Integer projetoId = activity.getIntent().getIntExtra("projetoId", 0);
                Intent enviarProposta = new Intent(activity, ProjetoActivity.class);
                enviarProposta.putExtra("projetoId", projetoId);
                activity.startActivity(enviarProposta);

            } else if (v.getId() == R.id.btAceitar) {
                Integer projetoId = activity.getIntent().getIntExtra("projetoId", 0);
                ProjetoController projetoController = new ProjetoController(activity);
                if (projetoController.projetoExiste(projetoId)) {
                    projetoController.atualizarStatusProjeto(projetoId, "Andamento");
                    Toast.makeText(activity, "Status atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    btAceitar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(activity, "Projeto não encontrado!", Toast.LENGTH_SHORT).show();
                }

            } else if (v.getId() == R.id.btRecusar) {
                //Mudar a img, tirar todos os botões
                Integer projetoId = activity.getIntent().getIntExtra("projetoId", 0);
                ProjetoController projetoController = new ProjetoController(activity);
                if(projetoController.projetoExiste(projetoId)){
                    projetoController.atualizarStatusProjeto(projetoId, "Cancelado");
                    Toast.makeText(activity, "Recusado com sucesso!", Toast.LENGTH_SHORT).show();

                } else{
                    Toast.makeText(activity, "Projeto não encontrado!", Toast.LENGTH_SHORT).show();

                }
                btAceitar.setVisibility(View.GONE);
                btRecusar.setVisibility(View.GONE);
                btMensagem.setVisibility(View.GONE);

            } else if (v.getId() == R.id.btMensagem) {
                Integer projetoId = activity.getIntent().getIntExtra("projetoId", 0);
                ProjetoController projetoController = new ProjetoController(activity);
                Integer clienteId = projetoController.buscarClientePorProjeto(projetoId);
                Integer pilotoId = projetoController.buscarPilotoPorProjeto(projetoId);

                Intent mensagem = new Intent(activity, ComentarActivity.class);
                mensagem.putExtra("pilotoId", pilotoId);
                mensagem.putExtra("clienteId", clienteId);
                activity.startActivity(mensagem);
            }
        */
            }
    }

}
