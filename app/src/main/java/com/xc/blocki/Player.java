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
    Bitmap shooting;
    int drawShooting;
    //int gameCoordX; //variable x is in terms of camera coordinates

    public Player(int xPos, int yPos, int xSpeed, int ySpeed, int gravity, int health,
                  int getWidth, int getHeight, Context context, GameView gameView) {
        super(xPos, yPos, xSpeed, ySpeed, gravity, health, getWidth, getHeight,context, gameView);
        type = Type.PLAYER;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bitmap =  Bitmap.createScaledBitmap(tmp, width, height, false);
        tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_shoot);
        shooting =  Bitmap.createScaledBitmap(tmp, width, height, false);
        drawShooting = 0;
        //gameCoordX = xPos;
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
        Log.i("Player", hitbox.toShortString());
        if (state == State.LEFT) {
            if (backgroundStopped)
                x -= speedX;
            /*gameCoordX -= speedX;
            if (gameCoordX <= 0) {
                gameCoordX = 0;
            }*/
            if (x <= 0) {
                x = 0;
            }
        }
        if (state == State.RIGHT) {
            if (backgroundStopped)
                x += speedX;
            /*gameCoordX += speedX;
            if (gameCoordX >= gameView.endX - width) {
                gameCoordX = gameView.endX - width;
            }*/
            if (x >= gameView.endX - width) {
                x = gameView.endX - width;
            }
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

    public void checkIntersect(){

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
            canvas.drawBitmap(shooting, x, y, null);
        }
        else {
            canvas.drawBitmap(bitmap, x, y, null);
        }
        //Log.d("player", "Draw");
    }
}
