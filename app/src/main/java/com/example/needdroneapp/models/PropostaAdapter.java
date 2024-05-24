package com.example.needdroneapp.models;

import static android.content.Intent.getIntent;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.needdroneapp.ComentarActivity;
import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.DroneController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.data.ProjetoController;
import com.example.needdroneapp.data.PropostaController;
import com.example.needdroneapp.ui.piloto.ProjetoActivity;
import com.example.needdroneapp.ui.piloto.PropostaPilotoActivity;

import java.util.List;

public class PropostaAdapter extends RecyclerView.Adapter<PropostaAdapter.VisualizadorProposta> {

    private List<Proposta> listaPropostas;
    private Activity activity;
    private Integer droneId;

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

    @SuppressLint({"SetTextI18n", "Range"})
    @Override
    public void onBindViewHolder(@NonNull VisualizadorProposta holder, int position) {
        PilotoController piloto = new PilotoController(activity);
        Proposta proposta = listaPropostas.get(position);

        Cursor cursor = piloto.carregaDadosPorId(proposta.getPilotoId());
        if (cursor.moveToFirst()) {
            holder.enviadorProposta.setText("Piloto: " + cursor.getString(cursor.getColumnIndex("nome")));
        }
        holder.valorProposta.setText("Valor: " + proposta.getOfertaInicial().toString());
        holder.detalhesProposta.setText("Detalhes: " + proposta.getDetalhesProposta());
        float valorFinal = proposta.getOfertaInicial() + proposta.getOfertaInicial() * 0.3f;
        holder.valorPropostaFinal.setText("Valor Final: " + Float.toString(valorFinal));

        Toast.makeText(activity, proposta.getStatus(), Toast.LENGTH_SHORT).show();

        if (proposta.getStatus().equals("Recusada")) {
            holder.statusImage.setImageResource(R.drawable.baseline_access_denied);
            holder.btAceitar.setVisibility(View.GONE);
            holder.btRecusar.setVisibility(View.GONE);
            holder.btMensagem.setVisibility(View.GONE);
        } else if (proposta.getStatus().equals("Pendente")) {
            holder.statusImage.setVisibility(View.GONE);
            holder.btAceitar.setVisibility(View.VISIBLE);
            holder.btRecusar.setVisibility(View.VISIBLE);
            holder.btMensagem.setVisibility(View.VISIBLE);
        } else {
            holder.statusImage.setImageResource(R.drawable.accepted);
            holder.btAceitar.setVisibility(View.GONE);
            holder.btRecusar.setVisibility(View.GONE);
            holder.btMensagem.setVisibility(View.VISIBLE);
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



        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btAceitar){
                Integer projetoId = activity.getIntent().getIntExtra("projetoId", 0);
                PropostaController proposta = new PropostaController(activity);
                String status = proposta.atualizarStatusProposta(projetoId, "Aceita");
                statusImage.setImageResource(R.drawable.accepted);
                Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();
                Toast.makeText(activity, "Status atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                btAceitar.setVisibility(View.GONE);

            } else if (v.getId() == R.id.btRecusar) {
                //Mudar a img, tirar todos os botões
                Integer projetoId = activity.getIntent().getIntExtra("projetoId", 0);
                PropostaController proposta = new PropostaController(activity);
                String status = proposta.atualizarStatusProposta(projetoId, "Recusada");
                statusImage.setImageResource(R.drawable.baseline_access_denied);
                Toast.makeText(activity, "Recusado com sucesso!", Toast.LENGTH_SHORT).show();

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
