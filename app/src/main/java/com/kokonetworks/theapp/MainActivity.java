package com.kokonetworks.theapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private Field field;
    private TextView tvLevel;
    private TextView tvScore;

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field = findViewById(R.id.field);
        tvLevel = findViewById(R.id.tvLevel);
        btnStart = findViewById(R.id.btnStart);
        tvScore = findViewById(R.id.tvScore);

        setEventListeners();
    }

    void setEventListeners(){
        btnStart.setOnClickListener(view -> {
            btnStart.setVisibility(View.GONE);
            tvScore.setVisibility(View.GONE);
            field.startGame();
        });

        field.setListener(listener);
    }

    void updateScore(int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvScore.setVisibility(View.VISIBLE);
                tvScore.setText(String.format(getString(R.string.your_score), score));
            }
        });

    }

    /*private String updateLeaderBoard(int score){

        HashMap map = new HashMap( );

        int size = map.size();
        if(size == 10)
        {
            map.put(size + "9", score);
        }else
        {
            map.put(size + "1", score);
        }
        return map.values().toString();

    }
    private void setLeaderBoard(int score){
        SharedPreferences sharedPreferences = getSharedPreferences("WHACAMOCK",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        if(sharedPreferences.contains("LeaderBoard")){
            String leaderBoard = sharedPreferences.getString("LeaderBoard", null);
        }
        else {
            myEdit.putString("LeaderBoard", updateLeaderBoard(score))
        }

        myEdit.apply();
    }*/

    private final Field.Listener listener = new Field.Listener() {

        @Override
        public void onGameEnded(int score) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnStart.setVisibility(View.VISIBLE);
                    tvScore.setVisibility(View.VISIBLE);
                    updateScore(score);
                }
            });
        }

        @Override
        public void onLevelChange(int level) {
            tvLevel.setText(String.format(getString(R.string.level), level));
        }

        @Override
        public void updateScore(int currentScore) {
            Log.d("Whac A Mole ", "currentScore " + currentScore);
            MainActivity.this.updateScore(currentScore);
        }
    };
}