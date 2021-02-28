package com.example.frogger;

public class TiempoThread extends Thread {

    private  MainActivity main;
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
        while(running) {
            try {
                view.limit--;
                if (view.limit == 0) {
                  view.vidas--;
                  view.posicionRanaX = view.tamanoX/2;
                  view.posicionRanaY = (view.tamanoY * 9) / 10;
                  main.cont = 9;
                  if (view.vidas >= 0) {
                      view.limit = 40;
                  } else {
                      view.gameOver();
                  }
                }
                sleep(1000);
            } catch(Exception e) {}
        }
    }
}
