package com.example.frogger;

public class TiempoThread extends Thread {

    private MainActivity main;
    private GameView view;
    private boolean running = false;

    public TiempoThread(GameView view, MainActivity main) {

        this.view = view;
        this.main = main;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while (running) {
            try {
                view.limit--;
                if (view.limit == 0) {
                    view.muerto = true;
                }
                sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
