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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.DNEG;

public class FootPain extends Minigame implements InputProcessor{

	TweenManager tm;
	Sprite background, forground;
	Sprite face;
	Sprite foot;
	Sprite foot_crash;
	Rectangle finish;
	Vector2 pos;
	Sound snap;
	float speed = 200;
	float doneTimer = 0;
	boolean crushed = false;
	public FootPain() {
		background = LimitCurse.atlas.createSprite("background");
		forground = LimitCurse.atlas.createSprite("wall_last_drawn");
		face = new Sprite((TextureRegion)LimitCurse.atlas.findRegion("green"));
		face.setSize(100, 100);
		face.setPosition(-590, -200);
		foot = LimitCurse.atlas.createSprite("foot", 1);
		foot_crash = LimitCurse.atlas.createSprite("foot", 3);
		finish = new Rectangle(-590,-200, 50, 50);
		pos = new Vector2();
		snap = Gdx.audio.newSound(Gdx.files.internal("sounds/foot_snap.mp3"));
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
		return 4.0f;
	}

	@Override
	public void create(TweenManager tm) {
		this.tm = tm;
		foot.setPosition(-1000, -400);
		foot.setSize(1472/2, 1219/2);
		foot_crash.setPosition(-570, -170);
		foot_crash.setSize(1472/2, 1219/2);
		background.setSize(W, H);
		background.setPosition(-W/2, -H/2);
		forground.setSize(W, H);
		forground.setPosition(-W/2, -H/2);
		Gdx.input.setInputProcessor(this);
		doneTimer = 0;
		crushed = false;
	}

	@Override
	public void render(SpriteBatch sb, float dt, float timeleft) {
		sb.begin();
		background.draw(sb);
		if(!crushed) {
			foot.draw(sb);
		}else{
			foot_crash.draw(sb);
			doneTimer+=dt;
		}
		forground.draw(sb);
		face.draw(sb);
		sb.end();
		float cspeed = speed*dt;
		if(Gdx.input.isKeyPressed(Keys.UP)){
			foot.translateY(cspeed);
		}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			foot.translateY(-cspeed);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			foot.translateX(-cspeed);
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			foot.translateX(cspeed);
		}
		//Gdx.app.log("Foot", "x: " + foot.getX() + " y: " + foot.getY());
		pos.set(foot.getX(), foot.getY());
		if(finish.contains(pos) && !crushed){
			crushed = true;
			snap.play();
		}
		if(doneTimer > 2) {
			complete(true);
		}
		
	}

	@Override
	public void timeOver() {
		if(!crushed){
			complete(false);
		}
	}

	@Override
	public void dipose() {
		
	}

	@Override
	public boolean keyDown(int keycode) {
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
