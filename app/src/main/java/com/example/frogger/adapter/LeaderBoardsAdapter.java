package com.example.frogger.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frogger.R;
import com.example.frogger.model.Usuario;

import java.util.ArrayList;

public class LeaderBoardsAdapter extends RecyclerView.Adapter<LeaderBoardsAdapter.LeardBoardViewHolder>{

    Context context;
    ArrayList<Usuario> listaUsuarios;

    public LeaderBoardsAdapter(ArrayList<Usuario> listaComercios, Context context) {
        this.listaUsuarios= listaComercios;
        this.context = context;
    }

    @NonNull
    @Override
    public LeardBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.usuario_row, parent, false);
        return new LeardBoardViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LeardBoardViewHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);
        holder.posicion.setText(Integer.toString(position+1));
        holder.nombre.setText(usuario.getNombre());
        holder.puntos.setText(Integer.toString(usuario.getPuntos()));

    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class LeardBoardViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView puntos;
        TextView posicion;

        public LeardBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewNombre);
            puntos = itemView.findViewById(R.id.textViewPuntuacion);
            posicion = itemView.findViewById(R.id.textViewNumero);
        }
    }
}