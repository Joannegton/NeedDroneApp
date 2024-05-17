package com.example.needdroneapp.models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;
import com.example.needdroneapp.ui.edicao.EditDroneActivity;

import java.util.List;
import java.util.Objects;

public class DroneAdapter extends RecyclerView.Adapter<DroneAdapter.DroneViewHolder> {

    private List<Drone> droneList;
    private boolean proposta;
    private int selectedItem = -1;

    private RetornoDroneSelecionado selecao;
    public DroneAdapter(List<Drone> droneList, boolean proposta) {
        this.droneList = droneList;
        this.proposta = proposta;
    }

    public interface RetornoDroneSelecionado {
        void droneSelecionado(int idDrone);
    }

    public void setSelecao(RetornoDroneSelecionado selecao) {
        this.selecao = selecao;
    }

    @NonNull
    @Override
    public DroneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdrone, parent, false);
        return new DroneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DroneViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Drone drone = droneList.get(position);
        //holder.imageViewDroneFoto.setImageBitmap(drone.getFoto());
        holder.textViewNome.setText(drone.getNome());
        holder.textViewAutonomia.setText("Autonomia: " + drone.getAutonomia() + ".");
        holder.textViewAreaCobertura.setText("Área de cobertura: " + drone.getAreaCobertura() + ".");
        holder.textViewStatus.setText(drone.getStatus());

        if (Objects.equals(drone.getStatus(), "Ativo")) {
            ViewCompat.setBackgroundTintList(holder.textViewStatus, ColorStateList.valueOf(ContextCompat.getColor(holder.textViewStatus.getContext(), R.color.green)));
        } else {
            ViewCompat.setBackgroundTintList(holder.textViewStatus, ColorStateList.valueOf(ContextCompat.getColor(holder.textViewStatus.getContext(), R.color.yellow)));
        }

        holder.textViewStatus.setText(drone.getStatus());

        boolean imgSobreposicao = drone.getImgSobreposicao();
        if (imgSobreposicao) {
            holder.textViewdroneSobreposicao.setText("Sobreposição");
        } else {
            holder.textViewdroneSobreposicao.setVisibility(View.GONE);
            holder.viewSobreposicao.setVisibility(View.GONE);
        }

        holder.textViewdroneQualidade.setText(drone.getImgQualidade());
        holder.textViewDroneTipo.setText(drone.getTipoDrone());

        holder.droneId = drone.getDroneId();

        if (proposta){
            if (selectedItem == position) {
                holder.itemView.setBackgroundColor(Color.parseColor("#dcdcdc"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            holder.itemView.setOnClickListener(v -> {
                notifyItemChanged(selectedItem);
                selectedItem = position;
                notifyItemChanged(selectedItem);
                if (selecao != null) {
                    selecao.droneSelecionado(drone.getDroneId());
                }
            });
        }else {
            holder.editarDrone.setOnClickListener(holder);
            if (!imgSobreposicao) {
                holder.textViewdroneSobreposicao.setVisibility(View.GONE);
            } else {
                holder.textViewdroneSobreposicao.setText("Sobreposição");
            }
        }

    }

    @Override
    public int getItemCount() {
        return droneList.size();
    }

    static class DroneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewNome, textViewAutonomia, textViewAreaCobertura, textViewDroneTipo, textViewdroneQualidade, textViewdroneSobreposicao, textViewStatus;
        ImageView imageViewDroneFoto;
        View viewSobreposicao;

        int droneId;

        Button editarDrone;
        public DroneViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDroneFoto = itemView.findViewById(R.id.droneFoto);
            textViewNome = itemView.findViewById(R.id.droneNome);
            textViewAutonomia = itemView.findViewById(R.id.droneAutonomia);
            textViewAreaCobertura = itemView.findViewById(R.id.droneAreaCobertura);
            textViewDroneTipo = itemView.findViewById(R.id.droneTipo);
            textViewdroneQualidade = itemView.findViewById(R.id.droneQualidade);
            textViewdroneSobreposicao = itemView.findViewById(R.id.droneSobreposicao);
            editarDrone = itemView.findViewById(R.id.btEditarDrone);
            viewSobreposicao = itemView.findViewById(R.id.viewSobreposicao);
            textViewStatus = itemView.findViewById(R.id.tvStatus);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btEditarDrone) {
                Intent intent = new Intent(v.getContext(), EditDroneActivity.class);
                intent.putExtra("idDrone", droneId);
                v.getContext().startActivity(intent);
            }
        }
    }
}