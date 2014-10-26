package no.wafflewings.limitcurse;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.DNEG;

public class Shoot extends Minigame implements InputProcessor{

	TweenManager tm;
	Sprite background;
	Sprite target;
	Sprite cursor;
	Vector2 pos;
	Sound shot;
	float speed = 200;
	float doneTimer = 0;
	boolean hit = false;
	public Shoot() {
		background = LimitCurse.atlas.createSprite("floor");
		target = new Sprite((TextureRegion)LimitCurse.atlas.findRegion("monster"));
		target.setSize(100, 100);
		cursor = LimitCurse.atlas.createSprite("crosshair");
		pos = new Vector2();
		shot = Gdx.audio.newSound(Gdx.files.internal("sounds/gun_shot.mp3"));
	}
	
	@Override
	public String getSuccessText() {
		return "Ouch!!";
	}

	@Override
	public String getDeveloper() {
		return "WaffleWings";
	}

	@Override
	public float getWantedTime() {
		return 5.0f;
	}

	@Override
	public void create(TweenManager tm) {
		this.tm = tm;
		cursor.setPosition(0, 0);
		cursor.setSize(100, 100);
		background.setSize(W, H);
		background.setPosition(-W/2, -H/2);
		Gdx.input.setInputProcessor(this);

		target.setPosition(MathUtils.random(-W/2 + 100, W/2 - 100),
						   MathUtils.random(-H/2 + 100, H/2 - 100));
		doneTimer = 0;
		hit = false;
	}

	@Override
	public void render(SpriteBatch sb, float dt, float timeleft) {
		sb.begin();
		background.draw(sb);
		target.draw(sb);
		cursor.draw(sb);
		if(!hit) {
		}else{
			doneTimer+=dt;
		}
		sb.end();
		float cspeed = speed*dt;
		if(Gdx.input.isKeyPressed(Keys.UP)){
			cursor.translateY(cspeed);
		}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			cursor.translateY(-cspeed);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			cursor.translateX(-cspeed);
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			cursor.translateX(cspeed);
		}
		//Gdx.app.log("Foot", "x: " + foot.getX() + " y: " + foot.getY());
		
		if(doneTimer > 1) {
			complete(true);
		}
		
	}

	@Override
	public void timeOver() {
		if(!hit){
			complete(false);
		}
	}

	@Override
	public void dipose() {
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE) {
			shot.play(0.5f);
			pos.set(cursor.getX() + cursor.getWidth()/2, cursor.getY() + cursor.getHeight()/2);
			if(target.getBoundingRectangle().contains(pos) && !hit){
				hit = true;
				shot.play();
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFailText() {
		return "Ok";
	}
}
