package edu.csub.startracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Main Driver Class
 * @author Frankie Sanchez
 * @version 1.0.0
 */

public class MainActivity extends AppCompatActivity {
    private HighScore highScore = HighScore.getInstance();

    /**
     * onCreate function used to set our
     * main game activity and it
     * will set the window to our game
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * onResume function used to set highscore, player name,
     * and verify get high score is is moved to post high score
     * if it is higher than current highscore
     */
    @Override
    protected void onResume(){
        super.onResume();
        getTopScores(10);

        TextView tvHighscore = findViewById(R.id.tvHighScore);
        EditText etPlayername = findViewById(R.id.etPlayerName);
        etPlayername.setText(highScore.getPlayerName());
        tvHighscore.setText(String.format("High Score: " + highScore.getHighScore()));
        if(highScore.getHighScore() != 0 && highScore.getHighScore() == highScore.getCurScore()){
            highScore.postHighScore();
        }
    }

    /**
     * getTopScores function used to grab the highest scores
     * and places them in the ListView
     * @param howMany
     */
    private void getTopScores(int howMany) {
        ListView highScores = findViewById(R.id.lvTopScores);
        highScore.getHighScores(howMany, highScores, this);
    }

    /**
     * onPlayerClicked function used to set player name
     * and reset player score
     * @param view used for home ame screen view
     */
    public void onPlayButtonClicked(View view) {
        highScore.resetCurScore();

        EditText etPlayerName = findViewById(R.id.etPlayerName);
        highScore.setPlayerName(etPlayerName.getText().toString());

        startActivity(new Intent(MainActivity.this, GameActivity.class));
    }
}