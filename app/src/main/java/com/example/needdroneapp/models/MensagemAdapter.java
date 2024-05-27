package com.example.needdroneapp.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;

import java.util.List;

public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.MensagemViewHolder> {

    private final List<Mensagem> mensagemList;
    private final Integer clienteId;
    private final String userType;

    public MensagemAdapter(List<Mensagem> mensagemList, Integer clienteId, String userType) {
        this.mensagemList = mensagemList;
        this.clienteId = clienteId;
        this.userType = userType;
    }

    @Override
    public MensagemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mensagem, parent, false);

        return new MensagemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MensagemViewHolder holder, int position) {
        Mensagem mensagem = mensagemList.get(position);
        holder.texto.setText(mensagem.getTexto());
        holder.dataHora.setText(mensagem.getDataHora());

        if (mensagem.getRemetenteId().equals(clienteId)) {
            // Se a mensagem é do cliente, coloque-a à direita se o usuário logado for o cliente, caso contrário, à esquerda
            if (userType.equals("cliente")) {
                holder.texto.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.chat_bubble_right));
            } else {
                holder.texto.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.chat_bubble_left));
            }
        } else {
            // Se a mensagem é do piloto, coloque-a à direita se o usuário logado for o piloto, caso contrário, à esquerda
            if (userType.equals("piloto")) {
                holder.texto.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.chat_bubble_right));
            } else {
                holder.texto.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.chat_bubble_left));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }

    public static class MensagemViewHolder extends RecyclerView.ViewHolder {
        public TextView texto;
        public TextView dataHora;

        public MensagemViewHolder(View view) {
            super(view);
            texto = view.findViewById(R.id.texto);
            dataHora = view.findViewById(R.id.dataHora);
        }
    }
}
