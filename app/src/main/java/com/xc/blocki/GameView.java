package com.xc.blocki;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Aaron on 2016-05-03.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    public GameView(Context context) { super(context) ;
        this.context = context;
        getHolder (). addCallback(this);
        setFocusable(true);
    }

    Context context;
    boolean gameLoaded = false;
    GameThread gt;
    int level=1;
    int score=0;
    int lives=3;

    public void loadGame(int level){

        loadTouchHandler();
    }

    public void loadTouchHandler(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //todo movement and abilities
                return true;
            }
        });
    }

    @Override
    public void surfaceCreated ( SurfaceHolder holder ) {
        // Launch animator thread
        if (!gameLoaded) {
            gt = new GameThread(this);
            gt.start();
            gameLoaded = true;
        }
        Log.d("Load", "surfaceView/Thread");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        switch(gt.getGameState()){
            case RUNNING:
                canvas.drawColor(Color.LTGRAY);
                break;
            case LOADING:
                break;
            case OVER:
                break;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
