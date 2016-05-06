package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by LiangYenChun on 5/4/2016.
 */
public class Background extends Block {

    boolean backgroundStopped;

    public Background(int getWidth, int getHeight, Context context){
        super(0, 0, 0, 0, 0, 0, getWidth, getHeight, context);
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        bitmap =  Bitmap.createScaledBitmap(tmp, getWidth*3, getHeight, false);
        backgroundStopped = true;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void update() {

    }
    @Override
    public void update(int playerX){
        //If player at quarter screen, background can move.
        //If background touches the end, it can not go back.
        if(playerX >= getWidth/4) {
            backgroundStopped = false;
            if (state == state.LEFT) {
                x -= 10;
                if(x <= -2*getWidth){
                    x = -2*getWidth;
                    backgroundStopped =true;
                }
            }
            if (state == state.RIGHT) {
                x += 10;
                if(x >= 0){
                    x = 0;
                    backgroundStopped = true;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
