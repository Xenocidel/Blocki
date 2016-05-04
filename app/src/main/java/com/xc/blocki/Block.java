package com.xc.blocki;

import android.graphics.RectF;

/**
 * Created by Aaron on 2016-05-03.
 */
public abstract class Block {
    int x;
    int y;
    int width;
    int height;
    int speedX;
    int speedY;
    int gravity;
    int health;
    RectF hitbox;
    State state;
    Type type;
    public enum State{STOPPED, MOVING, JUMPING};
    public enum Type{PLAYER, ENEMY, GROUND, ITEM};

    public Block(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health) {
        x = xPos;
        y = yPos;
        speedX = xSpeed;
        speedY = ySpeed;
        this.gravity = gravity;
        this.health = health;
        state = State.STOPPED;
        RectF tmp = new RectF(xPos, yPos, xPos+width, yPos+height);
    }

    public Block(){
    }

    public abstract void update();
    public abstract void draw();

}
