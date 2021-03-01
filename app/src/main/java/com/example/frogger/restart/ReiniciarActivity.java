package com.example.frogger.restart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.frogger.MainActivity;
import com.example.frogger.R;
import com.example.frogger.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReiniciarActivity extends AppCompatActivity {

    ImageButton coin;
    Button leader;
    EditText name;
    DatabaseReference database;
    public static  String nombreJugador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reiniciar);

        coin = findViewById(R.id.imageButton2);
        leader = findViewById(R.id.button);
        name = findViewById(R.id.editTextTextPersonName);

        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty()) {
                    Usuario u = new Usuario(name.getText().toString());
                    nombreJugador = u.getNombre();
                    database = FirebaseDatabase.getInstance().getReference().child("usuarios");
                    database.push().setValue(u);
                    Intent nextActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(nextActivityIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Falta introducir un nombre para jugar", Toast.LENGTH_LONG).show();
                }
            }
        });

        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivityIntent = new Intent(getApplicationContext(), LeaderBoardActivity.class);
                startActivity(nextActivityIntent);
            }
        });
    }
}