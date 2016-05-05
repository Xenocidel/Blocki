package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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
    Player player;
    Background background;
    ArrayList<Block> blocks = new ArrayList<>(); //contains all blocks except player

    public void addBlock(Block block){
        blocks.add(block);
    }

    public Block getBlock(int i){
         return blocks.get(i);
    }

    public void loadGame(int level){
        player = new Player(getWidth()/15, 9*getHeight()/10, 10, 10, 3, 3, getWidth(), getHeight(), context);
        background = new Background(context, getWidth(), getHeight());

        loadTouchHandler();
    }

    public void loadTouchHandler(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if(event.getX() > getWidth() / 2){
                            player.setState(Block.State.RIGHT);
                            background.setMovementState(background.LEFT);
                        }else{
                            player.setState(Block.State.LEFT);
                            background.setMovementState(background.RIGHT);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        background.setMovementState(background.STOPPED);
                        player.setState(Block.State.STOPPED);
                        break;
                }
                return true;
            }
        });
    }

    public void update(){
        player.update(background.backgroundStopped);
        background.update(player.x);
        for (Block block : blocks){
            block.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        update();
        switch(gt.getGameState()){
            case RUNNING:
                canvas.drawColor(Color.LTGRAY);
                background.draw(canvas);
                player.draw(canvas);
                break;
            case LOADING:
                break;
            case OVER:
                break;
        }
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
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
