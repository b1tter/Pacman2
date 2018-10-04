package org.example.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.Random;


public class GameView extends View {

	Game game;
    int h,w; //used for storing our height and width of the view

	public void setGame(Game game)
	{
		this.game = game;
	}

	/* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
	public GameView(Context context) {
		super(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}

	public GameView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
	}

	//In the onDraw we put all our code that should be
	//drawn whenever we update the screen.
	@Override
    protected void onDraw(Canvas canvas) {
		//Here we get the height and weight
		h = canvas.getHeight();
		w = canvas.getWidth();
		//update the size for the canvas to the game.
		game.setSize(h,w);
		Log.d("GAMEVIEW","h = "+h+", w = "+w);
		//Making a new paint object
		Paint paint = new Paint();
		canvas.drawColor(Color.WHITE); //clear entire canvas to white color

        //draw the pacmans
		if (game.pacDirection == "right") {
		canvas.drawBitmap(game.getPacBitmapRight(), game.getPacx(),game.getPacy(), paint);}
		if (game.pacDirection == "left") {
		canvas.drawBitmap(game.getPacBitmapLeft(), game.getPacx(),game.getPacy(), paint);}
		if (game.pacDirection == "down") {
		canvas.drawBitmap(game.getPacBitmapDown(), game.getPacx(),game.getPacy(), paint);}
		if (game.pacDirection == "up") {
		canvas.drawBitmap(game.getPacBitmapUp(), game.getPacx(),game.getPacy(), paint);}

		canvas.drawBitmap(game.getEnemy(), game.getEnemyx(), game.getEnemyy(), paint);

        //TODO loop through the list of goldcoins and draw them.
        Random rnd = new Random();

        for (GoldCoin coin : game.coins) {
        	//Set random x y for coins
            coin.setRndX(coin.getRndX() == 0 ? rnd.nextInt(w) : coin.getRndX());
            coin.setRndY(coin.getRndY() == 0 ? rnd.nextInt(h) : coin.getRndY());

            canvas.drawBitmap(coin.getImg(), coin.getRndX(), coin.getRndY(), paint);
        }

		super.onDraw(canvas);
	}

}
