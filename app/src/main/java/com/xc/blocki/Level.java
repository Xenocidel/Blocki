package com.xc.blocki;

import android.content.Context;

/**
 * Created by Aaron on 2016-05-04.
 */
public class Level {
    GameView gameView;
    final int BLOCK_WIDTH;
    final int BLOCK_HEIGHT;

    public Level(GameView gameView){
        this.gameView = gameView;
        BLOCK_HEIGHT = gameView.getHeight()/6; //all maps are 6 blocks high
        BLOCK_WIDTH = gameView.getWidth()/10;
    }

    public String createLevel(int level){
        switch(level){
            case 1:
                return  "                   g\n"+
                        " p                 g\n"+
                        "                   g\n"+
                        "gg  g              g\n"+
                        "gg       1  1 f    g\n"+
                        "gggggggggggggggggggg\n";

            case 2:
                return  "          gg   1  \n"+
                        "     gg           \n"+
                        "                  \n"+
                        "                  \n"+
                        "  p       1  g  11\n"+
                        "gggggggggggggggggg\n";

            case 3:
                return  "                                                  \n"+
                        "                                                  \n"+
                        "                                gg                \n"+
                        "g                  1           ggg                \n"+
                        "g p   1    1     gggg         gggg   1   1 1  1 1 \n"+
                        "gggggggggggggggggggggggggggggggggggggggggggggggggg\n";
        }
        return null;
    }

    public void loadLevel(int level){
        String map = createLevel(level);
        int x=0;
        int y=0;
        for (int i = 0; i < map.length(); i++){
            char tmp = map.charAt(i);
            switch (tmp){
                case '\n':
                    y++;
                    gameView.endX = x*BLOCK_WIDTH;
                    x=0;
                    break;
                case ' ':
                    x++;
                    break;
                case 'f':
                    gameView.addBlock(new FinishLine(x*BLOCK_WIDTH, y*BLOCK_HEIGHT, gameView.getWidth(), gameView.getHeight(), gameView.context, gameView));
                    x++;
                    break;
                case 'g':
                    gameView.addBlock(new Ground(x*BLOCK_WIDTH, y*BLOCK_HEIGHT,
                            gameView.getWidth(), gameView.getHeight(), gameView.context));
                    x++;
                    break;
                case 'p':
                    gameView.player = new Player(x*BLOCK_WIDTH, y*BLOCK_HEIGHT, 10, 10, 10, 1, gameView.getWidth(), gameView.getHeight(), gameView.context, gameView);
                    //todo: instead of creating a new player, implement a "move player" to avoid null pointers
                    x++;
                    break;
                case '1':
                    gameView.addBlock(new Enemy1(x*BLOCK_WIDTH, y*BLOCK_HEIGHT, 1, 1, 10, 1,
                            gameView.getWidth(), gameView.getHeight(), gameView.context, gameView));
                    x++;
                    break;
            }
        }
    }
}
