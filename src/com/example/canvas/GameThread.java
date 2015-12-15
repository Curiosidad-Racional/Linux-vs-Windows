package com.example.canvas;




public class GameThread extends Thread {


    public ViewDrag vista;

    public GameThread(ViewDrag vista) {
	this.vista = vista;
    }


    boolean running = false;

    void setRunning(boolean b){
	running = b;
    }



    @Override
    public void run() {
	while (running) {
	    try {
		sleep(100);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    if (vista.GameOver())
		running = false;
	    else
		vista.postInvalidate();
	}
    }

}
