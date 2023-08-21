package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EnemySpawner {
    ArrayList<GameObject> enemies;
    float x,y = 0;
    int screenWidth;
    int wave = 1, enemy01Spawned = 0, enemy02Spawned = 0;
    int frameTick = 0, spawnTick, waveTick = 0;
    Resources res;
    private Paint paint = new Paint();

    /**
     * EnemySpawner function used
     * to spawn enemies on to the screen
     * based on width and height of screen
     * @param res
     */
    public EnemySpawner(Resources res){
        enemies = new ArrayList<>();
        screenWidth = res.getDisplayMetrics().widthPixels;
        this.res = res;
        spawnTick = new Random().nextInt(120-60) + 60;
        paint.setColor(Color.WHITE);
        paint.setTextSize(screenWidth * 0.05f);
    }


    /**
     * update function used to update the frame tick by incrementing
     * by spawn ticks ensures performance is meet
     * and will add enemies on screen and updates all enemies
     */
    public  void update(){
        frameTick++;





        //spawning logic
        //wait a given amount of frames
        if(frameTick >= spawnTick) {
            //reset frameTick
            frameTick = 0;
            spawnTick = new Random().nextInt(120-60) + 60;
            //move x to new position
            x = new Random().nextInt((int) (screenWidth * 0.4f - screenWidth * 0.05f)) + screenWidth *  0.5f;
            //choose enemy to spawn
            int tmp = (int)Math.round(Math.random());
            if(tmp == 0 && enemy01Spawned < wave) { //Spawn enemy01
                enemies.add(new Enemy01(res, x, y));
                enemy01Spawned++;
            } else {
                tmp = 1;
            }
            if(tmp == 1 && enemy02Spawned < wave){
                enemies.add(new Enemy02(res,x,y));
                enemy02Spawned++;
            }

        }

        //if all enemies per wave have spawned
        if(enemy01Spawned >= wave && enemy02Spawned >= wave) {
            //increment
            wave++;
        }
        if(waveTick >= 240){
            wave++;
            waveTick = 0;
            enemy01Spawned = 0;
            enemy02Spawned = 0;
        }
        //update all enemies
        for(Iterator<GameObject> iterator = enemies.iterator(); iterator.hasNext();){
            GameObject go = iterator.next();
            go.update();
            if(!go.isAlive()){
                iterator.remove();
            }
        }

    }

    /**
     * draw function
     * @param canvas Canvas draws on screen for our enemy spawner
     */
    public void draw(Canvas canvas){
        canvas.drawText("Wave: " + wave, screenWidth * 0.05f, screenWidth * 0.05f, paint);
        for(GameObject go : enemies){
            go.draw(canvas);
        }
    }

    /**
     * getEnemies
     * @return enemies array list
     */
    public ArrayList<GameObject> getEnemies(){
        return enemies;
    }
}
