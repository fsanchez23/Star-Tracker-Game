package edu.csub.startracker;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.heartbeatinfo.DefaultHeartBeatInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class HighScore {
    private static final HighScore INSTANCE = new HighScore();
    private int curScore = 0;
    private int highScore = 0;
    private String name = "Player 1";
    private FirebaseFirestore db;

    /**
     * Highscore constructor class
     * Sets the fireStore database instance
     */
    private HighScore(){
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Highscorre getInstance function
     * used to set instance
     * @return private static final Instance
     */
    public static HighScore getInstance(){
        return INSTANCE;
    }

    /**
     * addScore function used to  increment score
     * and set highscore to current score if
     * current score is greater than highscore
     * @param score integer used to track score
     */
    public void addScore(int score){
        curScore += score;
        if(curScore > highScore){
            highScore = curScore;
        }
    }


    /**
     * getCurScore function
     * @return int curScore
     */
    public int getCurScore() {
        return curScore;
    }

    /**
     * getHighScore function
     * @return int highscore
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * resetCurScore function
     * sets curScore to 0
     */
    public void resetCurScore() {
        curScore = 0;
    }

    /**
     * setPlayerName function sets the name to player name
     * @param playerName string
     */
    public void setPlayerName(String playerName) {
        name = playerName;
    }

    /**
     * getPlayerName function
     * @return string name
     */
    public String getPlayerName() {
        return name;
    }


    /**
     * getHighScores function
     * Function creates an ArrayList of tmp scores
     * Database will be listening for any scores that are the highest scres
     * and will add it to array list as a list
     * @param howMany
     * @param highScores
     * @param context
     */
    public void getHighScores(int howMany, ListView highScores, Context context){
        ArrayList<String> topScores = new ArrayList<>();


        db.collection("HighScore")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(howMany)

            .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                String tmpString = String.format("%s: %s ", doc.getId(),
                                        doc.get("score"));
                                topScores.add(tmpString);
                                Log.d("SCORE", tmpString);
                            }
                            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(context,
                                    android.R.layout.simple_list_item_1, topScores);
                            highScores.setAdapter(itemsAdapter);
                        }
                    }
                });

    }

    /**
     * postHighScore function used
     * to post the high scores of the game and post their name
     * and score on to the database
     */
    public void postHighScore() {
        Map<String, Integer> hs = new HashMap<>();
        hs.put(name, highScore);

        db.collection("HighScore").document(name)
                .set(hs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("data", name + "'s score was set");

                    }
                });

    }
}
