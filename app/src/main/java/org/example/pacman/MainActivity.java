package org.example.pacman;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    GameView gameView;
    Game game;
    TextView textView;
    TextView pointsView;
    Enemy enemy;
    private Timer myTimer;
    public boolean running = true;
    public int counter = 1000;
    private static MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //saying we want the game to run in one mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.startGame).setOnClickListener(this);
//        findViewById(R.id.pauseGame).setOnClickListener(this);
//        findViewById(R.id.newGame).setOnClickListener(this);
        gameView =  findViewById(R.id.gameView);
        pointsView = findViewById(R.id.points);
        textView = findViewById(R.id.timer);



        game = new Game(this,textView);
        game.setGameView(gameView);
        gameView.setGame(game);

        game.newGame();

        //listener of our pacman, when somebody clicks it
        Button buttonUp = findViewById(R.id.moveUp);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setCoinDirection("up");
            }
        });

        Button buttonDown = findViewById(R.id.moveDown);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setCoinDirection("down");
            }
        });

        Button buttonLeft = findViewById(R.id.moveLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setCoinDirection("left");
            }
        });

        Button buttonRight = findViewById(R.id.moveRight);
        buttonRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.setCoinDirection("right");
            }
        });

        //Make a timer
        myTimer = new Timer();
        running = true;
        myTimer.schedule(new TimerTask() {
                             @Override
                             public void run() {
                                 TimerMethod();
                             }

                         }, 0,50);

        //Add background music
        music = MediaPlayer.create(this,R.raw.pacmusic);
        music.setVolume(100,100);
        music.setLooping(true);
        //music.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //just to make sure if the app is killed, that we stop the timer.
        myTimer.cancel();
        music.pause();

    }

    public boolean startMusic() {
        music.start();
        return true;
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            if (running) {
                counter--;
                pointsView.setText("Points: "+game.getPoints());
                textView.setText("Timer:" +counter);
                game.moveEnemy();
                game.move();
                startMusic();
                if (game.Enemycheck() == true){
                    running = false;
                    gameEnemyOver();
                }
                if (counter == 0) {
                    running = false;
                    gameOver();
                    gameView.invalidate();
                }
                if (game.coins.isEmpty()){
                    gameWon();
                    gameView.invalidate();
                    running = false;
                }

            }
        }
    };

    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }

    private void gameWon() {
        Toast.makeText(this,"You win!! Congratulations.", Toast.LENGTH_LONG).show();
    }

    public void gameOver() {
        Toast.makeText(this,"Game over!!You run out of time.", Toast.LENGTH_LONG).show();
        music.pause();
    }
    public void gameEnemyOver() {
        Toast.makeText(this,"Game over!!The ghost has eaten you.", Toast.LENGTH_LONG).show();
        music.pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this,"settings clicked",Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.newGame) {
            counter = 1000;
            running = true;
            //Clear the old arrays
            game.coins.clear();
            music.start();

            textView.setText("Timer: "+counter);
            //return true;
            if(game != null){
                game.newGame();
            }
        } else if (id == R.id.pauseGame) {
            running = false;
            music.pause();

        } else if (id == R.id.startGame) {
            running = true;
            music.start();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
    }
}