package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Player jave class
 * @version 1.0.0
 */

public class Player implements GameObject {

    private float x, y, prevX, prevY;
    private final Bitmap playerImg;
    private final Bitmap playerLeft;
    private final Bitmap playerRight;
    private  Bitmap curImage;
    private Paint paint;
    private final float dpi;
    private int frameTicks = 0, shotTicks = 0;
    private final Resources res;
    private final int width, height;

    ArrayList<Laser> lasers = new ArrayList<>();
    private float health = 100f;

    /**
     * Player function is our main constructor for player class
     * Uses various metrics to have our player start in a symmetric state
     * and tunes a user's dpi on device
     * @param res
     */
    public Player(Resources res){
        this.res = res;
        playerImg = BitmapFactory.decodeResource(res, R.mipmap.player);
        playerLeft= BitmapFactory.decodeResource(res, R.mipmap.player_left);
        playerRight = BitmapFactory.decodeResource(res, R.mipmap.player_right);

        curImage = playerImg;
        width = curImage.getWidth();
        height = curImage.getHeight();

        DisplayMetrics dm = res.getDisplayMetrics();
        dpi = dm.densityDpi;

        x = (dm.widthPixels / 2f) - (playerImg.getWidth() / 2f);
        y = (dm.heightPixels * 0.75f);
    }

    public void updateTouch(int touchX, int touchY){
        if(touchX > 0 && touchY > 0){
            this.x = touchX - (playerImg.getWidth() / 2f);
            this.y = touchY - (playerImg.getHeight() * 2f);
        }
    }

    /**
     * update function used to update our players every move
     * @param touchX Horizontal x for our screen of player
     * @param touchY Vertical y for our screen of player
     * Updates player by frames and ticks
     * Implements lasers and removes them off screen most importantly
     */
    @Override
    public void update(){
        if(health <= 0) return;
        if(Math.abs(x - prevX) < 0.04 * dpi) {
            frameTicks++;
        } else {
            frameTicks = 0;
        }

        if(this.x < prevX -0.04 * dpi) {
            curImage = playerLeft;
        } else if(this.x > prevX + 0.04 * dpi){
            curImage = playerRight;
        } else if(frameTicks > 5){
            curImage = playerImg;
        }
        prevX = x;
        prevY = y;

        //Increase shotTicks
        shotTicks++;

        // see if we need to shoot
        if(shotTicks >= 25){
            //shoot here
            Laser tmp = new Laser(this.res);
            tmp.setX(x + (playerImg.getWidth() / 2f) - tmp.getMidX());
            tmp.setY(y - tmp.getHeight() /2f);
            lasers.add(tmp);
            //reset the shotTicks
            shotTicks = 0;
        }


        //remove lasers that are off screen
        for(Iterator<Laser> iterator = lasers.iterator(); iterator.hasNext();){
            Laser laser = iterator.next();
            if(!laser.isOnScreen() || !laser.isAlive()){
                iterator.remove();
            }

        }

        //update all lasers
        for(Laser laser : lasers) {
            laser.update();
        }
    }

    /**
     * draw function used to draw our lasers on screen
     * @param canvas game view of game
     * Implements our lasers
     */
    public void draw(Canvas canvas) {
        if(health <= 0) return;
        canvas.drawBitmap(playerImg, this.x, this.y, this.paint);

        //draw all lasers
        for (Laser laser : lasers) {
            laser.draw(canvas);
        }
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

    /**
     * getLasers Array List function
     * @return lasers
     */
    public ArrayList<Laser> getLasers(){
        return lasers;
    }
}
