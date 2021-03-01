package com.example.frogger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.frogger.model.Usuario;
import com.example.frogger.restart.ReiniciarActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameView extends SurfaceView {

    public MainActivity main;
    public boolean muerto = false, borrado = false, musica = false, atropellado = false;
    private MediaPlayer media;
    public SoundPool sp = new SoundPool(9, AudioManager.STREAM_MUSIC, 0);
    int tamanoX, tamanoY, posicionRanaX, posicionRanaY;
    public int ahogado, atropello, tiempo, salto, puntuar, passLevel;
    public int vidas = 2, troncoX1, troncoX2, troncoX3,
            vehiculoX1, vehiculoX2, vehiculoX3, aleatorio,
            troncoY1, troncoY2, troncoY3, vehiculoY1, vehiculoY2, vehiculoY3, dificultad = 1;
    boolean nenufar1 = true, nenufar2 = true, nenufar3 = true, nenufar4 = true, nenufar5 = true, volver = true;
    public GameLoopThread gameLoopThread;
    DatabaseReference database;
    private SurfaceHolder holder;
    public int puntos = 0;
    private Bitmap agua, cesped, carretera, muerte;
    public Bitmap nenufar, rana, troncoB1, troncoB2, troncoB3, troncoLargo, troncoCorto, troncoMedio,
            vehiculoB1, vehiculoB2, vehiculoB3, camion, fragoneta, coche, camionIz, fragonetaIz, cocheIz, ranaFin;

    public long limit = 41;

    TiempoThread tiempoThread;
    TroncosVehiculos tronco1 = new TroncosVehiculos(this, 1);
    TroncosVehiculos tronco2 = new TroncosVehiculos(this, 2);
    TroncosVehiculos tronco3 = new TroncosVehiculos(this, 3);
    TroncosVehiculos vehiculo1 = new TroncosVehiculos(this, 4);
    TroncosVehiculos vehiculo2 = new TroncosVehiculos(this, 5);
    TroncosVehiculos vehiculo3 = new TroncosVehiculos(this, 6);

    boolean pasada = true;

    public GameView(Context context, Point pantalla, final MainActivity main) {
        super(context);

        media = MediaPlayer.create(getContext(), R.raw.frogger);
        media.setVolume(1, 1);

        gameLoopThread = new GameLoopThread(this);
        this.main = main;
        tiempoThread = new TiempoThread(this, main);

        agua = BitmapFactory.decodeResource(getResources(), R.drawable.aguajuego);
        cesped = BitmapFactory.decodeResource(getResources(), R.drawable.cespedjuego);
        carretera = BitmapFactory.decodeResource(getResources(), R.drawable.carreterajuego);
        nenufar = BitmapFactory.decodeResource(getResources(), R.drawable.nenufar);
        muerte = BitmapFactory.decodeResource(getResources(), R.drawable.muerte);
        ranaFin = BitmapFactory.decodeResource(getResources(), R.drawable.ranaunodown);

        troncoLargo = BitmapFactory.decodeResource(getResources(), R.drawable.troncolargo);
        troncoCorto = BitmapFactory.decodeResource(getResources(), R.drawable.troncopequenyo);
        troncoMedio = BitmapFactory.decodeResource(getResources(), R.drawable.troncomedio);

        camion = BitmapFactory.decodeResource(getResources(), R.drawable.camion);
        fragoneta = BitmapFactory.decodeResource(getResources(), R.drawable.fragoneta);
        coche = BitmapFactory.decodeResource(getResources(), R.drawable.coche);

        camionIz = BitmapFactory.decodeResource(getResources(), R.drawable.camioniz);
        fragonetaIz = BitmapFactory.decodeResource(getResources(), R.drawable.fragonetaiz);
        cocheIz = BitmapFactory.decodeResource(getResources(), R.drawable.cocheiz);

        holder = getHolder();

        tamanoX = pantalla.x;
        tamanoY = pantalla.y;

        if (pasada) {

            //Troncos
            do {
                aleatorio = (int) (Math.random() * 3);
            } while (aleatorio == 3);


            switch (aleatorio) {
                case 0:
                    troncoB1 = troncoCorto;
                    break;
                case 1:
                    troncoB1 = troncoMedio;
                    break;
                case 2:
                    troncoB1 = troncoLargo;
            }

            do {
                aleatorio = (int) (Math.random() * 3);
            } while (aleatorio == 3);

            switch (aleatorio) {
                case 0:
                    troncoB2 = troncoCorto;
                    break;
                case 1:
                    troncoB2 = troncoMedio;
                    break;
                case 2:
                    troncoB2 = troncoLargo;
            }

            do {
                aleatorio = (int) (Math.random() * 3);
            } while (aleatorio == 3);

            switch (aleatorio) {
                case 0:
                    troncoB3 = troncoCorto;
                    break;
                case 1:
                    troncoB3 = troncoMedio;
                    break;
                case 2:
                    troncoB3 = troncoLargo;
            }

            //Coches

            do {
                aleatorio = (int) (Math.random() * 3);
            } while (aleatorio == 3);

            switch (aleatorio) {
                case 0:
                    vehiculoB1 = camion;
                    break;
                case 1:
                    vehiculoB1 = fragoneta;
                    break;
                case 2:
                    vehiculoB1 = coche;
            }

            do {
                aleatorio = (int) (Math.random() * 3);
            } while (aleatorio == 3);

            switch (aleatorio) {
                case 0:
                    vehiculoB2 = camionIz;
                    break;
                case 1:
                    vehiculoB2 = fragonetaIz;
                    break;
                case 2:
                    vehiculoB2 = cocheIz;
            }

            do {
                aleatorio = (int) (Math.random() * 3);
            } while (aleatorio == 3);

            switch (aleatorio) {
                case 0:
                    vehiculoB3 = camion;
                    break;
                case 1:
                    vehiculoB3 = fragoneta;
                    break;
                case 2:
                    vehiculoB3 = coche;
            }
        }
        pasada = false;

        troncoX1 = tamanoX;
        troncoX2 = -troncoB2.getWidth();
        troncoX3 = tamanoX;

        vehiculoX1 = -vehiculoB1.getWidth();
        vehiculoX2 = tamanoX;
        vehiculoX3 = -vehiculoB3.getWidth();


        direccionRana(3);

        posicionRanaX = tamanoX / 2 - rana.getWidth() / 2;
        posicionRanaY = (tamanoY * 9) / 10;

        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

                media.pause();
                if (volver) {
                    Intent nextActivityIntent = new Intent(main.getApplicationContext(), ReiniciarActivity.class);
                    main.startActivity(nextActivityIntent);
                    main.finish();
                }
            }

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {


                tiempoThread.setRunning(true);
                tiempoThread.start();

                tronco1.setRunning(true);
                tronco1.start();
                tronco2.setRunning(true);
                tronco2.start();
                tronco3.setRunning(true);
                tronco3.start();

                vehiculo1.setRunning(true);
                vehiculo1.start();
                vehiculo2.setRunning(true);
                vehiculo2.start();
                vehiculo3.setRunning(true);
                vehiculo3.start();

                gameLoopThread.setRunning(true);
                gameLoopThread.start();

                ahogado = sp.load(getContext(), R.raw.aguasonido, 0);
                salto = sp.load(getContext(), R.raw.salto, 0);
                puntuar = sp.load(getContext(), R.raw.rananenufar, 0);
                atropello = sp.load(getContext(), R.raw.claxon, 0);
                tiempo = sp.load(getContext(), R.raw.muertesound, 0);
                passLevel = sp.load(getContext(), R.raw.megavictoria, 0);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!musica) {
            media.start();
            media.setLooping(true);
            musica = true;
        }
        if (canvas != null) {
            dibujarCampo(canvas);

            if (posicionRanaX < 0) {
                posicionRanaX = 0;
            } else {
                if (posicionRanaX + rana.getWidth() > tamanoX) {
                    posicionRanaX = tamanoX - rana.getWidth();
                }
            }

            //Morir en agua cuando hay tronco
            if (posicionRanaY > 0 && posicionRanaY < nenufar.getHeight() * 3) {
                if ((posicionRanaX + rana.getWidth() >= troncoX1 &&
                        posicionRanaX <= troncoX1 + troncoB1.getWidth() &&
                        posicionRanaY >= troncoY1 &&
                        posicionRanaY <= troncoY1 + troncoB1.getHeight()) || (posicionRanaX + rana.getWidth() >= troncoX3 &&
                        posicionRanaX <= troncoX3 + troncoB3.getWidth() &&
                        posicionRanaY >= troncoY3 &&
                        posicionRanaY <= troncoY3 + troncoB3.getHeight())) {

                    posicionRanaX = (int)(posicionRanaX - dificultad * 7.5);

                } else if ((posicionRanaX + rana.getWidth() >= troncoX2 &&
                        posicionRanaX <= troncoX2 + troncoB2.getWidth() &&
                        posicionRanaY >= troncoY2 &&
                        posicionRanaY <= troncoY2 + troncoB2.getHeight()) ){

                    posicionRanaX = (int)(posicionRanaX + dificultad * 7.5);

                } else {
                    sp.play(ahogado, 1,  1, 1, 0, 1);
                    pierdeVida(canvas);
                }
            } else {
                if (muerto) {
                    muerto = false;
                    pierdeVida(canvas);
                    if (atropellado) {
                        atropellado = false;
                        sp.play(atropello, 1,  1, 1, 0, 1);
                    }
                }
            }
            //Llegas a los nenufares y comprueba si ya llegaste

            if (!nenufar1) {
                canvas.drawBitmap(ranaFin, 30, 70, null);
            }

            nenufares(canvas, 1);

            if (!nenufar2) {
                canvas.drawBitmap(ranaFin, nenufar.getWidth() + 30, 70, null);
            }

            nenufares(canvas, 2);

            if (!nenufar3) {
                canvas.drawBitmap(ranaFin, nenufar.getWidth() * 2 + 30, 70, null);
            }
            nenufares(canvas, 3);

            if (!nenufar4) {
                canvas.drawBitmap(ranaFin, nenufar.getWidth() * 3 + 30, 70, null);
            }

            nenufares(canvas, 4);

            if (!nenufar5) {
                canvas.drawBitmap(ranaFin, nenufar.getWidth() * 4 + 30, 70, null);
            }

            nenufares(canvas, 5);

            if (!nenufar1 && !nenufar2 && !nenufar3 && !nenufar4 && !nenufar5) {
                nenufar1 = true;
                nenufar2 = true;
                nenufar3 = true;
                nenufar4 = true;
                nenufar5 = true;
                vidas++;
                dificultad++;
                sp.play(passLevel, 1,  1, 1, 0, 1);
                puntos = puntos + 5;
            }


            canvas.drawBitmap(rana, posicionRanaX, posicionRanaY + 20, null);
            temporizador(canvas);
        }

    }

    public void setX(float x) {
        if ((int) Math.floor(x) != 0) {
            if (((int) Math.floor(x) > 0)) {
                direccionRana(2);
            } else {
                direccionRana(3);
            }
            this.posicionRanaX = posicionRanaX - (int) Math.floor(x) * 10;
        }
    }

    public void setY(boolean arriba, int cont) {
        if (arriba) {
            direccionRana(1);
        } else {
            direccionRana(4);
        }
        posicionRanaY = (tamanoY * cont) / 10;
    }

    public void direccionRana(int direccion) {
        switch (direccion) {
            case 1:
                rana = BitmapFactory.decodeResource(getResources(), R.drawable.ranaunoup);
                break;
            case 2:
                rana = BitmapFactory.decodeResource(getResources(), R.drawable.ranaleft);
                break;
            case 3:
                rana = BitmapFactory.decodeResource(getResources(), R.drawable.ranaright);
                break;
            case 4:
                rana = BitmapFactory.decodeResource(getResources(), R.drawable.ranaunodown);
                break;
        }
    }

    public void dibujarCampo(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int auxX = cesped.getWidth(), dibujoX = 0, auxY = cesped.getHeight(), dibujoY = 0, medidor = 0,
                auxCAX = carretera.getWidth();

        for (int i = 1; i < 13; ++i) {
            for (int j = 1; j < 13; ++j) {

                if (medidor == 4 || medidor == 5 || medidor >= 9) {
                    canvas.drawBitmap(cesped, dibujoX, dibujoY, null);
                    dibujoX = dibujoX + auxX;
                } else {
                    if (medidor > 0 && medidor < 4) {
                        canvas.drawBitmap(agua, dibujoX, dibujoY, null);
                        movimientoObstaculos(medidor, dibujoY, canvas);
                        dibujoX = dibujoX + auxX;
                    } else {
                        if (medidor == 0) {
                            //NENUFAR
                            canvas.drawBitmap(nenufar, dibujoX, dibujoY, null);
                            dibujoX = dibujoX + auxX;
                        } else {
                            canvas.drawBitmap(carretera, dibujoX, dibujoY, null);
                            movimientoObstaculos(medidor, dibujoY, canvas);
                            dibujoX = dibujoX + auxCAX;
                        }
                    }
                }
            }
            dibujoX = 0;
            dibujoY = dibujoY + auxY;
            medidor++;
        }
    }

    public void temporizador(Canvas canvas) {
        TextView text = new TextView(getContext());
        text.setText("Vidas " + vidas + " - Tiempo " + limit + " - Puntos " + puntos);
        text.setTextSize(15);

        Paint paintText = text.getPaint();

        Rect boundsText = new Rect();


        paintText.getTextBounds(text.getText().toString(), 0, text.length(), boundsText);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(Color.BLACK);


        canvas.drawText(text.getText().toString(), getWidth() / 2, getHeight() - boundsText.height() / 2, paintText);
    }

    public void movimientoObstaculos(int medidor, int dibujoY, Canvas canvas) {

        switch (medidor) {
            case 1:
                troncoY1 = dibujoY;
                canvas.drawBitmap(troncoB1, troncoX1, troncoY1, null);
                break;

            case 2:
                troncoY2 = dibujoY;
                canvas.drawBitmap(troncoB2, troncoX2, troncoY2, null);
                break;

            case 3:
                troncoY3 = dibujoY;
                canvas.drawBitmap(troncoB3, troncoX3, troncoY3, null);
                break;
            case 6:
                vehiculoY1 = dibujoY;
                canvas.drawBitmap(vehiculoB1, vehiculoX1, vehiculoY1, null);
                break;
            case 7:
                vehiculoY2 = dibujoY;
                canvas.drawBitmap(vehiculoB2, vehiculoX2, vehiculoY2, null);
                break;
            case 8:
                vehiculoY3 = dibujoY;
                canvas.drawBitmap(vehiculoB3, vehiculoX3, vehiculoY3, null);
                break;

        }
    }

    public void gameOver() {
        limit = 0;
        vidas = 0;
        volver = false;
        borrado = false;
        Intent nextActivityIntent = new Intent(main.getApplicationContext(), ReiniciarActivity.class);
        gameLoopThread.setRunning(false);
        tiempoThread.setRunning(false);
        //Subos
        database = FirebaseDatabase.getInstance().getReference().child("usuarios");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!borrado) {
                    for (DataSnapshot child: snapshot.getChildren()) {
                        if (child.child("nombre").getValue().toString().equals(ReiniciarActivity.nombreJugador)) {
                            Usuario newU = child.getValue(Usuario.class);
                            newU.setPuntos(puntos);
                            database = FirebaseDatabase.getInstance().getReference().child("usuarios").child(child.getKey());
                            borrado = true;
                            database.removeValue();
                            database = FirebaseDatabase.getInstance().getReference().child("usuarios").child(child.getKey());
                            database.setValue(newU);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        main.startActivity(nextActivityIntent);
        main.finish();
    }

    public void pierdeVida(Canvas canvas) {
        vidas--;
        int posicionMuerteX = posicionRanaX, posicionMuerteY = posicionRanaY;
        posicionRanaX = tamanoX / 2;
        posicionRanaY = (tamanoY * 9) / 10;
        main.cont = 9;
        canvas.drawBitmap(muerte, posicionMuerteX, posicionMuerteY, null);
        if (vidas >= 0) {
            limit = 41;
        } else {
            tiempoThread.setRunning(false);
            gameOver();
        }
    }

    public void nenufares(Canvas canvas, int numeroNenufar) {
        if (posicionRanaY > -20 && posicionRanaY < nenufar.getHeight() - 20) {
            switch (numeroNenufar) {
                case 1:

                    if (posicionRanaX > 0 && posicionRanaX < nenufar.getWidth()) {
                        if (nenufar1) {
                            posicionRanaX = tamanoX / 2;
                            posicionRanaY = (tamanoY * 9) / 10;
                            main.cont = 9;
                            limit = limit + 21;
                            nenufar1 = false;
                            puntos = puntos + 1;
                            sp.play(puntuar, 1,  1, 1, 0, 1);
                        } else {
                            sp.play(tiempo, 1,  1, 1, 0, 1);
                            pierdeVida(canvas);
                        }
                    }

                    break;
                case 2:

                    if (posicionRanaX > nenufar.getWidth() && posicionRanaX < nenufar.getWidth()*2) {
                        if (nenufar2) {
                            posicionRanaX = tamanoX / 2;
                            posicionRanaY = (tamanoY * 9) / 10;
                            main.cont = 9;
                            limit = limit + 21;
                            nenufar2 = false;
                            puntos = puntos + 1;
                            sp.play(puntuar, 1,  1, 1, 0, 1);
                        } else {
                            sp.play(tiempo, 1,  1, 1, 0, 1);
                            pierdeVida(canvas);
                        }
                    }

                    break;
                case 3:

                    if (posicionRanaX > nenufar.getWidth()*2 && posicionRanaX < nenufar.getWidth()*3) {
                        if (nenufar3) {
                            posicionRanaX = tamanoX / 2;
                            posicionRanaY = (tamanoY * 9) / 10;
                            main.cont = 9;
                            limit = limit + 21;
                            nenufar3 = false;
                            puntos = puntos + 1;
                            sp.play(puntuar, 1,  1, 1, 0, 1);
                        } else {
                            sp.play(tiempo, 1,  1, 1, 0, 1);
                            pierdeVida(canvas);
                        }
                    }
                    break;
                case 4:

                    if (posicionRanaX > nenufar.getWidth()*3 && posicionRanaX < nenufar.getWidth()*4) {
                        if (nenufar4) {
                            posicionRanaX = tamanoX / 2;
                            posicionRanaY = (tamanoY * 9) / 10;
                            main.cont = 9;
                            limit = limit + 21;
                            nenufar4 = false;
                            puntos = puntos + 1;
                            sp.play(puntuar, 1,  1, 1, 0, 1);
                        } else {
                            sp.play(tiempo, 1,  1, 1, 0, 1);
                            pierdeVida(canvas);
                        }
                    }
                    break;
                case 5:

                    if (posicionRanaX > nenufar.getWidth()*4 && posicionRanaX < nenufar.getWidth()*5) {
                        if (nenufar5) {
                            posicionRanaX = tamanoX / 2;
                            posicionRanaY = (tamanoY * 9) / 10;
                            main.cont = 9;
                            limit = limit + 21;
                            nenufar5 = false;
                            puntos = puntos + 1;
                            sp.play(puntuar, 1,  1, 1, 0, 1);
                        } else {
                            sp.play(tiempo, 1,  1, 1, 0, 1);
                            pierdeVida(canvas);
                        }
                    }
                    break;
            }
        }
    }
}
