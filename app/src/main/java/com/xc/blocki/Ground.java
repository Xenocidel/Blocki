package com.xc.blocki;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Ground extends Block {

    public Ground(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health);
        loaded = false;
    }

    private boolean loaded;

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        if (!loaded){

        }
    }
}
