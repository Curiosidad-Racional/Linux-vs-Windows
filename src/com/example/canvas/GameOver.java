package com.example.canvas;

import android.app.Activity;
import android.os.Bundle;

public class GameOver extends Activity
{

    ViewDrag vista;
    GameThread mainThread;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
    }

}
