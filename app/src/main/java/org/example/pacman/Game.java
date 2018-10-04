package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.Comparator;
import java.util.Arrays;
/**
 *
 * This class should contain all your game logic
 */
public class Game extends GoldCoin implements OnClickListener {
    //context is a reference to the activity
    private Context context;
    public int points = 0; //how points do we have
    //bitmap of the pacman
    private Bitmap pacBitmapRight;
    private Bitmap pacBitmapLeft;
    private Bitmap pacBitmapUp;
    private Bitmap pacBitmapDown;


    private Bitmap enemy;
    private int enemyx, enemyy;
    //textview reference to points
    private TextView pointsView;
    private int pacx, pacy;

    //the list of goldcoins - initially empty
    public ArrayList<GoldCoin> coins = new ArrayList<GoldCoin>();
    //The time used for the pacman
    private Timer myTimer;
    private int counter = 0;
    private GameView gameView;
    MainActivity mainActivity;
    private int h,w; //height and width of screen
    TextView textView;
    public String pacDirection = "right";
    int count = 0;



    public Game(Context context, TextView view) {
        this.context = context;
        this.pointsView = view;
        pacBitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanright);
        pacBitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanleft);
        pacBitmapUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanup);
        pacBitmapDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmandown);
        enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);

    }

    public void setGameView(GameView view)
    {
        this.gameView = view;
    }

    //TODO initialize goldcoins also here
    public void newGame()
    {
        pacx = 20;
        pacy = 800;
        enemyy = 700;
        enemyx = 700;
        //reset the points
        points = 0;
        pointsView.setText(context.getResources().getString(R.string.points)+" "+points);
        gameView.invalidate(); //redraw screen
        //getCoins();
        GoldCoin();

    }


    public void setSize(int h, int w)
    {
        this.h = h;
        this.w = w;
    }


    public void movePacmanUp(int pixels)
    {
        if(pacy+pixels+pacBitmapRight.getHeight()> 200 ) {
            pacy = pacy - pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanDown(int pixels)
    {
        if (pacy+pixels+pacBitmapRight.getHeight()<h) {
            pacy = pacy + pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanLeft(int pixels)
    {
        if (pacx+pixels+pacBitmapRight.getWidth()> 200) {
            pacx = pacx - pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanRight(int pixels)
    {
        if (pacx+pixels+pacBitmapRight.getWidth()<w) {
            pacx = pacx + pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void moveEnemyUp (int pixels) {
        if (enemyy+pixels+enemy.getWidth()> 200){
            enemyy = enemyy - pixels;
            doCollisionCheck();
        }
    }

    public void moveEnemyRight (int pixels) {
        if (enemyx+pixels+enemy.getWidth()< w){
            enemyx = enemyx + pixels;
            doCollisionCheck();
        }
    }

    public void moveEnemyLeft (int pixels) {
        if (enemyx+pixels+enemy.getWidth()> 200){
            enemyx = enemyx - pixels;
            doCollisionCheck();
        }
    }

    public void moveEnemyDown (int pixels) {
        if (enemyy+pixels+enemy.getWidth()< h){
            enemyy = enemyy + pixels;
            doCollisionCheck();
        }
    }

    //Move the coin
    public void move (){
        if (this.pacDirection == "right") {
            movePacmanRight(14);
        }
        if (this.pacDirection == "left") {
            movePacmanLeft(14);
        }
        if (this.pacDirection == "up") {
            movePacmanUp(14);
        }
        if (this.pacDirection == "down") {
            movePacmanDown(14);
        }
    }

    public void moveUp(){
            moveEnemyUp(8);
    }

    public void moveRight(){
            moveEnemyRight(8);
    }

    public void moveDown(){
            moveEnemyDown(8);
    }

    public void moveLeft(){
            moveEnemyLeft(8);
    }

    public void moveEnemy()
    {
        Random randomGen = new Random();
        //random nr between 0 and 3
        int rndNumber = randomGen.nextInt(60);
        switch (rndNumber) {
            case 0:
                for (count = 0; count < 10; count++){
                moveUp();}
                break;
            case 1:
                for (count = 0; count < 10; count++){
                moveLeft();}
                break;
            case 2:
                for (count = 0; count < 10; count++) {
                moveRight();}
                break;
            case 3:
                for (count = 0; count < 10; count++){
                moveDown();}
                break;
        }
    }

    public void setCoinDirection (String coinDirection){
        this.pacDirection = coinDirection;
    }


    public void GoldCoin () {
        Bitmap coin = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin);

        for (int i= 0; i < 10; i++){
            coins.add(new GoldCoin(coin));
        }
    }
    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    public void doCollisionCheck() {
        int px = this.getPacx();
        int py = this.getPacy();
        int ex = this.getEnemyx();
        int ey = this.getEnemyy();

        GoldCoin[] coins2 = coins.toArray(new GoldCoin[0]);
        Arrays.sort(coins2, new Comparator<GoldCoin>() {
            @Override
            public int compare(GoldCoin o1, GoldCoin o2) {
                return o1.compareTo(o2);
            }
        });
        for (GoldCoin c : coins2) {
            int cx = c.getRndX();
            int cy = c.getRndY();
            int m = (int) Math.pow(cx - px, 2);
            int n = (int) Math.pow(py - cy, 2);
            int d = (int) Math.sqrt(m + n);
            // max distance = sqrt( pac width ^ 2 + coin height ^ 2)

            // max distance = sqrt(80^2 + 60 ^ 2)
            if (d <= 150) {
                c.setTaken(true);
                coins.remove(c);
                addPoints();
                //mainActivity.eatingMusic();
              }
        }
    }

    public boolean Enemycheck () {
        //for Enemy
        int px = this.getPacx();
        int py = this.getPacy();
        int ex = this.getEnemyx();
        int ey = this.getEnemyy();
        int em = (int) Math.pow(ex - px, 2);
        int en = (int) Math.pow(py - ey, 2);
        int ed = (int) Math.sqrt(em + en);

        if (ed <= 150) {
            return  true;
        }
           return false;
    }

    public int addPoints(){
        return points += 10;
    }
    public int getPacx() {
        return pacx;
    }

    public int getPacy() {
        return pacy;
    }

    public int getEnemyx (){
        return enemyx;
    }

    public int getEnemyy () {
        return enemyy;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<GoldCoin> getCoins() {
        return coins;
    }

    public Bitmap getPacBitmapRight() {
        return pacBitmapRight;
    }

    public Bitmap getPacBitmapLeft() {
        return pacBitmapLeft;
    }

    public Bitmap getPacBitmapUp() {
        return pacBitmapUp;
    }

    public Bitmap getPacBitmapDown() {
        return pacBitmapDown;
    }

    public Bitmap getEnemy(){
        return enemy;
    }

    @Override
    public void onClick(View v) {
    }
}
