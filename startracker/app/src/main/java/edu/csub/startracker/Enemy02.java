package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Enemy02 implements GameObject{

    private float x, y, ySpeed;
    private float health = 100;
    private final Bitmap enemy;
    private final Bitmap enemy_fast;
    private Bitmap curImage;
    private final int screenWidth, screenHeight, dpi;
    private final int width, height;
    private Paint paint = new Paint();
    private int frameTick = 0, launchTick;


    /**
     * Enemy21 Constructor used to create
     * an enemy that tilts and accelerates
     * it will also be used to pin point where the enemy on screen
     * will be at with its x and y cordinates
     * @param res
     * @param x
     * @param y
     */
    public Enemy02(Resources res, float x, float y){
        dpi = res.getDisplayMetrics().densityDpi;
        screenWidth = res.getDisplayMetrics().widthPixels;
        screenHeight = res.getDisplayMetrics().heightPixels;

        enemy = BitmapFactory.decodeResource(res, R.mipmap.enemy02);
        enemy_fast = BitmapFactory.decodeResource(res, R.mipmap.enemy02_fast);
        curImage = enemy;

        width = curImage.getWidth();
        height = curImage.getHeight();


        this.x = x;
        this.y = y;

        ySpeed = 0.01f * dpi;
        launchTick = new Random().nextInt(120-30) + 30;

    }


    /**
     * update function used
     * to update the fram tick and y position speed
     * of the enemy
     */
    @Override
    public void update() {
        //Start slow wait some time
        frameTick++;

        if(frameTick == launchTick) {
            //switch images and go fast
            curImage = enemy_fast;
            ySpeed *= 4f;
        }

        // move on the Y
        y += ySpeed;


    }

    /**
     * Draw function
     * @param canvas Canvas to draw our enemy
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(curImage,x,y,paint);
    }

    /**
     * getX function
     * @return float x
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * getY function
     * @return float y
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * getWidth function
     * @return float width
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     * getHeight function
     * @return float height
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * isALive function
     * @return float health
     */
    @Override
    public boolean isAlive() {
        return health > 0f;
    }

    /**
     * getHealth function
     * @return float health
     */
    @Override
    public float getHealth() {
        return health;
    }

    /**
     * takeDamage function
     * @param damage float of damage taken
     * @return health minus the damage taken
     */
    @Override
    public float takeDamage(float damage) {
        return health -= damage;
    }

    /**
     * addHealth function
     * @param repairAmount float to add health
     * @return health plus repair amount
     */
    @Override
    public float addHealth(float repairAmount) {
        return health += repairAmount;
    }
}
