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
        health = 1;
        isAlive = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(int playerX) {
        if (health <= 0){
            isAlive = false;
        }
        //If player at quarter screen, ground can move.
        //If ground touches the end, it can not go back.
        if (isAlive) {
            if (playerX >= getWidth / 4) {
                if (state == State.LEFT) {
                    x -= 10;
                }
                if (state == State.RIGHT) {
                    x += 10;
                }
            }
            hitbox.set(x, y, x+width, y+height);
        }
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void draw(Canvas canvas) {
        if (isAlive){
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }
}
