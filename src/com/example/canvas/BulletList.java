package com.example.canvas;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Canvas;


public class BulletList {

    private ArrayList<Integer> liveList;

    private ArrayList<Float> speedList;

    private ArrayList<CRect> bullets;

    private ArrayList<Bitmap> images;

    private float maxWidth, maxHeight;

    private Paint paint;

    private int nitems = 0, item;

    public BulletList(float maxWidth, float maxHeight) {
	bullets = new ArrayList<CRect>();
	speedList = new ArrayList<Float>();
	liveList = new ArrayList<Integer>();
	images = new ArrayList<Bitmap>();

	this.maxWidth = maxWidth;
	this.maxHeight = maxHeight;
	paint = new Paint();
    }

    public void add(CRect crect, int live, float speed, Bitmap image) {
	bullets.add(crect);
	liveList.add(live);
	speedList.add(speed);
	images.add(image);
	nitems++;
    }

    private void remove(int item) {
	bullets.remove(item);
	liveList.remove(item);
	speedList.remove(item);
	images.remove(item);
	nitems--;
    }	 

    public void draw(Canvas canvas) {
	int i;
	i = 0;
	while (i < nitems) {
	    bullets.get(i).setY(bullets.get(i).getY() + speedList.get(i));
	    canvas.drawBitmap(images.get(i), bullets.get(i).getL(), bullets.get(i).getU(), paint);
	    if (bullets.get(i).getY() < 0)
		remove(i);
	    else if (bullets.get(i).getY() > maxHeight)
		remove(i);
	    else if (bullets.get(i).getX() < 0)
		remove(i);
	    else if (bullets.get(i).getX() > maxWidth)
		remove(i);
	    else
		i++;
	}
    }

    public int size() {
	return nitems;
    }

    public int Hits(CRect crect) {
	int hits = 0;
	int i;
	i = 0;
	while (i < nitems) {
	    if (bullets.get(i).intersect(crect)) {
		hits++;
		liveList.set(i, liveList.get(i) - 1);
		if (liveList.get(i) > 0)
		    i++;
		else
		    remove(i);
	    } else {
		i++;
	    }
	}
	return hits;
    }

}
