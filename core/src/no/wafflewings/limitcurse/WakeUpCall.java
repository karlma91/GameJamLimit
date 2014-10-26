package no.wafflewings.limitcurse;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class WakeUpCall extends Minigame implements InputProcessor{

	TweenManager tm;
	TextureRegion background;
	Sprite clock, armSprite;
	
	Animation arm;
	
	float time = 0;
	float speed = 40;
	float cspeed;
	Rectangle rec;
	Sound ring;
	public WakeUpCall() {
		background = LimitCurse.atlas.findRegion("badlogic");
		clock = new Sprite((LimitCurse.atlas.findRegion("alarm_clock")));
		arm = new Animation(1/13.0f, LimitCurse.atlas.findRegions("arm"));
		arm.getKeyFrame(0.1f, true);
		armSprite = new Sprite(arm.getKeyFrame(0.1f, true));
		//armSprite = new Sprite(LimitCurse.atlas.findRegion("arm",1));
		rec = new Rectangle();
		ring = Gdx.audio.newSound(Gdx.files.internal("sounds/alarm-clock-01.wav"));
	}
	
	@Override
	public String getSuccessText() {
		return "Wake up man!";
	}

	@Override
	public String getDeveloper() {
		return "WaffleWings";
	}

	@Override
	public float getWantedTime() {
		return 2.5f;
	}

	@Override
	public void create(TweenManager tm) {
		this.tm = tm;
		armSprite.setPosition(0, 0);
		armSprite.setSize(1765/2, 534/2);
		clock.setPosition(-500, -400);
		clock.setScale(0.5f, 0.5f);
		clock.rotate(-10);
		Tween.to(clock, SpriteAccessor.ROTATION, 0.05f)
	    .target(10)
	    .repeatYoyo(-1, 0.0f)
	    .start(tm);
		Gdx.input.setInputProcessor(this);
		first = true;
	}
	boolean first;

	@Override
	public void render(SpriteBatch sb, float dt, float timeleft) {
		if(first) {
			ring.play(0.1f);
			first = false;
		}
		time+= dt;
		armSprite.setRegion(arm.getKeyFrame(time, true));
		sb.begin();
		clock.draw(sb);
		armSprite.draw(sb);
		sb.end();
		rec.set(armSprite.getBoundingRectangle());
		rec.setX(rec.x + 250);
		if (clock.getBoundingRectangle().overlaps(rec)) {
			ring.stop();
			complete(true);
		}
		
	}

	@Override
	public void timeOver() {
		ring.stop();
		complete(false);
	}

	@Override
	public void dipose() {
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE) {
			armSprite.translateX(-speed);
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
		return "Fired";
	}
}
