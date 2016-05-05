package com.xc.blocki;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Enemy1 extends Block {

    public Enemy1(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight);
        type = Type.ENEMY;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(boolean backgroundStopped) {

    }

    @Override
    public void setState(State state) {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
