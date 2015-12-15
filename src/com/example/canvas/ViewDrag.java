package com.example.canvas;


// Importaciones añadidas
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;

public class ViewDrag extends View {


    public static final int UNO = 1;
    public static final int DOS = 2;
    private static int modo = 1;

    private float maxWidth, maxHeight;
    private int frame;

    // Imágenes.
    private Bitmap imageShip, imageBullet, imageWin, backGround;
    private ArrayList<Bitmap> al_fire, al_usb;
    private Paint paint;

    // Pantallas.
    private Context context;
    private boolean gameOver = false;
    private int level = 1;

    // Diferencia entre cursor y posición nave.
    private float dspaceShipPosX = 0, dspaceShipPosY = 0;

    // Objetos espaciales
    private Sprite spaceShip;
    private ArrayList<Sprite> invaders;

    public ViewDrag(Context context) {
	super(context);

	this.maxWidth = context.getResources().getDisplayMetrics().widthPixels;
	this.maxHeight = context.getResources().getDisplayMetrics().heightPixels;
	// Pantallas
	this.context = context;
	// Imágenes
	al_fire = new ArrayList<Bitmap>();
	al_fire.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.fire1));
	al_fire.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.fire2));
	al_fire.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.fire3));
	al_fire.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.fire4));
	al_fire.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.fire5));
	al_usb = new ArrayList<Bitmap>();
	al_usb.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.usb0));
	al_usb.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.usb1));
	al_usb.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.usb2));
	imageShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.linux_gun);
	imageBullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
	imageWin = BitmapFactory.decodeResource(context.getResources(), R.drawable.winicon);
	backGround = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);

	// Duendecillos espaciales
	invaders = new ArrayList<Sprite>();
	spaceShip = new Sprite(maxWidth/2, maxHeight/2, 0, 0, invaders,  imageShip, imageBullet, al_fire, maxWidth, maxHeight);
	invaders.add(new Sprite(imageWin.getWidth()/2, imageWin.getHeight()/2 + 10, .2f, 0, spaceShip, imageWin, al_usb.get(0), al_fire, maxWidth, maxHeight));

	paint = new Paint();
	paint.setColor(Color.WHITE);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);
	int i = 0, speedFrame = 2;
	// Fondo animado. Necesariamente lo primero.
	canvas.drawBitmap(backGround, 0, frame - backGround.getHeight() - 2*speedFrame, paint); 
	canvas.drawBitmap(backGround, 0, frame - 2*speedFrame, paint);
	frame += speedFrame;
	if (frame > backGround.getHeight())
	    frame -= backGround.getHeight();
	// Nave
	spaceShip.draw(canvas);
	if (!spaceShip.alive()) {
	    gameOver = true;
	    ((MainCanvas)context).toGameOver();
	}
	// Invaders
	while (i < invaders.size()) {
	    invaders.get(i).Shooting(true);
	    invaders.get(i).draw(canvas);
	    if (invaders.get(i).alive())
		i++;
	    else
		invaders.remove(i);
	}
	if (invaders.size() == 0) {
	    level++;
	    for (i = 0; i < level; i++)
		invaders.add(new Sprite( imageWin.getWidth() + maxWidth/((float)level+1)*((float)i), imageWin.getHeight()/2 + 10 + (imageWin.getHeight() + 10)*((float)(i%2)), (float)( 100*Math.atan(((double)(i+1))/20)*2/3 ), 0, spaceShip, imageWin, al_usb.get(i%3), al_fire, maxWidth, maxHeight));
	}
	canvas.drawText("Level: " + level, 0, 10, paint);
    }

    private void spaceShipPos(float x, float y) {
	    switch (modo) {
	    case (UNO):
		spaceShip.setX(x);
		spaceShip.setY(y);
		break;
	    case (DOS):
		spaceShip.setX(x + dspaceShipPosX);
		spaceShip.setY(y + dspaceShipPosY);
		break;
	    }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
 
        int action = event.getAction();

        switch (action) {
        case (MotionEvent.ACTION_DOWN):
            // La accion ha sido ABAJO
	    dspaceShipPosX = spaceShip.getX() - event.getX();
	    dspaceShipPosY = spaceShip.getY() - event.getY();
	    spaceShipPos(event.getX(), event.getY());

	    spaceShip.Shooting(true);
            break;
        case (MotionEvent.ACTION_MOVE):
            // La acción ha sido MOVER
	    spaceShipPos(event.getX(), event.getY());
	    break;
        case (MotionEvent.ACTION_UP):
            // La acción ha sido ARRIBA
	    spaceShip.Shooting(false);
            break;
        case (MotionEvent.ACTION_CANCEL):
            // La accion ha sido CANCEL
            break;
        case (MotionEvent.ACTION_OUTSIDE):
            // La accion ha sido fuera del elemento de la pantalla
            break;
        default:
            return super.onTouchEvent(event);
        }
	
	
	//invalidate(); // se redibuja en el Thread
	return true;
    }

    public void setMode(int modo) {
	this.modo = modo;
    }

    public boolean GameOver() {
	return gameOver;
    }
}
