package com.xc.blocki;

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
                return  "                                                  \n"+
                        "                                                  \n"+
                        "                                gg                \n"+
                        "g                  1           ggg                \n"+
                        "g     1    1     gggg         gggg   1   1 1  1 1 \n"+
                        "gggggggggggggggggggggggggggggggggggggggggggggggggg\n";
            case 2:
                return  "                                                  \n"+
                        "                                                  \n"+
                        "                                gg                \n"+
                        "g                  1           ggg                \n"+
                        "g     1    1     gggg         gggg   1   1 1  1 1 \n"+
                        "gggggggggggggggggggggggggggggggggggggggggggggggggg\n";
            case 3:
                return  "                                                  \n"+
                        "                                                  \n"+
                        "                                gg                \n"+
                        "g                  1           ggg                \n"+
                        "g     1    1     gggg         gggg   1   1 1  1 1 \n"+
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
                    x=0;
                    break;
                case ' ':
                    break;
                case 'g':
                    //gameView.addBlock(new Ground(x*BLOCK_WIDTH, y*BLOCK_HEIGHT));
                    break;
                case '1':
                    //gameView.addBlock(new Enemy1(x*BLOCK_WIDTH, y*BLOCK_HEIGHT, 5, 5, 5, 1));
                    break;
            }
            x++;
        }
    }
}
