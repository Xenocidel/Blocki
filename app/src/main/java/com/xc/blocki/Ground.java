package com.xc.blocki;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Ground extends Block {

    private boolean loaded; //save time by updating the ground only once

    public Ground(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health);
        loaded = false;
        type = Type.GROUND;
    }

    public Ground(int xPos, int yPos){
        this(xPos, yPos, 0, 0, 0, 0);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        if (!loaded){

        }
    }
}
