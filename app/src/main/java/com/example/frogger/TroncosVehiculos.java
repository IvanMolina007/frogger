package com.example.frogger;

public class TroncosVehiculos extends Thread {

    static final long FPS = 120;
    private GameView view;
    private boolean running = false;
    private int troncoXDependencia;

    public TroncosVehiculos(GameView view, int troncoXDependencia) {
        this.view = view;
        this.troncoXDependencia = troncoXDependencia;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        int cuandoSale;
        int aleatoriolo;


        cuandoSale = (int) (Math.random() * 3);

        //Cuando saldra la primera vez
        try {
            sleep(cuandoSale * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (running) {
            startTime = System.currentTimeMillis();
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                switch (troncoXDependencia) {
                    case 1:
                        view.troncoX1 = view.troncoX1 - view.dificultad;

                        if (view.troncoX1 + view.troncoB1.getWidth() <= 0) {
                            do{
                                aleatoriolo = (int) (Math.random() * 3);
                            } while (aleatoriolo == 3);

                            switch (aleatoriolo) {
                                case 0:
                                    view.troncoB1 = view.troncoCorto;
                                    break;
                                case 1:
                                    view.troncoB1 = view.troncoMedio;
                                    break;
                                case 2:
                                    view.troncoB1 = view.troncoLargo;
                            }

                            view.troncoX1 = view.tamanoX;
                        }
                        break;
                    case 2:
                        view.troncoX2 = view.troncoX2 + view.dificultad;

                        if (view.troncoX2 >= view.tamanoX) {

                            do{
                                aleatoriolo = (int) (Math.random() * 3);
                            } while (aleatoriolo == 3);

                            switch (aleatoriolo) {
                                case 0:
                                    view.troncoB2 = view.troncoCorto;
                                    break;
                                case 1:
                                    view.troncoB2 = view.troncoMedio;
                                    break;
                                case 2:
                                    view.troncoB2 = view.troncoLargo;
                            }

                            view.troncoX2 = -view.troncoB2.getWidth();

                        }
                        break;
                    case 3:
                        view.troncoX3 = view.troncoX3 - view.dificultad;

                        if (view.troncoX3 + view.troncoB3.getWidth() <= 0) {

                            do{
                                aleatoriolo = (int) (Math.random() * 3);
                            } while (aleatoriolo == 3);

                            switch (aleatoriolo) {
                                case 0:
                                    view.troncoB3 = view.troncoCorto;
                                    break;
                                case 1:
                                    view.troncoB3 = view.troncoMedio;
                                    break;
                                case 2:
                                    view.troncoB3 = view.troncoLargo;
                            }
                            view.troncoX3 = view.tamanoX;
                        }
                        break;
                    case 4:
                        view.vehiculoX1 = view.vehiculoX1 + view.dificultad;

                        if (view.posicionRanaX + view.rana.getWidth() >= view.vehiculoX1 &&
                                view.posicionRanaX <= view.vehiculoX1 + view.vehiculoB1.getWidth() &&
                                view.posicionRanaY >= view.vehiculoY1 &&
                                view.posicionRanaY <= view.vehiculoY1 + view.vehiculoB1.getHeight()) {
                            view.muerto = true;
                            view.atropellado = true;
                        }

                        if (view.vehiculoX1 >= view.tamanoX) {

                            do{
                                aleatoriolo = (int) (Math.random() * 3);
                            } while (aleatoriolo == 3);

                            switch (aleatoriolo) {
                                case 0:
                                    view.vehiculoB1 = view.camion;
                                    break;
                                case 1:
                                    view.vehiculoB1 = view.fragoneta;
                                    break;
                                case 2:
                                    view.vehiculoB1= view.coche;
                            }

                            view.vehiculoX1 = -view.vehiculoB1.getWidth();

                        }
                        break;
                    case 5:
                        view.vehiculoX2 = view.vehiculoX2 - view.dificultad;

                        if (view.posicionRanaX + view.rana.getWidth() >= view.vehiculoX2 &&
                                view.posicionRanaX <= view.vehiculoX2 + view.vehiculoB2.getWidth() &&
                                view.posicionRanaY >= view.vehiculoY2 &&
                                view.posicionRanaY <= view.vehiculoY2 + view.vehiculoB2.getHeight()) {
                            view.muerto = true;
                            view.atropellado = true;
                        }

                        if (view.vehiculoX2 + view.vehiculoB2.getWidth() <= 0) {

                            do{
                                aleatoriolo = (int) (Math.random() * 3);
                            } while (aleatoriolo == 3);

                            switch (aleatoriolo) {
                                case 0:
                                    view.vehiculoB2 = view.camionIz;
                                    break;
                                case 1:
                                    view.vehiculoB2 = view.fragonetaIz;
                                    break;
                                case 2:
                                    view.vehiculoB2 = view.cocheIz;
                            }
                            view.vehiculoX2 = view.tamanoX;
                        }
                        break;
                    case 6:
                        view.vehiculoX3 = view.vehiculoX3 + view.dificultad;

                        if (view.posicionRanaX + view.rana.getWidth() >= view.vehiculoX3 &&
                                view.posicionRanaX <= view.vehiculoX3 + view.vehiculoB3.getWidth() &&
                                view.posicionRanaY >= view.vehiculoY3 &&
                                view.posicionRanaY <= view.vehiculoY3 + view.vehiculoB3.getHeight()) {
                            view.muerto = true;
                            view.atropellado = true;
                        }

                        if (view.vehiculoX3 >= view.tamanoX) {

                            do{
                                aleatoriolo = (int) (Math.random() * 3);
                            } while (aleatoriolo == 3);

                            switch (aleatoriolo) {
                                case 0:
                                    view.vehiculoB3 = view.camion;
                                    break;
                                case 1:
                                    view.vehiculoB3 = view.fragoneta;
                                    break;
                                case 2:
                                    view.vehiculoB3= view.coche;
                            }

                            view.vehiculoX3 = -view.vehiculoB3.getWidth();

                        }
                        break;
                }

                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }
        }
    }
}
