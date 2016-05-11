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
    boolean fullScreen; // if endX > getWidth, fullScreen is true;
    ArrayList<Block> blocks = new ArrayList<>(); //contains all blocks except player
    Bullet[] bullet = new Bullet[10];
    int maxNumOfBullet = 10; // you can set the maximum number of bullets
    int numOfBullet = 0;
    //GregorianCalendar cal = new GregorianCalendar();

    public void addBlock(Block block){
        blocks.add(block);
    }

    public Block getBlock(int i){
         return blocks.get(i);
    }

    public void loadGame(int gameLevel){
        player = new Player(50, getHeight()/2, 10, 30, 10, 3, getWidth(), getHeight(), context, this);
        level = new Level(this);
        level.loadLevel(gameLevel);
        //bullet creation
        for(int i=0; i<maxNumOfBullet; i++) {
            bullet[numOfBullet] = new Bullet(this.context, getWidth(), getHeight(), player.hitbox.right, player.hitbox.centerY());
            numOfBullet++;
        }
        background = new Background(getWidth(), getHeight(),context, this);


        loadTouchHandler();
    }

    public void loadTouchHandler(){
        setOnTouchListener(new OnTouchListener() {
            int lastAction = -1;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //for now, player can only shoot when stopped and multiple bullets can be on screen at a time
                if (event.getY() < getHeight() * 3 / 4) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        for (int x = 0; x < maxNumOfBullet; x++) {
                            if (!bullet[x].isShooting && bullet[x].getX() < getWidth() && bullet[x].getX() > 0) { //prevent bug with bullet being launched from offscreen
                                bullet[x].setShooting(true, player.facingRight);
                                Log.i("Bullet", bullet[x].getX()+","+bullet[x].getY());
                                player.drawShooting = 5;
                                break;
                            }
                        }
                    }
                }
                switch (event.getActionIndex()) {
                    case 0:
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.d("Log.debug", "ACTION_DOWN at X=" + Float.toString(event.getX()) + ", Y=" + Float.toString(event.getY()));
                                if (event.getY() > getHeight() * 3 / 4) {
                                    if (event.getX() > getWidth() / 2) {
                                        player.setState(Block.State.RIGHT);
                                        background.setState(Block.State.LEFT);
                                        for (Block block : blocks){
                                            block.setState(Block.State.LEFT);
                                        }
                                        lastAction = 0;
                                    } else {
                                        player.setState(Block.State.LEFT);
                                        background.setState(Block.State.RIGHT);
                                        for (Block block : blocks){
                                            block.setState(Block.State.RIGHT);
                                        }
                                        lastAction = 1;
                                    }
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                player.setState(Block.State.STOPPED);
                                background.setState(Block.State.STOPPED);
                                for (Block block : blocks){
                                    block.setState(Block.State.STOPPED);
                                }
                                lastAction = -1;
                                break;
                            case MotionEvent.ACTION_POINTER_UP: //two buttons pressed, first one released
                                Log.d("Log.debug", "First button released");
                                if (event.getY(1) > getHeight() * 3 / 4) {
                                    if (event.getX(1) > getWidth() / 2) {
                                        player.setState(Block.State.RIGHT);
                                        background.setState(Block.State.LEFT);
                                        for (Block block : blocks){
                                            block.setState(Block.State.LEFT);
                                        }
                                    } else if (event.getX(1) < getWidth() / 2) {
                                        player.setState(Block.State.LEFT);
                                        background.setState(Block.State.RIGHT);
                                        for (Block block : blocks){
                                            block.setState(Block.State.RIGHT);
                                        }
                                    }
                                }
                                break;
                        }
                        return true;
                    case 1: //for handling two-touch events
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_POINTER_DOWN:
                                Log.d("Log.debug", "ACTION_POINTER_DOWN at X= " + Float.toString(event.getX(1)));
                                if (event.getY(1) > getHeight() * 3 / 4) {
                                    if (event.getX(1) < getWidth() / 2 && player.state == Block.State.RIGHT) { //cancel right movement
                                        player.setState(Block.State.STOPPED);
                                        background.setState(Block.State.STOPPED);
                                        for (Block block : blocks){
                                            block.setState(Block.State.STOPPED);
                                        }
                                    } else if (event.getX(1) > getWidth() / 2 && player.state == Block.State.LEFT) { //cancel left movement
                                        player.setState(Block.State.STOPPED);
                                        background.setState(Block.State.STOPPED);
                                        for (Block block : blocks){
                                            block.setState(Block.State.STOPPED);
                                        }
                                    }
                                }
                                break;
                            case MotionEvent.ACTION_POINTER_UP:
                                switch (lastAction) {
                                    case 0:
                                        player.setState(Block.State.RIGHT);
                                        background.setState(Block.State.LEFT);
                                        for (Block block : blocks){
                                            block.setState(Block.State.LEFT);
                                        }
                                        break;
                                    case 1:
                                        player.setState(Block.State.LEFT);
                                        background.setState(Block.State.RIGHT);
                                        for (Block block : blocks){
                                            block.setState(Block.State.RIGHT);
                                        }
                                }
                                break;
                        }
                        return true;
                }
                return false;
            }
        });

                /*switch(event.getActionMasked()){
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
        });*/
    }


    public void update(){
        player.update(background.backgroundStopped);
        if(endX > getWidth()){
            fullScreen = true;
        }else{
            fullScreen = false;
            STOPPED = true;
        }
        if(!STOPPED) {
            background.update(player.x);
            if(background.x < -(endX - getWidth())){
                STOPPED = true;
            }
            for (Block block : blocks) {
                block.update(player.x);
                if (block.x <= -(endX - getWidth())) {
                    // if ground touched the bound, background, ground enemy all can't move.
                    STOPPED = true;
                }
            }

        }
        //collisionDetection();
        //bullet[] update
        for (int i = 0; i < numOfBullet; i++) {
            //variable xi used here for the initial location of the bullet
            if (player.facingRight){
                bullet[i].xi = player.hitbox.right;
            }
            else{
                bullet[i].xi = player.hitbox.left - bullet[i].bulletWidth;
            }
            bullet[i].update(bullet[i].xi, player.hitbox.centerY()-bullet[i].bulletHeight, player.facingRight);
        }

    }

    /*
    public void collisionDetection(){
        //if a bullet collides with an invader, invaders[i].isAlive = false
        outerLoop:
        for(int j=0; j<numOfBullet; j++){
            if (bullet[j].isShooting) {
                float bCenterX = bullet[j].getX() + bullet[j].bulletWidth / 2;
                float bCenterY = bullet[j].getY() - bullet[j].bulletHeight / 2;
                float uCenterX = ufo.getX() + ufo.shipWidth / 2;
                float uCenterY = ufo.getY() - ufo.shipWidth / 2;
                if((Math.abs(uCenterY - bCenterY) <= touchDistanceYforUFO ) && (Math.abs(uCenterX - bCenterX) <= touchDistanceXforUFO)){
                    soundPool.play(soundShotUFO,1,1,1,0,1);
                    bullet[j].isAlive = false;
                    bullet[j].update(ship.getX()); //to prevent multi-kill with one bullet
                    score += 20*level;
                    scoreString = "Score: "+score;
                }
                for(int i=0; i<numOfInvaders; i++){
                    if (invaders[i].isAlive) {
                        float iCenterX = invaders[i].getX() + invaders[i].invadersWidth / 2;
                        float iCenterY = invaders[i].getY() - invaders[i].invadersHight / 2;
                        if ((Math.abs(iCenterY - bCenterY) <= touchDistanceY) && (Math.abs(iCenterX - bCenterX) <= touchDistanceX)) {
                            soundPool.play(soundBomb,0.1f,0.1f,1,0,1);
                            invaders[i].isAlive = false;
                            bullet[j].isAlive = false;
                            bullet[j].update(ship.getX()); //to prevent multi-kill with one bullet
                            score+=level;
                            if (--numOfInvadersAlive == 0) {
                                //disable all touch handling while next level is loading
                                setOnTouchListener(null);
                                //remove all bullets in the air before loading next level
                                for (int k = 0; k < numOfBullet; k++){
                                    if (bullet[k].isShooting){
                                        bullet[k].setShooting(false);
                                        bullet[k].resetY();
                                        bullet[k].update(ship.getX());
                                    }
                                }
                                score+=level*level;
                                level++;
                                st.setGameState(st.LOADING);
                                break outerLoop;
                            }
                            scoreString = "Score: "+score;
                            levelString = "Level: "+(int)level;
                            Log.d("collisionDetection", "invaders[" + i + "] removed by bullet["+j+"]");
                        }
                    }
                }
            }
        }
    }*/
    Paint p = new Paint(); //for debug
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        update();
        switch(gt.getGameState()){
            case RUNNING:
                //canvas.drawColor(Color.LTGRAY);
                background.draw(canvas);
                player.draw(canvas);
                p.setAlpha(128);
                p.setColor(Color.GREEN);
                //canvas.drawRect(player.hitbox, p); //for debug
                for (Block block : blocks){
                    block.draw(canvas);
                }
                for (int i = 0; i < numOfBullet; i++) {
                    bullet[i].draw(canvas);
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
