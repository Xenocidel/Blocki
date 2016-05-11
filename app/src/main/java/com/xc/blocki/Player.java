package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by Aaron on 2016-05-03.
 */
public class Player extends Block {
    boolean playerMiddle;
    Bitmap shootRight;
    Bitmap shootLeft;
    Bitmap idleRight;
    Bitmap idleLeft;
    int drawShooting;
    boolean facingRight;
    //int gameCoordX; //variable x is in terms of camera coordinates

    public Player(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context, GameView gameView) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight,context, gameView);
        type = Type.PLAYER;
        health = 1;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_left);
        idleLeft = Bitmap.createScaledBitmap(tmp, width, height, false);
        tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_left);
        shootLeft = Bitmap.createScaledBitmap(tmp, width, height, false);
        tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_right);
        idleRight = Bitmap.createScaledBitmap(tmp, width, height, false);
        tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_right);
        shootRight = Bitmap.createScaledBitmap(tmp, width, height, false);
        drawShooting = 0;
        facingRight = true;
        //gameCoordX = xPos;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void update() {

    }

    public void update(int backgroundX) {
        collisionDetection();
        if (!isAlive){
            gameView.gt.setGameState(GameThread.GameState.OVER);
        }
        //Log.i("Player", hitbox.toShortString());
        if (state == state.LEFT) {
            if(gameView.STOPPED) {
                x -= speedX;
            }
            if(x <= 0){
                x = 0;
            }
            facingRight = false;
        }
        if (state == State.RIGHT){
            if(gameView.STOPPED) {
                x += speedX;
            }
            if(x > getWidth - width) {
                x = getWidth - width;
            }
            facingRight = true;
        }
        if(x >= getWidth/4 && backgroundX == 0) {
            gameView.STOPPED = false;
        }
        if (y + height >= getHeight - height) { //hardcoded ground level
            y = getHeight - 2*height;
        }
        else {
            y += gravity;
        }
        hitbox.set(x, y, x+width, y+height);
        //}
        //Log.i("Player", hitbox.toShortString());
    }

    public void collisionDetection(){
        blockLoop:
        for (Block i : gameView.blocks){
            if (RectF.intersects(hitbox, i.hitbox)){
                switch (i.type){
                    case ENEMY:
                        //for now player dies immediately upon hitting an enemy
                        isAlive = false;
                        break blockLoop;
                    case GROUND:
                        //player cannot move and stops
                        setState(State.STOPPED);
                        x--;
                        gameView.STOPPED=true;
                        break;
                    case ITEM:
                        //todo: items not yet implemented
                        break;
                }
            }
        }
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    @Override
    public void draw(Canvas canvas) {
        if (drawShooting-- > 0){
            if (facingRight) {
                canvas.drawBitmap(shootRight, x, y, null);
            }
            else{
                canvas.drawBitmap(shootLeft, x, y, null);
            }
        }
        else {
            if (facingRight) {
                canvas.drawBitmap(idleRight, x, y, null);
            }
            else{
                canvas.drawBitmap(idleLeft, x, y, null);
            }
        }
        //Log.d("player", "Draw");
    }
}
