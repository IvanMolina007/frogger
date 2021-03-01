package com.example.frogger.restart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.frogger.R;
import com.example.frogger.adapter.LeaderBoardsAdapter;
import com.example.frogger.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Usuario> listaUsuario;
    DatabaseReference baseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        recyclerView = findViewById(R.id.usuarios_recycler);

        listaUsuario = new ArrayList<>();

        baseDate = FirebaseDatabase.getInstance().getReference().child("usuarios");

        baseDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaUsuario.clear();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(new LeaderBoardsAdapter(listaUsuario, getApplicationContext()));
                for (DataSnapshot child: snapshot.getChildren()) {
                    Usuario u = child.getValue(Usuario.class);
                    listaUsuario.add(u);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                    ordenarArray();
                    recyclerView.setAdapter(new LeaderBoardsAdapter(listaUsuario, getApplicationContext()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setupToolBar();
    }

    @SuppressLint("NonConstantResourceId")
    private void setupToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void ordenarArray() {
        int i, j, aux;
        String auxN;
        for (i = 0; i < listaUsuario.size() - 1; i++) {
            for (j = 0; j < listaUsuario.size() - i - 1; j++) {
                if (listaUsuario.get(j + 1).getPuntos() > listaUsuario.get(j).getPuntos()) {
                    aux = listaUsuario.get(j + 1).getPuntos();
                    auxN = listaUsuario.get(j + 1).getNombre();

                    listaUsuario.get(j + 1).setPuntos(listaUsuario.get(j).getPuntos());
                    listaUsuario.get(j + 1).setNombre(listaUsuario.get(j).getNombre());

                    listaUsuario.get(j).setPuntos(aux);
                    listaUsuario.get(j).setNombre(auxN);
                }
            }
        }
    }

}