package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Enemy1 extends Block {

    public Enemy1(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context, GameView gameView) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight, context, gameView);
        type = Type.ENEMY;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy1);
        bitmap =  Bitmap.createScaledBitmap(tmp, width, height, false);
        health = 1;
        movingRight = true;
    }

    boolean movingRight;

    @Override
    public void update() {
        if (health <= 0){
            if (isAlive)
                gameView.addScore(1);
            isAlive = false;
        }
        if (isAlive) {
            if (state == State.LEFT) {
                x -= 10;
            }
            if (state == State.RIGHT) {
                x += 10;
            }
            hitbox.set(x, y, x+width, y+height);
        }
    }

    public void updateAI(){
        if (movingRight){
            x += speedX;
        }
        else{
            x -= speedX;
        }
        boolean onGround = false;
        RectF tmp = new RectF(x, y+gravity, x+width, y+gravity+height);
        for (Block i : gameView.blocks){
            if (RectF.intersects(tmp, i.hitbox) && i.type == Type.GROUND) {
                onGround = true;
            }
        }
        if (!onGround) {
            y += gravity;
        }
        hitbox.set(x, y, x+width, y+height);
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
