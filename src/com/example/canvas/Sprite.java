package com.example.canvas;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Canvas;
import java.util.ArrayList;
import android.graphics.RectF;
import java.util.Random;

public class Sprite extends CRect {

    private float vx, vy, bulletSpeed = 7;

    private int live;

    private Bitmap imageShip, imageBullet;

    private float maxWidth, maxHeight;

    private boolean shooting = false, visible = true;

    private Paint paint;

    private BulletList bullets; 

    private Sprite other = null;
    private ArrayList<Bitmap> fireAnim;
    private ArrayList<Sprite> others = null;

    // Constructores INI
    public Sprite(float x, float y, float vx, float vy, Sprite other, Bitmap imageShip, Bitmap imageBullet, ArrayList<Bitmap> fireAnim, float maxWidth, float maxHeight) {
	super(x, y, imageShip.getWidth(), imageShip.getHeight());
	this.imageShip = imageShip;
	this.imageBullet = imageBullet;
	if (x < 0)
	    setX(imageShip.getWidth()/2);
	if (y < 0)
	    setY(imageShip.getHeight()/2);
	this.fireAnim = fireAnim;

	this.maxWidth = maxWidth;//context.getResources().getDisplayMetrics().widthPixels;
	this.maxHeight = maxHeight;//context.getResources().getDisplayMetrics().heightPixels;

	this.vx = vx;
	this.vy = vy;

	this.other = other;
	paint = new Paint();
	paint.setColor(Color.WHITE);
	bullets = new BulletList(maxWidth, maxHeight);
	live = 10;
    }

    public Sprite(float x, float y, float vx, float vy, ArrayList<Sprite> others, Bitmap imageShip, Bitmap imageBullet, ArrayList<Bitmap> fireAnim, float maxWidth, float maxHeight) {
	super(x, y, imageShip.getWidth(), imageShip.getHeight());
	this.imageShip = imageShip;
	this.imageBullet = imageBullet;
	if (x < 0)
	    setX(imageShip.getWidth()/2);
	if (y < 0)
	    setY(imageShip.getHeight()/2);	
	this.fireAnim = fireAnim;

	this.maxWidth = maxWidth;//context.getResources().getDisplayMetrics().widthPixels;
	this.maxHeight = maxHeight;//context.getResources().getDisplayMetrics().heightPixels;

	this.vx = vx;
	this.vy = vy;

	this.others = others;
	paint = new Paint();
	paint.setColor(Color.WHITE);
	bullets = new BulletList(maxWidth, maxHeight);
	live = 100;
    }
    // Constructores FIN




    public void Shooting(boolean shooting) {
	if (visible)
	    this.shooting = shooting;
	else
	    this.shooting = false;
    }
    public void Visible(boolean visible) {
	this.visible = visible;
    }


    int animStep = 0;

    public void draw(Canvas canvas) {
	int i;
	if (other == null) {
	  // Nave espacial
	    // Balas
	    if (shooting)
		bullets.add(new CRect(getX(), getU(), imageBullet.getWidth(), imageBullet.getHeight()), 2, -bulletSpeed, imageBullet);
	    bullets.draw(canvas);
	    // Balas enemigas
	    if (live > 0) {
		canvas.drawBitmap(imageShip, getL(), getU(), paint);
		canvas.drawText(" " + live, getL(), getD() + 10, paint);
		for (i = 0; i < others.size(); i++)
		    live -= others.get(i).BulletsHits(new CRect(getX(), getY(), getW(), getH()));
	    } else if (animStep < fireAnim.size()) {
		canvas.drawBitmap(fireAnim.get(animStep), null, new RectF(getL(), getU(), getR(), getD()), paint);
		animStep++;
		visible = false;
		shooting = false;
	    }
	} else {
	  // Invasores
	    // Balas
	    Random rand = new Random();
	    if (shooting && rand.nextInt(10) == 0)
		bullets.add(new CRect(getX(), getD(), imageBullet.getWidth(), imageBullet.getHeight()), imageBullet.getWidth()/5, bulletSpeed, imageBullet);
	    bullets.draw(canvas);
	    if (live > 0) {
	    // Invasor
		setX(getX() + (float)Math.atan((double)(other.getX() - this.getX())/40)*vx);
		setY(getY() + (float)Math.atan((double)(other.getY() - this.getY())/40)*vy);
		canvas.drawBitmap(imageShip, getL(), getU(), paint);
		canvas.drawText(" " + live, getL(), getU(), paint);
	    // Balas enemigas
		live -= other.BulletsHits(new CRect(getX(), getY(), getW(), getH()));
	    } else if (animStep < fireAnim.size()) {
		canvas.drawBitmap(fireAnim.get(animStep), null, new RectF(getL(), getU(), getR(), getD()), paint);
		animStep++;
		visible = false;
		shooting = false;
	    }
	}
    }

    public boolean alive() {
	if (live > 0 || animStep < fireAnim.size())
	    return true;
	else
	    return false;
    }

    public BulletList getBullets() {
	return bullets;
    }

    public int BulletsHits(CRect crect) {
	return bullets.Hits(crect);
    }


    // public void nextStep() {
    // 	//if (enabled || visible) {
    // 	    switch (moveTipe) {
    // 	    case MOVE_REBOUND:
    // 		setX(x + vx);
    // 		setY(y + vy);
    // 		if (xL < 0) {
    // 		    setL(0);
    // 		    vx = - vx;
    // 		} else if (xR > maxWidth) {
    // 		    setR(maxWidth);
    // 		    vx = - vx;
    // 		}
    // 		if (yU < 0) {
    // 		    setU(0);
    // 		    vy = - vy;
    // 		} else if (yD > maxHeight) {
    // 		    setD(maxHeight);
    // 		    vy = - vy;
    // 		}
    // 		break;
    // 	    case MOVE_FROM:
    // 		setX(x + vx);
    // 		setY(y + vy);
    // 		if (xL < 0) {
    // 		    setR(other.getL());
    // 		    setY(other.getY());
    // 		    if (!enabled) visible = false;
    // 		} else if (xR > maxWidth) {
    // 		    setR(other.getL());
    // 		    setY(other.getY());
    // 		    if (!enabled) visible = false;
    // 		}
    // 		if (yU < 0) {
    // 		    setD(other.getU());
    // 		    setX(other.getX());
    // 		    if (!enabled) visible = false;
    // 		} else if (yD > maxHeight) {
    // 		    setU(other.getD());
    // 		    setX(other.getX());
    // 		    if (!enabled) visible = false;
    // 		}
    // 		break;
    // 	    case MOVE_TO:
    // 		setX(x + (other.getX() - this.getX())*vx);
    // 		setY(y + (other.getY() - this.getY())*vy);
    // 		break;
    // 	    }
    //     //}
    // }
}
