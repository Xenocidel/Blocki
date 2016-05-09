package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Ground extends Block {

    private boolean loaded; //save time by updating the ground only once

    public Ground(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight, context);
        loaded = false;
        type = Type.GROUND;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);
        bitmap =  Bitmap.createScaledBitmap(tmp, getHeight/10, getHeight/10, false);
    }

    public Ground(int xPos, int yPos, int getWidth, int getHeight, Context context){
        super(xPos, yPos, 0, 0, 0, 0, getWidth, getHeight, context);
        loaded = false;
        type = Type.GROUND;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);
        bitmap =  Bitmap.createScaledBitmap(tmp, width, height, false);
        Log.i("Ground", hitbox.toShortString());
    }

    @Override
    public void update() {

    }


    public void update(int playerX){
        //If player at quarter screen, ground can move.
        //If ground touches the end, it can not go back.
        if(playerX >= getWidth/4) {
            if (state == State.LEFT) {
                x -= 10;
            }
            if (state == State.RIGHT) {
                x += 10;
            }
        }
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        //Log.d("ground", "Draw");
    }
}
