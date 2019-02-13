package com.nmeunier.peppertictactoe;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //    Pepper Player = 0, NAO Player = 1
    int player = 0;
    boolean gameIsActive = true;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {

//        Load the views
        ImageView counter = (ImageView) view;
        ImageView pepperPlayer = (ImageView) findViewById(R.id.pepperPlayer);
        ImageView naoPlayer = (ImageView) findViewById(R.id.naoPlayer);

//        Get the tags of the 3 by 3 positions
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
//        If the cell is empty and the game is active
        if (gameState[tappedCounter] == 2 && gameIsActive) {
//            The cell becomes the current player cell
            gameState[tappedCounter] = player;
            Log.i("Game State", "Position " + tappedCounter + ": " + player);

            counter.setTranslationY(-1000f);

//            Select the next player
            if (player == 0) {
                counter.setImageResource(R.drawable.pepper_head);
                player = 1;
                naoPlayer.setAlpha(1f);
                pepperPlayer.setAlpha(0.1f);
            } else {
                counter.setImageResource(R.drawable.nao_head);
                player = 0;
                naoPlayer.setAlpha(0.1f);
                pepperPlayer.setAlpha(1f);
            }

//            Drop the image in the cell
            counter.animate().translationYBy(1000f).rotation(360).setDuration(200);
//            Test if there is a winner
            winner();
        }
    }

    public void winner() {
//        For all the winning combinations
        for (int[] winPos: winningPositions) {
//            If the winning combination contains the same number in all the cells and not empty (2)
            if (gameState[winPos[0]] == gameState[winPos[1]]
                    && gameState[winPos[0]] == gameState[winPos[2]]
                    && gameState[winPos[0]] != 2) {
//                Stop the game
                gameIsActive = false;

//                Load the views
                LinearLayout winLayout = (LinearLayout) findViewById(R.id.winnerLayout);
                TextView winMessage = (TextView) findViewById(R.id.winnerTextView);
                ImageView pepperPlayer = (ImageView) findViewById(R.id.pepperPlayer);
                ImageView naoPlayer = (ImageView) findViewById(R.id.naoPlayer);
                android.support.v7.widget.GridLayout grid =
                        (android.support.v7.widget.GridLayout) findViewById(R.id.grid);

//                Fade the grid
                grid.setAlpha(0.1f);

//                Select the message according to the winner
                if (gameState[winPos[0]] == 0) {
                    Log.i("Game State", "Pepper won!");
                    pepperPlayer.setAlpha(1f);
                    naoPlayer.setAlpha(0.1f);
//                    Toast.makeText(MainActivity.this, "Pepper won!", Toast.LENGTH_LONG).show();
                    winMessage.setText("Pepper won!");
                } else {
                    Log.i("Game State", "NAO won!");
                    pepperPlayer.setAlpha(0.1f);
                    naoPlayer.setAlpha(1f);
//                    Toast.makeText(MainActivity.this, "NAO won!", Toast.LENGTH_LONG).show();
                    winMessage.setText("NAO won!");
                }

//                Display the win layout
                winLayout.setVisibility(View.VISIBLE);
            }
        }
        if (gameIsActive) {
            draw();
        }
    }

    public void draw() {
        boolean gameIsOver = true;
        for (int position: gameState) {
            if (position == 2) {
                gameIsOver = false;
            }
        }
        if (gameIsOver) {

            LinearLayout winLayout = (LinearLayout) findViewById(R.id.winnerLayout);
            TextView winMessage = (TextView) findViewById(R.id.winnerTextView);
            ImageView pepperPlayer = (ImageView) findViewById(R.id.pepperPlayer);
            ImageView naoPlayer = (ImageView) findViewById(R.id.naoPlayer);
            android.support.v7.widget.GridLayout grid =
                    (android.support.v7.widget.GridLayout) findViewById(R.id.grid);

            grid.setAlpha(0.1f);

            naoPlayer.setAlpha(0.1f);
            pepperPlayer.setAlpha(0.1f);

            Log.i("Game State", "It's a draw!");
//            Toast.makeText(MainActivity.this, "It's a draw!", Toast.LENGTH_LONG).show();
            winMessage.setText("It's a draw!");
            winLayout.setVisibility(View.VISIBLE);
        }

    }

    public void reset(View view) {
        // RESET VIEWS

        LinearLayout winLayout = (LinearLayout) findViewById(R.id.winnerLayout);
        TextView winMessage = (TextView) findViewById(R.id.winnerTextView);
        ImageView pepperPlayer = (ImageView) findViewById(R.id.pepperPlayer);
        ImageView naoPlayer = (ImageView) findViewById(R.id.naoPlayer);
        android.support.v7.widget.GridLayout grid =
                (android.support.v7.widget.GridLayout) findViewById(R.id.grid);

//                Fade the grid
        grid.setAlpha(1f);

        naoPlayer.setAlpha(0.1f);
        pepperPlayer.setAlpha(1f);
        winLayout.setVisibility(View.INVISIBLE);
        player = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        for (int i = 0; i < grid.getChildCount(); i++) {
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }
        gameIsActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
