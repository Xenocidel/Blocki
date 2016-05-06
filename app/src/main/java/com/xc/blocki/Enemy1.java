package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Enemy1 extends Block {

    public Enemy1(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight, context);
        type = Type.ENEMY;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy1);
        bitmap =  Bitmap.createScaledBitmap(tmp, width, height, false);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(int playerX) {
        //If player at quarter screen, ground can move.
        //If ground touches the end, it can not go back.
        if(playerX >= getWidth/4) {
            if (state == state.LEFT) {
                x -= 10;
            }
            if (state == state.RIGHT) {
                x += 10;
            }
        }
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }


    @Override
    public void draw(Canvas canvas) { canvas.drawBitmap(bitmap, x, y, null); }
}
