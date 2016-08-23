package com.brettsandusky.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerDisplay;
    TextView scoreDisplay;
    TextView promptText;
    TextView gameEndText;
    Button controlButton;
    CountDownTimer timer;
    GridLayout gridLayout;
    Boolean gameIsActive = false;
    int tile;
    int scoreCount = 0;
    int totalAnswered = 0;

    public void resetGame() {
        gameIsActive = false;
        timer.cancel();
        controlButton.setText(R.string.start);
        timerDisplay.setText(R.string.thirty_seconds);
        scoreDisplay.setText(R.string.zero_of_zero);
        promptText.setText(R.string.nine_plus_six);
        gameEndText.setVisibility(View.INVISIBLE);
        promptText.setVisibility(View.VISIBLE);
        scoreCount = 0;
        totalAnswered = 0;

        for (int j=0; j<gridLayout.getChildCount(); j++) {
            TextView x = (TextView) gridLayout.getChildAt(j);
            x.setText(R.string.fifteen);
        }
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
        scoreDisplay.setText(String.format("%d/%d",scoreCount,totalAnswered));
        ArrayList round = generateRandom(1,100,2);
        ArrayList randomTile = generateRandom(0,3,1);
        ArrayList others = generateRandom(1,200,4);
        int answer = (int) round.get(0) + (int) round.get(1);
        tile = (int) randomTile.get(0);

        TextView t1 = (TextView) gridLayout.getChildAt(tile);
        t1.setText(Integer.toString(answer));
        promptText.setText(String.format("%d + %d",round.get(0),round.get(1)));

        for (int j=0; j<4; j++) {

            if (j == tile) {
                continue;
            }
            TextView x = (TextView) gridLayout.getChildAt(j);
            x.setText(Integer.toString((int) others.get(j)));
        }
    }

    public void handleTileClick(View v) {
        if (v.getTag().equals(String.valueOf(tile))) {
            scoreCount++;
        }
        totalAnswered++;
        setBoard();
    }

    public void handleControlButtonClick(View v) {

        if(!gameIsActive) {
            gameIsActive = true;
            controlButton.setText(R.string.reset);
            setBoard();

            for (int j=0; j<gridLayout.getChildCount(); j++) {
                TextView x = (TextView) gridLayout.getChildAt(j);
                x.setEnabled(true);
            }

            timer = new CountDownTimer(30000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) millisUntilFinished/1000;
                    timerDisplay.setText(String.format(":%02d", seconds));
                }

                @Override
                public void onFinish() {
                    timerDisplay.setText(":00");
                    promptText.setVisibility(View.INVISIBLE);
                    gameEndText.setText(String.format(getResources().getString(R.string.correct), scoreCount));
                    gameEndText.setVisibility(View.VISIBLE);

                    for (int j=0; j<gridLayout.getChildCount(); j++) {
                        TextView x = (TextView) gridLayout.getChildAt(j);
                        x.setEnabled(false);
                    }
                }
            }.start();

        } else {
            resetGame();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlButton = (Button) findViewById(R.id.controlButton);
        timerDisplay = (TextView) findViewById(R.id.timerDisplay);
        scoreDisplay = (TextView) findViewById(R.id.scoreDisplay);
        gameEndText = (TextView) findViewById(R.id.gameEndText);
        promptText = (TextView) findViewById(R.id.promptText);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

    }
}
