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
import com.badlogic.gdx.math.Vector2;

public class BusStop extends Minigame implements InputProcessor{

	TweenManager tm;
	Sprite background;
	Sprite bird;
	Sprite poo;
	Sprite man;
	Vector2 speed;
	float leftSpeed = 5;
	float gravity = 0.3f;
	float damp = 0.99f;
	boolean poOut;
	Rectangle hit;
	Vector2 poopos;
	boolean hashit;
	float doneTimer;
	float time;
	Animation flapp;
	Sound flaps, splat;
	TextureRegion birdReg;
	
	
	public BusStop() {
		background = LimitCurse.atlas.createSprite("busStop_bg");
		bird = LimitCurse.atlas.createSprite("vinger", 1);
		
		TextureRegion[] tr = new TextureRegion[2];
		tr[0] = LimitCurse.atlas.findRegion("vinger",1);
		tr[1] = LimitCurse.atlas.findRegion("vinger",2);
		flapp = new Animation(1/2.0f, tr);
		poo = LimitCurse.atlas.createSprite("poop");
		man = LimitCurse.atlas.createSprite("sad_dude");
		speed = new Vector2();
		man.setPosition(-200, -210);
		man.setSize(man.getWidth() / 3, man.getHeight() / 3);
		hit = new Rectangle(-200, -160, 50, 50);
		poopos = new Vector2();
		
		flaps = Gdx.audio.newSound(Gdx.files.internal("sounds/flap.wav"));
		splat = Gdx.audio.newSound(Gdx.files.internal("sounds/splat.wav"));
	}
	
	@Override
	public String getSuccessText() {
		return "You made his day wors!";
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
		bird.setSize(100, 100);
		bird.setPosition(W/2, H/2-bird.getHeight());
		background.setSize(W, H);
		background.setPosition(-W/2, -H/2);
		poo.setSize(30, 30);
		poo.setPosition(W, H);
		poOut = false;
		speed.set(-leftSpeed, 0);
		doneTimer = 0;
		hashit = false;
		man.setRegion(LimitCurse.atlas.findRegion("sad_dude"));
		Gdx.input.setInputProcessor(this);
		time = 0;
	}

	@Override
	public void render(SpriteBatch sb, float dt, float timeleft) {
		time+=dt;
		sb.begin();
		background.draw(sb);
		if(poOut) {
			speed.x = speed.x*damp;
			speed.y -= gravity;
			if(!hashit) {
				poo.setPosition(poo.getX() + speed.x, poo.getY() + speed.y);
				poo.draw(sb);
			}
			poopos.set(poo.getX() + poo.getWidth()/2, poo.getY() + poo.getHeight()/2);
			if(poo.getY() < -H) {
				complete(false);
			}
			if(hit.contains(poopos) && !hashit){
				hashit = true;
				splat.play();
				man.setRegion(LimitCurse.atlas.findRegion("sad_poop_dude"));
			}
			if(hashit) {
				if(doneTimer > 1.5f) {
					complete(true);
				}
				doneTimer += dt;
			}
		}
		if(birdReg != flapp.getKeyFrame(time, true)) {
			bird.setRegion(flapp.getKeyFrame(time, true));
			flaps.play(0.3f);
		}
		birdReg = flapp.getKeyFrame(time, true);
		bird.draw(sb);
		man.draw(sb);
		sb.end();
		bird.setX(bird.getX() - leftSpeed);
	}

	@Override
	public void timeOver() {
		if(!hashit){
			complete(false);
		}
	}

	@Override
	public void dipose() {
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE && !poOut && time>0.1f) {
			poOut = true;
			poo.setPosition(bird.getX() + bird.getWidth()/2, bird.getY() + bird.getHeight()/2);
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
		return "Missed!";
	}
}
