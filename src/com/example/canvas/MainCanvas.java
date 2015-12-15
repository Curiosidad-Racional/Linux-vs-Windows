package com.example.canvas;

import android.app.Activity;
import android.os.Bundle;

// Importaciones añadidas
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;

public class MainCanvas extends Activity
{

    ViewDrag vista;
    GameThread mainThread;
    Intent gameOver;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	vista = new ViewDrag(this);
        setContentView(vista);

	// Pantallas
	gameOver = new Intent(MainCanvas.this, GameOver.class);
	gameOver.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Elimina toda actividad relacionada con la nueva
    }



    @Override
    protected void onStart() {
	super.onStart();
	mainThread = new GameThread(vista);
	mainThread.setRunning(true);
	mainThread.start();
    }

    @Override
    protected void onStop() {
	super.onStop();
	mainThread.setRunning(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.options_menu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
        case R.id.uno:
            vista.setMode(1);
            return true;
        case R.id.dos:
            vista.setMode(2);
            return true;
        default:
            return super.onOptionsItemSelected(item);
	}
    }


    public void toGameOver() {
	startActivity(gameOver);
	finish();
    }
}
