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

public class SpoonFed extends Minigame implements InputProcessor{

	TweenManager tm;
	Sprite face;
	Sprite arm;
	float downSpeed = 0;
	float speed = 2;
	Sound smatt;
	
	boolean closed;
	public SpoonFed() {
		face = LimitCurse.atlas.createSprite("dude");
		face.setSize((int)((float)face.getWidth()/1.5f), (int)((float)face.getHeight()/1.5f));
		face.setPosition(100, -200);
		arm = LimitCurse.atlas.createSprite("spoon");
		smatt = Gdx.audio.newSound(Gdx.files.internal("sounds/smatt.wav"));
	}
	
	@Override
	public String getSuccessText() {
		return "Jummy!";
	}

	@Override
	public String getDeveloper() {
		return "WaffleWings";
	}

	@Override
	public float getWantedTime() {
		return 6.0f;
	}

	@Override
	public void create(TweenManager tm) {
		this.tm = tm;
		arm.setPosition(-W/2, -200);
		downSpeed = 0;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(SpriteBatch sb, float dt, float timeleft) {
		sb.begin();
		face.draw(sb);
		arm.draw(sb);
		sb.end();
		downSpeed -= 0.5f;
		arm.setY(arm.getY() + downSpeed);
		arm.setX(arm.getX() + 2f);
		
		if(arm.getY() > -470 && arm.getY() < -330){
			face.setRegion(LimitCurse.atlas.findRegion("dude"));
			if(closed) {
				smatt.play();
			}
			closed = false;
		}else{
			face.setRegion(LimitCurse.atlas.findRegion("dude_closed"));
			closed = true;
			
		}
		if(arm.getY() < -H - 200){
			complete(false);
		}
		
		if(arm.getX() > 100 && arm.getY() > -420 && arm.getY() < -350){
			complete(true);
		}
	}

	@Override
	public void timeOver() {
		complete(false);
	}

	@Override
	public void dipose() {
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE) {
			downSpeed = 5f;
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
		return "What a mess!";
	}
}
