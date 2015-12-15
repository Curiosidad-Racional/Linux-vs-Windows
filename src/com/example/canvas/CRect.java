package com.example.canvas;

public class CRect {

    private float x, y, xL, xR, yU, yD;

    private float width, height, semiWidth, semiHeight;

    public CRect(float x, float y, float width, float height) {
	this.x = x; this.y = y;
	this.width = width; this.height = height;
	this.semiWidth = width/2; this.semiHeight = height/2;
	this.xL = x - semiWidth;
	this.xR = x + semiWidth;
	this.yU = y - semiHeight;
	this.yD = y + semiHeight;
    }


    // Bordes INI
    public void setL(float xL) {
	this.xL = xL;
	this.x = xL + semiWidth;
	this.xR = xL + width;
    }


    public void setR(float xR) {
	this.xR = xR;
	this.x = xR - semiWidth;
	this.xL = xR - width;
    }

    public void setU(float yU) {
	this.yU = yU;
	this.y = yU + semiHeight;
	this.yD = yU + height;
    }

    public void setD(float yD) {
	this.yD = yD;
	this.y = yD - semiHeight;
	this.yU = yD - height;
    }

    public float getL() {
	return xL;
    }


    public float getR() {
	return xR;
    }

    public float getU() {
	return yU;
    }

    public float getD() {
	return yD;
    }
    // Bordes FIN

    // Centro INI
    public void setX(float x) {
	this.x = x;
	this.xL = x - semiWidth;
	this.xR = x + semiWidth;
    }

    public void setY(float y) {
	this.y = y;
	this.yU = y - semiHeight;
	this.yD = y + semiHeight;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }
    // Centro FIN


    public float getW() {
	return width;
    }

    public float getH() {
	return height;
    }

    public boolean intersect(CRect other) {
	if (Math.abs(this.x - other.x) < this.semiWidth + other.semiWidth 
	    && Math.abs(this.y - other.y) < this.semiHeight + other.semiHeight) {
	    return true;
	} else {
	    return false;
	}
    }
}
