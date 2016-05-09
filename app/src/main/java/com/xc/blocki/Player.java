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
    int gameCoordX; //variable x is in terms of camera coordinates

    public Player(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context, GameView gameView) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight,context, gameView);
        type = Type.PLAYER;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bitmap =  Bitmap.createScaledBitmap(tmp, width, height, false);
        gameCoordX = xPos;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void update() {

    }
    @Override
    public void update(int i) {

    }
    public void update(boolean backgroundStopped) {
        if (!obstacle) {
            if (state == State.LEFT) {
                if (backgroundStopped)
                    x -= speedX;
                gameCoordX -= speedX;
                if (gameCoordX <= 0) {
                    gameCoordX = 0;
                }
                if (x <= 0) {
                    x = 0;
                }
            }
            if (state == State.RIGHT) {
                if (backgroundStopped)
                    x += speedX;
                gameCoordX += speedX;
                if (gameCoordX >= gameView.endX - width) {
                    gameCoordX = gameView.endX - width;
                }
                if (x >= gameView.endX - width) {
                    x = gameView.endX - width;
                }
            }
        }
        if(state == State.JUMPING){ //todo: implement check intersect here
            y -= speedY;
            setState(State.STOPPED);
        /*if(y <= 0)
            y = 0;*/ //player can go through ceiling or not?
        }
        hitbox.set(gameCoordX, y, gameCoordX+width, y+height);
        checkIntersect();
        if (!onGround) { //falling state
            if (y + height >= getHeight) {
                y = getHeight - height;
            }
            else {
                y += gravity;
            }
        }
        Log.i("Player", hitbox.toShortString());
    }

    public void checkIntersect(){ //todo: fix side obstacle detection
        boolean tmp = false;
        for (Block i : gameView.blocks){
            if (((i.hitbox.left - hitbox.right <= 0) && (i.hitbox.left - hitbox.right >= -width) && (hitbox.bottom == i.hitbox.top))){
                //calculating if the two blocks are touching along the bottom edge
                //block below player
                onGround = true;
                tmp = true;
                break;
            }
            if ((i.hitbox.top - hitbox.bottom <= 0) && (i.hitbox.top - hitbox.bottom >= -height) && ((hitbox.left == i.hitbox.right) || (hitbox.right == i.hitbox.left))){
                //calculating if two blocks are touching along either side
                /*if (!onGround){
                    setState(State.JUMPING); //hit a block while moving and falling
                    obstacle = true;
                }*/
                //else{
                    setState(State.STOPPED); //hit a block while moving on the ground
                    obstacle = true;
                    Log.i("Player", "obstacle");
                //}
            }
            else{
                obstacle = false;
            }
        }
        if (!tmp){
            onGround = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        //Log.d("player", "Draw");
    }
}
