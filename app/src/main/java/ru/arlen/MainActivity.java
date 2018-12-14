package ru.arlen;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends Activity {
    private static final String GAME_PLAYED = "GAME_PLAYED";
    private static final String BALL_PLACE = "BALL_PLACE";
    private static final int MAX_DRAWABLE_LEVEL = 10000;
    private static final int MIN_DRAWABLE_LEVEL = 0;
    private View newGameBtn;
    private ImageView leftBox, centerBox, rightBox;

    private int ballPlace = 0;
    private boolean gamePlayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newGameBtn = findViewById(R.id.btn);
        leftBox = findViewById(R.id.imageLeft);
        leftBox.setTag(1);
        centerBox = findViewById(R.id.imageCenter);
        centerBox.setTag(2);
        rightBox = findViewById(R.id.imageRight);
        rightBox.setTag(3);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userChoice = (int) v.getTag();
                gamePlayed = true;
                showBall(ballPlace, userChoice);
            }
        };
        leftBox.setOnClickListener(onClickListener);
        centerBox.setOnClickListener(onClickListener);
        rightBox.setOnClickListener(onClickListener);

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePlayed = false;
                ballPlace = new Random().nextInt(2) + 1;
                leftBox.getDrawable().setLevel(MIN_DRAWABLE_LEVEL);
                centerBox.getDrawable().setLevel(MIN_DRAWABLE_LEVEL);
                rightBox.getDrawable().setLevel(MIN_DRAWABLE_LEVEL);
            }
        });

        if (savedInstanceState != null && savedInstanceState.getBoolean(GAME_PLAYED)) {
            gamePlayed = savedInstanceState.getBoolean(GAME_PLAYED);
            ballPlace = savedInstanceState.getInt(BALL_PLACE);
            showBall(ballPlace, ballPlace);
        } else
            ballPlace = new Random().nextInt(2) + 1;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (gamePlayed) {
            outState.putBoolean(GAME_PLAYED, gamePlayed);
            outState.putInt(BALL_PLACE, ballPlace);
        }
    }

    private void showBall(int ballPlace, int userChoice) {
        long delay = ballPlace == userChoice ? 0 : 2000;
        final ImageView view;
        switch (ballPlace) {
            case 1:
                view = leftBox;
                break;
            case 2:
                view = centerBox;
                break;
            case 3:
                view = rightBox;
                break;
            default:
                throw new IllegalArgumentException("Невозможное положение шарика!");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.getDrawable().setLevel(MAX_DRAWABLE_LEVEL);
            }
        }, delay);
    }
}
