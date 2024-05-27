package com.example.needdroneapp.models;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.DroneController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.data.PropostaController;
import com.example.needdroneapp.ui.ChatActivity;
import com.example.needdroneapp.ui.PerfilActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PropostaAdapter extends RecyclerView.Adapter<PropostaAdapter.VisualizadorProposta> {

    private List<Proposta> listaPropostas;
    private Activity activity;
    private Integer projetoId;
    private Integer propostaId;

    public PropostaAdapter(List<Proposta> propostaList, Activity activity){
        this.listaPropostas = propostaList;
        this.activity = activity;

        // Ordenar a lista de propostas por status
        Collections.sort(this.listaPropostas, new Comparator<Proposta>() {
            @Override
            public int compare(Proposta p1, Proposta p2) {
                return statusValue(p1.getStatus()) - statusValue(p2.getStatus());
            }

            private int statusValue(String status) {
                switch (status) {
                    case "Aceita":
                        return 1;
                    case "Pendente":
                        return 2;
                    case "Recusada":
                        return 3;
                    default:
                        return 4;
                }
            }
        });

    }

    @NonNull
    @Override
    public VisualizadorProposta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemproposta, parent, false);
        return new VisualizadorProposta(view);
    }

    @SuppressLint({"SetTextI18n", "Range"})
    @Override
    public void onBindViewHolder(@NonNull VisualizadorProposta holder, int position) {
        PilotoController piloto = new PilotoController(activity);
        Proposta proposta = listaPropostas.get(position);
        propostaId = proposta.getId();
        projetoId = proposta.getProjetoId();

        Cursor cursor = piloto.carregaDadosPorId(proposta.getPilotoId());
        if (cursor.moveToFirst()) {
            holder.enviadorProposta.setText(cursor.getString(cursor.getColumnIndex("nome")));
            holder.enviadorProposta.setTag(proposta.getPilotoId()); // Armazenar o ID do piloto como uma tag
        }
        holder.valorProposta.setText(proposta.getOfertaInicial().toString());
        holder.detalhesProposta.setText(proposta.getDetalhesProposta());

        String valorFinalFormatado = String.format("%.2f", proposta.getOfertaInicial() + proposta.getOfertaInicial() * 0.3f);
        holder.valorPropostaFinal.setText(valorFinalFormatado);

        // Setar tags para os botões de aceitar, recusar
        holder.btAceitar.setTag(propostaId);
        holder.btRecusar.setTag(propostaId);
        holder.btMensagem.setTag(propostaId);



        SharedPreferences preferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userType = preferences.getString("userType", "");
        if (userType.equals("piloto")) {
            holder.btAceitar.setVisibility(View.GONE);
            holder.btRecusar.setVisibility(View.GONE);
            if (proposta.getStatus().equals("Aceita")){
                holder.btMensagem.setVisibility(View.VISIBLE);
            }
        } else {
            if (proposta.getStatus().equals("Recusada")) {
                holder.statusImage.setImageResource(R.drawable.canceled);
                holder.btAceitar.setVisibility(View.GONE);
                holder.btRecusar.setVisibility(View.GONE);
                holder.btMensagem.setVisibility(View.GONE);
            } else if (proposta.getStatus().equals("Aceita")) {
                holder.statusImage.setImageResource(R.drawable.accepted);
                holder.btAceitar.setVisibility(View.GONE);
                holder.btRecusar.setVisibility(View.VISIBLE);
                holder.btMensagem.setVisibility(View.VISIBLE);
            } else {
                holder.statusImage.setVisibility(View.GONE);
                holder.btAceitar.setVisibility(View.VISIBLE);
                holder.btRecusar.setVisibility(View.VISIBLE);
                holder.btMensagem.setVisibility(View.VISIBLE);
            }
        }


        preencherDetalhesDrone(holder, proposta.getDroneId());

    }

    @Override
    public int getItemCount() {
        return listaPropostas.size();
    }

    class VisualizadorProposta extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView enviadorProposta, detalhesProposta, valorProposta, valorPropostaFinal, nomeDrone, droneTipo, droneAutonomia,  droneSobreposicao, droneResolucao, droneAreaCobertura;
        Button btAceitar, btRecusar, btMensagem;
        ImageView droneImagem, statusImage;
        public VisualizadorProposta(@NonNull View itemView){
            super(itemView);
            enviadorProposta = itemView.findViewById(R.id.enviadorProposta);
            detalhesProposta = itemView.findViewById(R.id.detalhesProposta);
            valorProposta = itemView.findViewById(R.id.valorProposta);
            valorPropostaFinal = itemView.findViewById(R.id.valorPropostaFinal);
            statusImage = itemView.findViewById(R.id.statusImage);

            droneImagem = itemView.findViewById(R.id.droneImagem);
            nomeDrone = itemView.findViewById(R.id.nomeDrone);
            droneTipo = itemView.findViewById(R.id.droneTipo);
            droneAutonomia = itemView.findViewById(R.id.droneAutonomia);
            droneSobreposicao = itemView.findViewById(R.id.droneSobreposicao);
            droneResolucao = itemView.findViewById(R.id.droneResolucao);
            droneAreaCobertura = itemView.findViewById(R.id.droneAreaCobertura);

            btAceitar = itemView.findViewById(R.id.btAceitar);
            btRecusar = itemView.findViewById(R.id.btRecusar);
            btMensagem = itemView.findViewById(R.id.btMensagem);

            btAceitar.setOnClickListener(this);
            btRecusar.setOnClickListener(this);
            btMensagem.setOnClickListener(this);
            enviadorProposta.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PropostaController propostaController = new PropostaController(activity);
            int pilotoId = propostaController.buscarPilotoPorProposta(propostaId);

            if (v.getId() == R.id.btAceitar){
                int propostaId = (int) v.getTag();
                propostaController.atualizarStatusProposta(propostaId, "Aceita");

                statusImage.setImageResource(R.drawable.accepted);

                ProjetoController projetoController = new ProjetoController(activity);
                projetoController.atualizarStatusUsuarioProjeto(projetoId, "Andamento", pilotoId);
                Toast.makeText(activity, "Proposta aceita!", Toast.LENGTH_SHORT).show();
                btAceitar.setVisibility(View.GONE);

            } else if (v.getId() == R.id.btRecusar) {
                int propostaId = (int) v.getTag();
                propostaController.atualizarStatusProposta(propostaId, "Recusada");
                statusImage.setImageResource(R.drawable.baseline_access_denied);
                ProjetoController projetoController = new ProjetoController(activity);
                projetoController.atualizarStatusUsuarioProjeto(projetoId, "Pendente", pilotoId);

                Toast.makeText(activity, "Proposta Recusada!", Toast.LENGTH_SHORT).show();

                btAceitar.setVisibility(View.GONE);
                btRecusar.setVisibility(View.GONE);
                btMensagem.setVisibility(View.GONE);

            } else if (v.getId() == R.id.btMensagem) {
                int propostaId = (int) v.getTag();
                Intent chat = new Intent(activity, ChatActivity.class);
                chat.putExtra("projetoId", projetoId);
                chat.putExtra("propostaId", propostaId);
                activity.startActivity(chat);

            } else if (v.getId() == R.id.enviadorProposta) {
                int pilotoIdTag = (int) v.getTag(); // Recuperar o ID do piloto da tag
                Intent perfil = new Intent(activity, PerfilActivity.class);
                perfil.putExtra("userType", "piloto");
                perfil.putExtra("userId", pilotoIdTag);
                activity.startActivity(perfil);
            }
        }
    }

    @SuppressLint("Range")
    private void preencherDetalhesDrone(VisualizadorProposta holder, int droneId) {
        DroneController db = new DroneController(activity.getApplicationContext());
        Cursor cursor = db.carregarDadosDrone(droneId);
        if (cursor.moveToFirst()) {
            holder.nomeDrone.setText(cursor.getString(cursor.getColumnIndex("nome")));
            holder.droneTipo.setText(cursor.getString(cursor.getColumnIndex("tipoDrone")));
            holder.droneAutonomia.setText("Autonomia: " + cursor.getString(cursor.getColumnIndex("autonomia")));
            holder.droneAreaCobertura.setText("Área de cobertura: " + cursor.getString(cursor.getColumnIndex("areaCobertura")));
            holder.droneResolucao.setText(cursor.getString(cursor.getColumnIndex("imgQualidade")));

            int imgSobreposicaoInt = cursor.getInt(cursor.getColumnIndex("imgSobreposicao"));
            boolean imgSobreposicao = imgSobreposicaoInt != 0; // Converte para boolean
            if (imgSobreposicao) {
                holder.droneSobreposicao.setText("Sobreposição");
            } else {
                holder.droneSobreposicao.setVisibility(View.GONE);
            }
        }
    }

}
