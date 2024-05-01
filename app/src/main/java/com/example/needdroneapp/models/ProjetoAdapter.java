package com.example.needdroneapp.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;
import com.example.needdroneapp.ui.piloto.ProjetoActivity;

import java.util.List;

public class ProjetoAdapter extends RecyclerView.Adapter<ProjetoAdapter.VisualizadorProjeto>{

    private List<Projeto> projetoList;
    private Context context;

    public ProjetoAdapter(List<Projeto> projetoList, Context context){
        this.projetoList = projetoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VisualizadorProjeto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemprojeto, parent, false);
        return new VisualizadorProjeto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisualizadorProjeto holder, int position) {
        Projeto projeto = projetoList.get(position);
        holder.projetoTitulo.setText(projeto.getTitulo());
        holder.projetoDescricao.setText(projeto.getDescricao());
        holder.projetoId = projeto.getProjetoId();
        holder.btnDetalhes.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return projetoList.size();
    }

    class VisualizadorProjeto extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView projetoTitulo, projetoDescricao;
        Button btnDetalhes;
        Integer projetoId;

        public VisualizadorProjeto(@NonNull View itemView) {
            super(itemView);
            projetoTitulo = itemView.findViewById(R.id.projeto_titulo);
            projetoDescricao = itemView.findViewById(R.id.projeto_descricao);
            btnDetalhes = itemView.findViewById(R.id.btnDetalhes);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnDetalhes) {
                Intent detalhesProjeto = new Intent(context, ProjetoActivity.class);
                detalhesProjeto.putExtra("projetoId", projetoId);
                context.startActivity(detalhesProjeto);
            }
        }
    }
}