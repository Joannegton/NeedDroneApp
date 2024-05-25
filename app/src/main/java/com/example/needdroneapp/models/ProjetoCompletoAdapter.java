package com.example.needdroneapp.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.ui.projeto.ProjetoActivity;

import java.util.List;

public class ProjetoCompletoAdapter extends RecyclerView.Adapter<ProjetoCompletoAdapter.VisualizadorProjeto> {

    private List<Projeto> projetoList;
    private Context context;
    public ProjetoCompletoAdapter(List<Projeto> projetoList, Context context) {
        this.projetoList = projetoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VisualizadorProjeto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemprojetocompleto, parent, false);
        return new VisualizadorProjeto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisualizadorProjeto holder, int position) {
        Projeto projeto = projetoList.get(position);
        holder.projetoId = projeto.getProjetoId();
        Integer clienteId = projeto.clienteId;

        holder.tvTitulo.setText(projeto.getTitulo());
        holder.tvDescricao.setText(projeto.getDescricao());
        holder.tvData.setText(projeto.getDataEvento());

        ClienteController clienteController = new ClienteController(context.getApplicationContext());
        String nomeCliente = clienteController.pegarNomePorId(clienteId);
        holder.tvCliente.setText(nomeCliente + ": ");

        Float media = clienteController.pegarAvaliacaoPorId(clienteId);
        holder.ratingBar.setRating(media);

        holder.tvTitulo.setOnClickListener(holder);
    }


    @Override
    public int getItemCount() {
        return projetoList.size();
    }

    class VisualizadorProjeto extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitulo, tvDescricao, tvCliente, tvData;
        RatingBar ratingBar;
        Integer projetoId;

        public VisualizadorProjeto(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvData = itemView.findViewById(R.id.tvData);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvTitulo){
                Intent intent = new Intent(context, ProjetoActivity.class);
                intent.putExtra("projetoId", projetoId);
                context.startActivity(intent);
            }
        }
    }
}
