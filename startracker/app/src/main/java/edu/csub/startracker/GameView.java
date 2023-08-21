package edu.csub.startracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * GameView java class for our game
 * Contains background and images of our game
 * @version 1.0.0
 */

public class GameView extends SurfaceView implements Runnable {

    private final Background background1;
    private final Background background2;
    private boolean isPlaying = true;
    private Thread thread;
    private int touchX, touchY;
    private ArrayList<Laser> lasers;
    private ArrayList<GameObject> enemies;

    private final Player player;

    private EnemySpawner spawner;
    private GameActivity gameActivity;
    private final float screenWidth, screenHeight;
    private Paint textPaint = new Paint();
    private Paint highScorePaint = new Paint();
    private HighScore highScore = HighScore.getInstance();


    /**
     * GameView function used for our backgrounds of our game
     * @param context context of game view
     * @param screenX Horizontal x of screen
     * @param screenY vertical y of screen
     */


    public GameView(GameActivity context,int screenX, int screenY) {
        super(context);

        Resources res = getResources();
        screenWidth = res.getDisplayMetrics().widthPixels;
        screenHeight = res.getDisplayMetrics().heightPixels;
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(screenWidth * 0.1f);
        highScorePaint.setColor(Color.WHITE);
        highScorePaint.setTextSize(screenWidth * 0.04f);

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.setY(screenY);

        player = new Player(res);
        spawner = new EnemySpawner(res);

        lasers = player.getLasers();
        spawner = new EnemySpawner(res);

        lasers = player.getLasers();
        enemies = spawner.getEnemies();

        gameActivity = context;

    }
    /**
     * onTouchEvent function used for play button
     * @param event when clicked will start game
     * @return true
     */

    @Override
    public boolean onTouchEvent(MotionEvent event){
        touchX = (int)event.getX();
        touchY = (int) event.getY();
        return true;
    }

    /**
     * Run function used to keep the game in loop for running
     * Uses 3 main functions
     * update(), draw(), and sleep()
     */
    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }

    }

    /**
     * update function used for the constant interactions of our game
     * Updates by pixels
     */

    private void update() {
        background1.update();
        background2.update();
        player.updateTouch(touchX, touchY);
        player.update();
        spawner.update();
        checkAllCollisions();
        checkEnemiesOffScreen();

    }

    /**
     * checkEnemiesOffScreen function
     * used to check if the enemies are off the
     * screen based on y position and screen
     * height and if enemy is taken off screen
     * player dies and game is over
     */
    private void checkEnemiesOffScreen() {
        for(GameObject go : enemies){
            if(go.getY() > screenHeight){
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }

    /**
     * CheckAllCollisions function
     *  used to check if lakers are hitting the enemeis
     *  if player collides with the enemies gplayer ends
     *  and game ends
     */
    private void checkAllCollisions() {
        for(Laser laser : lasers){
            for(GameObject go : enemies){
                if(checkCollision(laser, go)){
                    laser.takeDamage(100);
                    go.takeDamage(25);
                    highScore.addScore(25);
                }
            }
        }

        for(GameObject go : enemies){
            if(checkCollision(player, go)){
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }


    /**
     * checkCollisions function
     * @param g1 x,y positions
     * @param g2 x,y positions
     * @return
     */
    private boolean checkCollision(GameObject g1, GameObject g2){
        return g1.getX() < g2.getX() + g2.getWidth() &&
                g1.getX() + g1.getWidth() > g2.getX() &&
                g1.getY() < g2.getY() + g2.getHeight() &&
                g1.getY() + g1.getHeight() > g2.getY();
    }

    /**
     * draw function used to draw the canvas of our game
     * such as background and player
     */

    private  void draw(){
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            background1.draw(canvas);
            background2.draw(canvas);

            if(!player.isAlive()){
                canvas.drawText("GAME OVER", screenWidth/4f, screenHeight /2f, textPaint);
            }

            canvas.drawText(String.format("Score: %s" ,highScore.getCurScore()), screenWidth * 0.02f,
                    screenHeight * 0.06f , highScorePaint);

            player.draw(canvas);
            spawner.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    /**
     * sleep function used to keep our frames in check close to 60fps
     */
    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * pause function used for pausing our game when needed and used a thread for processing a pause
     */

    public void pause(){
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * resume function used for resuming the game
     * thread will start and game will resume
     */
    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }


}
