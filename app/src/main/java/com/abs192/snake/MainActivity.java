package com.abs192.snake;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    SnakeGame snakeGame;
    Button newGameButton, playPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snakeGame = (SnakeGame) findViewById(R.id.surfaceView);
        newGameButton = (Button) findViewById(R.id.buttonNewGame);
        playPauseButton = (Button) findViewById(R.id.buttonPlayPause);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snakeGame.pause();
                //show are you sure dialog

                snakeGame.restart();
            }
        });
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (snakeGame.getCurrentState() == SnakeGame.State.PAUSED) {
                    playPauseButton.setText(R.string.play);
                    snakeGame.resume();
                } else {
                    playPauseButton.setText(R.string.pause);
                    snakeGame.pause();
                }

            }
        });
    }
}
