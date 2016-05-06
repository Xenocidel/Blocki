package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Player extends Block {
    boolean playerMiddle;

    public Player(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight,context);
        type = Type.PLAYER;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bitmap =  Bitmap.createScaledBitmap(tmp, width, height, false);
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void update() {

    }
    @Override
    public void update(int i) {

    }
    public void update(boolean backgroundStopped) {
        if(backgroundStopped){
            if(state == state.LEFT){
                x -= speedX;
                if(x <= 0){ x = 0; }
            }
            if(state == state.RIGHT){
                x += speedX;
                if(x >= getWidth-getHeight/10){
                    x = getWidth-getHeight/10;

                }
            }
        }

        if(state == state.JUMPING){
            y -= speedY;
            if(y <= 0)
                y = 0;
        }
        y += gravity;
        if(y >= 8*getHeight/10){
            y = 8*getHeight/10;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        //Log.d("player", "Draw");
    }
}
