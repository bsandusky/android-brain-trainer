package com.brettsandusky.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerDisplay;
    TextView scoreDisplay;
    TextView promptText;
    Button controlButton;
    CountDownTimer timer;
    GridLayout gridLayout;
    Boolean gameIsActive = false;

    public void resetGame() {
        gameIsActive = false;
        timerDisplay.setText(":30");
    }

    public ArrayList<Integer> generateRandom(int min, int max, int times) {

        Random r = new Random();
        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = 0; i<times; i++) {
             arr.add(r.nextInt(max - min + 1) + min);
        }

        return arr;
    }

    public void setBoard() {
        ArrayList round = generateRandom(1,100,5);
        ArrayList randomTile = generateRandom(0,3,1);
        int answer = (int) round.get(0) + (int) round.get(1);
        int tile = (int) randomTile.get(0);


        promptText.setText(String.format("%d + %d", round.get(0), round.get(1)));
        TextView t1 = (TextView) gridLayout.getChildAt(tile);

        t1.setText(Integer.toString(answer));


    }

    public void handleControlButtonClick(View v) {
        gameIsActive = true;
        setBoard();
        timer = new CountDownTimer(30000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished/1000;
                timerDisplay.setText(String.format(":%02d", seconds));
            }

            @Override
            public void onFinish() {
                //resetGame();
            }
        };

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlButton = (Button) findViewById(R.id.controlButton);
        timerDisplay = (TextView) findViewById(R.id.timerDisplay);
        scoreDisplay = (TextView) findViewById(R.id.scoreDisplay);
        promptText = (TextView) findViewById(R.id.promptText);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

    }
}
