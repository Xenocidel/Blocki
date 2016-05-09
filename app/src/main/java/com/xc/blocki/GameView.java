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
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    //int gameLevel=1;
    int score=0;
    Level level;
    Player player;
    Background background;
    boolean STOPPED;
    int endX; //rightmost position in game coordinates
    ArrayList<Block> blocks = new ArrayList<>(); //contains all blocks except player
    //GregorianCalendar cal = new GregorianCalendar();

    public void addBlock(Block block){
        blocks.add(block);
    }

    public Block getBlock(int i){
         return blocks.get(i);
    }

    public void loadGame(int gameLevel){
        player = new Player(50, getHeight()/2, 10, 30, 10, 3, getWidth(), getHeight(), context, this);
        background = new Background(getWidth(), getHeight(),context);
        level = new Level(this);
        level.loadLevel(gameLevel);

        loadTouchHandler();
    }

    public void loadTouchHandler(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getY() < getHeight()*3/4){
                    if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
                        player.setState(Block.State.JUMPING);
                    }
                }
                switch(event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if(event.getY() > getHeight()*3/4){
                            if(event.getX() > getWidth() / 2){
                                player.setState(Block.State.RIGHT);
                                background.setState(Block.State.LEFT);
                                for (Block block : blocks){
                                    block.setState(Block.State.LEFT);
                                }

                            }else{
                                player.setState(Block.State.LEFT);
                                background.setState(Block.State.RIGHT);
                                for (Block block : blocks){
                                    block.setState(Block.State.RIGHT);
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        background.setState(Block.State.STOPPED);
                        player.setState(Block.State.STOPPED);
                        for (Block block : blocks){
                            block.setState(Block.State.STOPPED);
                        }
                        break;
                }
                return true;
            }
        });
    }


    public void update(){
        player.update(background.backgroundStopped);
        if(!STOPPED) {
            background.update(player.x);
            for (Block block : blocks) {
                block.update(player.x);
                if (block.x <= -2 * getWidth()) {
                    // if ground touched the bound, background, ground enemy all can't move.
                    STOPPED = true;
                }
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        update();
        switch(gt.getGameState()){
            case RUNNING:
                //canvas.drawColor(Color.LTGRAY);
                background.draw(canvas);
                player.draw(canvas);
                for (Block block : blocks){
                    block.draw(canvas);
                }
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
