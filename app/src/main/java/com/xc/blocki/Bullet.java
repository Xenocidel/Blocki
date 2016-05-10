package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Taken and modified from SpaceyInvaders
 */
public class Bullet {

    int width; //screenWidth
    int height; //screenHeight
    int bulletWidth;
    int bulletHeight;
    int margin;
    float xi, yi; // initial position of bullets
    float x, y;
    float vx; //speed of bullets
    Bitmap bitmapBullet;
    boolean isShooting;
    boolean isAlive;

    public Bullet(Context context, int width, int height, float xPosition, float yPosition) {
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        bulletWidth = width/25;
        bulletHeight = bulletWidth;
        bitmapBullet = Bitmap.createScaledBitmap(tmp, bulletWidth, bulletHeight, false);
        vx = 15;
        this.width = width;
        this.height = height;
        margin = bulletWidth/2;
        xi = xPosition;
        yi = yPosition;
        x = xi;
        y = yi;
        isShooting = false;
        isAlive = true;
        Log.d("Load", "Bullet");
    }

    public void draw(Canvas c){
        Paint p = new Paint();
        if (isShooting) {
            c.drawBitmap(bitmapBullet, x, y, p);
        }
    }

    public void update(float xPosition, float yPosition){
        y = yPosition;
        if (isShooting) {
            float tmpX;
            tmpX = x + vx;
            if (tmpX > width || (!isAlive)) { //isAlive is linked to collision detection, which is in GameView's update()
                setShooting(false);
            }
            x = tmpX;
        }
        else{
            x = xPosition;
            isAlive = true;
        }
    }

    public void setShooting(boolean shooting){
        isShooting = shooting;
    }

    public float getX(){return x;}
    public float getY(){return y;}
}
