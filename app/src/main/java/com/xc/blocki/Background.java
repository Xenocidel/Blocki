package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by LiangYenChun on 5/4/2016.
 */
public class Background extends Block {

    boolean backgroundStopped;
    GameView gameView;


    public Background(int getWidth, int getHeight, Context context, GameView gameView){
        super(0, 0, 0, 0, 0, 0, getWidth, getHeight, context);
        this.gameView = gameView;
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        bitmap =  Bitmap.createScaledBitmap(tmp, gameView.endX, getHeight, false);
        backgroundStopped = true;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void update() {

    }

    public void update(int playerX){
        //If player at quarter screen, background can move.
        //If background touches the end, it can not go back.
        if(playerX >= getWidth/4) {
            backgroundStopped = false;
            if (state == state.LEFT) {
                x -= 10;
                if(x <= -(gameView.endX - gameView.getWidth())){
                    x = -(gameView.endX - gameView.getWidth());
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
