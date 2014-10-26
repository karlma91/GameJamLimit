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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class FlappyBird extends Minigame implements InputProcessor{

	TweenManager tm;
	Sprite bird, background;
	float gravity = 0.3f;
	float yspeed;
	float leftspeed = 5;
	Vector2 bpos;
	float maxtop = 100;
	float maxbot = -100;
	
	Sound flap;
	Array<Sprite> objects;
	
	boolean dead;
	float timer = 0;
	
	public FlappyBird() {
		bird = LimitCurse.atlas.createSprite("flappy_bird");
		background = LimitCurse.atlas.createSprite("flappy_bg");
		background.setSize(W, H);
		background.setPosition(-W/2, -H/2);
		bird.setSize(100,100);
		objects = new Array<Sprite>();
		generateobjects();
		bpos = new Vector2();
		flap = Gdx.audio.newSound(Gdx.files.internal("sounds/flap.wav"));
	}
	
	private void generateobjects() {
		for (int i = 0; i < 10; i++) {
			Sprite top = LimitCurse.atlas.createSprite("pipe_down"); 
			top.setSize(top.getWidth()/2, top.getHeight()/2);
			Sprite bot = LimitCurse.atlas.createSprite("pipe");
			bot.setSize(bot.getWidth()/2, bot.getHeight()/2);
			objects.add(top);
			objects.add(bot);
		}
	}
	
	@Override
	public String getSuccessText() {
		return "Flapp away!";
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
		for (int i = 0; i < objects.size; i+=2) {
			Sprite top = objects.get(i);
			Sprite bot = objects.get(i+1);
			//top.setRotation(180);
			bot.setRotation(0);
			
			float middle = MathUtils.random(maxbot, maxtop);
			bot.setPosition(i*300, middle - bot.getHeight() - 100);
			top.setPosition(i*300, middle);
		}
		bird.setPosition(-W/3, 100);
		yspeed = 0;
		Gdx.input.setInputProcessor(this);
		dead = false;
		timer = 0;
	}

	@Override
	public void render(SpriteBatch sb, float dt, float timeleft) {
		sb.begin();
		background.draw(sb);
		bird.draw(sb);
		for (int i = 0; i < objects.size; i++) {
			Sprite tmp = objects.get(i);
			tmp.draw(sb);
		}
		sb.end();
		if(!dead) {
			for (int i = 0; i < objects.size; i++) {
				Sprite tmp = objects.get(i);
				tmp.setPosition(tmp.getX() - 200*dt, tmp.getY());
				bpos.set(bird.getX() + bird.getWidth() / 2 , bird.getY() + bird.getHeight() / 2);
				if(tmp.getBoundingRectangle().contains(bpos)) {
					dead = true;
				}
			}
		}else{
			timer += dt;
		}
		
		if(timer > 2) {
			complete(false);
		}
		
		yspeed -= gravity;
		bird.setY(bird.getY() + yspeed);
		
		if(bird.getY() < -300){
			bird.setY(-300);
		}
	}

	@Override
	public void timeOver() {
		complete(!dead);
	}

	@Override
	public void dipose() {
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE && !dead) {
			yspeed = 5f;
			flap.play(0.3f);
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
