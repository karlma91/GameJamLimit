package no.wafflewings.limitcurse;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Minigame{
	
	public boolean success;
	public boolean done;
		
	/**
	 * Width and height of the gamw window
	 */
	public int W = LimitCurse.W;
	public int H = LimitCurse.H;
	
	/**
	 * This will show before the game starts
	 * @return the name og the game
	 */
	public abstract String getSuccessText();
	
	public abstract String getFailText();
	
	/**
	 * The developers name for credits 
	 * @return The developers name
	 */
	public abstract String getDeveloper();
	
	/**
	 * This function returns a timw this minigame need
	 * @return a number between 2 and 10 if it returns something else then the game wil not work
	 */
	public abstract float getWantedTime();
	/**
	 * This function is called right before the game starts
	 * Use it to initialize all variables
	 * @param tm If you want to use tweenengine save this variable
	 */
	public abstract void create(TweenManager tm);
	
	/**
	 * Spritebatch is clear and correct resolution
	 * 
	 * @param sb
	 * @param dt
	 * @param timeleft
	 */
	public abstract void render(SpriteBatch sb, float dt, float timeleft);
	
	
	/**
	 * This function is called when the game have no more time 
	 */
	public abstract void timeOver();
	
	/**
	 * When your game is complete call this function
	 * @param success
	 * @param score
	 */
	public void complete(boolean suc) {
		this.done = true;
		this.success = suc;
	}
	/**
	 * dispose of objects used in your game
	 */
	public abstract void dipose();
	
	public void reset(){
		done = false;
		success = false;
	}
	
}
