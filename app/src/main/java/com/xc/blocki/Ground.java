package com.xc.blocki;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Ground extends Block {

    private boolean loaded; //save time by updating the ground only once

    public Ground(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight);
        loaded = false;
        type = Type.GROUND;
    }

    public Ground(int xPos, int yPos, int getWidth, int getHeight, Context context){
        this(xPos, yPos, 0, 0, 0, 0, getWidth, getHeight, context);
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
        if (!loaded){

        }
    }
}
