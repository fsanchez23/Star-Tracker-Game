package edu.csub.startracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

/**
 * Main driver for a11
 * @author  Frankie Sanchez
 * @version 1.0.0
 */

public class GameActivity extends AppCompatActivity {
    private GameView gameView;

    /**
     * Function used for our content on screen
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);

        setContentView(gameView);

    }
    public void gameOver(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 6000);
    }

    /**
     * onPause function used for pausing the game if exited
     */
    @Override
        protected void onPause(){
            super.onPause();
            gameView.pause();
        }

    /**
     * onResume function used for resuming game
     */

    @Override
        protected void onResume() {
            super.onResume();
            gameView.resume();
        }
}