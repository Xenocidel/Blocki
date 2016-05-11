package com.xc.blocki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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
    String scoreText;
    Level level;
    Player player;
    Background background;
    boolean STOPPED = true;
    int endX; //rightmost position in game coordinates
    ArrayList<Block> blocks = new ArrayList<>(); //contains all blocks except player
    Bullet[] bullet = new Bullet[10];
    int maxNumOfBullet = 10; // you can set the maximum number of bullets
    int numOfBullet = 0;
    Paint p = new Paint();
    //GregorianCalendar cal = new GregorianCalendar();

    public void addBlock(Block block){
        blocks.add(block);
    }

    public Block getBlock(int i){
         return blocks.get(i);
    }

    public void loadGame(int gameLevel){
        player = new Player(200, getHeight()/2, 10, 30, 10, 3, getWidth(), getHeight(), context, this);
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
        scoreText = "Score: "+score;
        player.update(background.x);
        if (gt.getGameState() == GameThread.GameState.OVER){
            return;
        }

        if(!STOPPED) { background.update(); }
        if(!STOPPED){ for (Block block : blocks) { block.update(); } }

        collisionDetection(); //bullet collision detection
        for (int i = 0; i < numOfBullet; i++) {
            //variable xi used here for the initial location of the bullet
            if (player.facingRight){
                bullet[i].xi = player.hitbox.right;
            }
            else{
                bullet[i].xi = player.hitbox.left - bullet[i].margin;
            }
            bullet[i].update(bullet[i].xi, player.hitbox.centerY()-bullet[i].bulletHeight, player.facingRight);
        }

    }


    public void collisionDetection(){
        //if a bullet collides with a non-item block, bullet.isAlive = false
        for(int j=0; j<numOfBullet; j++){
            if (bullet[j].isShooting) {
                RectF b = new RectF(bullet[j].getX(), bullet[j].getY(), bullet[j].getX()+bullet[j].bulletWidth, bullet[j].getY()+bullet[j].bulletHeight);
                bulletDied:
                for (Block block : blocks){
                    if(RectF.intersects(b, block.hitbox)){
                        switch(block.type){
                            case GROUND:
                                Log.i("Bullet", "hit ground");
                                bullet[j].isAlive = false;
                                bullet[j].update(player.getX(), player.getY(), player.facingRight);
                                break bulletDied;
                            case ENEMY:
                                if (block.isAlive) {
                                    Log.i("Bullet", "hit enemy");
                                    bullet[j].isAlive = false;
                                    bullet[j].update(player.getX(), player.getY(), player.facingRight);
                                    block.health--;
                                    break bulletDied;
                                }
                                break;
                        }
                    }
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

                //canvas.drawRect(player.hitbox, p); //for debug
                for (Block block : blocks){
                    if (block.isAlive)
                        block.draw(canvas);
                }
                for (int i = 0; i < numOfBullet; i++) {
                    bullet[i].draw(canvas);
                }
                player.draw(canvas);
                p.setTextSize(getHeight()/12);
                p.setAntiAlias(true);
                p.setColor(Color.BLACK);
                p.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(scoreText, 10, getHeight()/12+5, p);

                break;
            case LOADING:
                break;
            case OVER:
                canvas.drawColor(Color.BLACK);
                p.setTextSize(getHeight()/8);
                p.setAntiAlias(true);
                p.setColor(Color.WHITE);
                p.setTextAlign(Paint.Align.CENTER);
                p.setFakeBoldText(true);
                canvas.drawText("Game Over!", getWidth()/2, getHeight()/2, p);
                p.setTextSize(getHeight()/12);
                canvas.drawText(scoreText, getWidth()/2, getHeight()*3/4, p);
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
