package com.example.needdroneapp.ui.piloto;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needdroneapp.R;

import java.util.List;

public class DroneAdapter extends RecyclerView.Adapter<DroneAdapter.DroneViewHolder> {

    private List<Drone> droneList;
    private int selectedItem = -1;

    public DroneAdapter(List<Drone> droneList) {
        this.droneList = droneList;
    }

    @NonNull
    @Override
    public DroneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.droneitem, parent, false);
        return new DroneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DroneViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Drone drone = droneList.get(position);
        //holder.imageViewDroneFoto.setImageBitmap(drone.getFoto());
        holder.textViewNome.setText(drone.getNome());
        holder.textViewAutonomia.setText("Autonomia: até " + drone.getAutonomia() + " minutos.");
        holder.textViewAreaCobertura.setText("Área de cobertura: até " + drone.getAreaCobertura() + "m².");
        holder.textViewdroneSobreposicao.setText(drone.getImgSobreposicao());
        holder.textViewdroneQualidade.setText(drone.getImgQualidade());
        holder.textViewDroneTipo.setText(drone.getTipoDrone());

        if (selectedItem == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FB2E00"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedItem);
            selectedItem = position;
            notifyItemChanged(selectedItem);
        });
    }

    @Override
    public int getItemCount() {
        return droneList.size();
    }

    static class DroneViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewAutonomia, textViewAreaCobertura, textViewDroneTipo, textViewdroneQualidade, textViewdroneSobreposicao;
        ImageView imageViewDroneFoto;
        public DroneViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDroneFoto = itemView.findViewById(R.id.droneFoto);
            textViewNome = itemView.findViewById(R.id.droneNome);
            textViewAutonomia = itemView.findViewById(R.id.droneAutonomia);
            textViewAreaCobertura = itemView.findViewById(R.id.droneAreaCobertura);
            textViewDroneTipo = itemView.findViewById(R.id.droneTipo);
            textViewdroneQualidade = itemView.findViewById(R.id.droneQualidade);
            textViewdroneSobreposicao = itemView.findViewById(R.id.droneSobreposicao);

        }
    }
}