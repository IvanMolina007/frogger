package com.example.frogger.restart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.frogger.MainActivity;
import com.example.frogger.R;

public class ReiniciarActivity extends AppCompatActivity {

    ImageButton coin;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reiniciar);

        coin = findViewById(R.id.imageButton2);
        name = findViewById(R.id.editTextTextPersonName);

        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty()) {
                    Intent nextActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(nextActivityIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Falta introducir un nombre para jugar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}