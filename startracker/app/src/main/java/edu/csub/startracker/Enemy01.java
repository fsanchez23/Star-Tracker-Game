package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy01 implements GameObject{
    private final float dpi;
    private float x, y, ySpeed;
    private final float width, height;
    private float health = 100;
    private final Bitmap enemy, enemy_left, enemy_right;
    private Bitmap curImage;
    private  int screenWidth, screenHeight;
    private Paint paint = new Paint();

    /**
     * Enemy01 Constructor used to create
     * an enemy that tilts and accelerates
     * it will also be used to pin point where the enemy on screen
     * will be at with its x and y cordinates
     * @param res
     * @param x
     * @param y
     */
    public Enemy01(Resources res, float x, float y){
        this.x = x;
        this.y = y;
        enemy = BitmapFactory.decodeResource(res, R.mipmap.enemy01);
        enemy_left = BitmapFactory.decodeResource(res, R.mipmap.enemy01_left);
        enemy_right = BitmapFactory.decodeResource(res, R.mipmap.enemy01_right);
        curImage = enemy;
        width = curImage.getWidth();
        height = curImage.getHeight();
        dpi = res.getDisplayMetrics().densityDpi;
        screenHeight = res.getDisplayMetrics().heightPixels;
        screenWidth = res.getDisplayMetrics().widthPixels;
        ySpeed = 0.02f * dpi;
    }

    /**
     * update function
     * used to update the screen and enemy(x,y)
     * position by the
     * absolute value of the current image
     */
    @Override
    public void update() {
        float xOff = (float) (0.01f * screenWidth * Math.sin(y/(0.04f * screenHeight)));
        x += xOff;
        y += ySpeed;

        curImage = xOff > 0 ? enemy_left: enemy_right;
        if(Math.abs(xOff) < 2) curImage = enemy;

    }

    /**
     * Draw function  used to draw current image
     * x, y, and paint
     * @param canvas Canvas view
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
