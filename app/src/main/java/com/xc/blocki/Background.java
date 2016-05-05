package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by LiangYenChun on 5/4/2016.
 */
public class Background {
    Bitmap background;
    int x, y;
    int getWidth, getHeight;
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;
    int backgroundMoving;
    boolean backgroundStopped;

    public Background(Context context, int getWidth, int getHeight){
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.background3);
        background =  Bitmap.createScaledBitmap(tmp, getWidth*3, getHeight, false);
        x = 0;
        y = 0;
        this.getHeight = getHeight;
        this.getWidth = getWidth;
        backgroundStopped = true;
    }

    public void setMovementState(int state){
        backgroundMoving = state;
    }

    public void update(int playerX){
        if(playerX >= getWidth/2) {
            backgroundStopped = false;
            if (backgroundMoving == LEFT) {
                x -= 10;
                if(x < -getWidth){
                    x = -getWidth;
                    backgroundStopped =true;
                }
            }
            if (backgroundMoving == RIGHT) {
                x += 10;
                if(x >= 0){
                    x = 0;
                    backgroundStopped = true;
                }
            }
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(background, x, y, null);
    }
}
