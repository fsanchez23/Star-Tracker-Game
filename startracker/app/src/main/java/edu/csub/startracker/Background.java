package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Background java class
 * @version 1.0.0
 */
public class Background {

    private Bitmap background;
    private int screenX, screenY;

    private Paint paint = new Paint();
    private float dpi;

    private float x = 0f;
    private float y = 0f;

    /**
     * Background function used to optimize our background
     * @param screenX horizontal x axis scree
     * @param screenY vertical y axis screen
     * @param res resources for our background
     */

    public Background(int screenX, int screenY, Resources res){
        this.screenX = screenX;
        this.screenY = screenY;
        this.background = BitmapFactory.decodeResource(res, R.mipmap.background);
        this.background = Bitmap.createScaledBitmap(this.background, screenX, screenY, false);
        this.dpi = res.getDisplayMetrics().densityDpi;

    }

    /**
     * getX function
     * @return x axis position
     */
    public float getX() {
        return x;
    }

    /**
     * getY function
     * @return y axis position
     */
    public float getY() {
        return y;
    }

    /**
     * setY function
     * @param y sets the y axis position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * setX function
     * @param x sets the x axis position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Update function used to update our background constantly
     * Essentially moves stars in background
     */
    public void update() {
        this.y += 0.006f * dpi;

        if(this.y > screenY){
            this.y = -screenY;
        }
    }

    /**
     * draw function used to animate our background
     * @param canvas game view animation for our game
     */

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.background, this.x, this.y, paint);
    }
}
