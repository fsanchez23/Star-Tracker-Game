package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Laser java class
 * @version Frankie Sanchez
 */

public class Laser implements GameObject{

    private float x, y;
    private Bitmap laser;
    private float dpi;
    private Paint paint = new Paint();
    private float health = 100f;
    private final int width, height;

    /**
     * Laser constructor class
     * @param res uses the resources of game to get appropriate dpi for our bullet
     */

    public Laser(Resources res) {
        laser = BitmapFactory.decodeResource(res, R.mipmap.bullet);
        dpi = res.getDisplayMetrics().densityDpi;
        width = laser.getWidth();
        height = laser.getHeight();
    }

    /**
     * isOnScreen used for determining if a bullet has exited the game view
     * @return height if false
     */

    public boolean isOnScreen(){
        return !(y < getHeight());

    }

    /**
     * update function controls the speed of the laser
     */
    public void update(){
        y -= 0.02f * dpi;


    }

    /**
     * draw function will draw the lasers on screen
     * @param canvas
     */
    public void draw(Canvas canvas){
        canvas.drawBitmap(laser, this.x, this.y, this.paint);

    }

    /**
     * getMidX function will get the middle of the laser width
     * @return middle of laser
     */
    public  float getMidX(){
        return laser.getWidth() / 2f;
    }

    /**
     * getHeight function gets height of laser
     * @return laser height
     */
    @Override
    public  float getHeight(){
        return height;
    }

    @Override
    public boolean isAlive() {
        return health > 0f;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float takeDamage(float damage) {
        return health -= damage;
    }

    @Override
    public float addHealth(float repairAmount) {
        return health += repairAmount;
    }

    /**
     * getX function
     * @return laser x axis position
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * setX function
     * @param x sets the x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * getY function
     * @return gets the y position
     */
    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    /**
     * setY function
     * @param y sets the y axis position
     */
    public void setY(float y) {
        this.y = y;
    }
}
