package no.wafflewings.limitcurse;

import java.awt.Font;
import java.lang.Thread.State;
import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LimitCurse extends Game {
	static int W = 800;
	static int H = 480;
	Camera cam;
	Viewport viewport;
	SpriteBatch batch;
	public static Sprite background;
	public static TextureAtlas atlas;
	Sound explo, gameoversound;
	
	enum States{
		START, RUNNUNG, TRANSITION, GAMEOVER
	}
	
	
	States state = States.START;
	
	ArrayList<Minigame> minigames;
	
	Minigame current;
	float timeLimit;
	float timeLeft;
	
	float pauseTime = 2;
	float pasueTimer=0;
	
	TweenManager tm;
	int gameIndex = 0;
	
	Animation explosion;
	Animation welldone;
	boolean success = true;
	String endText = "GO!";
	float speed = 1;
	
	Sprite greenBar;
	TextureRegion barCover;
	int gamesCompleted = 0;
	int lifes;
	float survivalTime = 0;
	
	Sound fanfare;
	BitmapFont font;
	
	
	public LimitCurse() {
		minigames = new ArrayList<Minigame>();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		tm = new TweenManager();
	}
	
	int start = 0;
	@Override
	public void create () {
		atlas = new TextureAtlas(Gdx.files.internal("game.atlas"));
		cam = new OrthographicCamera();
		viewport = new FitViewport(W, H, cam);
		batch = new SpriteBatch();
		background = atlas.createSprite("haha");
		background.setSize(W, H);
		background.setPosition(-W/2, -H/2);
		font = new BitmapFont();
//		minigames.add(new WakeUpCall());
//		minigames.add(new SpoonFed());
//		minigames.add(new FootPain());
//		minigames.add(new BusStop());
//		minigames.add(new FlappyBird());
//		minigames.add(new Labyrinth());
		minigames.add(new Shoot());
//		
		int FRAME_COLS = 5;
		int FRAME_ROWS = 5;
        Texture walkSheet = new Texture(Gdx.files.internal("nuclear.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        explosion = new Animation(1/25.0f, walkFrames);      // #11
        
        Array<AtlasRegion> arr = atlas.findRegions("awesome");
        
        Texture well = new Texture(Gdx.files.internal("smiley.png"));
        
        TextureRegion[] tr = new TextureRegion[arr.size];
        for (int i = 0; i < tr.length; i++) {
			tr[i] = arr.get(i);
		}
        welldone = new Animation(1/3.0f, tr);
		
        barCover = atlas.findRegion("bar");
        greenBar = new Sprite(atlas.findRegion("green"));
        greenBar.setPosition(-W/2 + 5, -H/2 + 4);
        greenBar.setSize(W, 40);
        
        fanfare = Gdx.audio.newSound(Gdx.files.internal("sounds/fanfare.wav"));
        explo = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.mp3"));
        gameoversound = Gdx.audio.newSound(Gdx.files.internal("sounds/sad_ending_cut.mp3"));
        
	}

	@Override
	public void render () {
		float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
		
		dt*=speed;
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		switch (state) {
		case START:
			startRender(dt);
			break;
		case TRANSITION:
			transittion(dt);
			break;
		case RUNNUNG:
			running(dt);
			break;
		case GAMEOVER:
			gameover(dt);
			break;
		default:
			break;
		}
	}
	
	private void transittion(float dt) {
		pasueTimer+=Gdx.graphics.getDeltaTime();
		batch.begin();
		if(success) {
			batch.draw(welldone.getKeyFrame(pasueTimer, true),-W/2, -H/2, W, H);
		}else{
			batch.draw(explosion.getKeyFrame(pasueTimer, true),-W/2, -H/2, W, H);  // #16
			batch.draw(explosion.getKeyFrame(pasueTimer, true),-W/2, -H/2, W, H);  // #16
		}
		font.setColor(Color.WHITE);
		font.setScale(2);
		font.draw(batch, endText, -font.getBounds(endText).width/2, H/2);
		if(gamesCompleted > 0 && gamesCompleted % 4 == 0){
			font.draw(batch, "SPEED UP!", -font.getBounds("SPEED UP!").width/2, -100);
		}
		batch.end();
		if(pasueTimer > pauseTime) {
			if(lifes == 0) {
				state = States.GAMEOVER;
				gameoversound.play();
			}else{
				state = States.RUNNUNG;
			}
		}
	}
	
	private void running(float dt) {
		survivalTime += Gdx.graphics.getDeltaTime();
		tm.update(dt);
		current.render(batch, dt, timeLeft);
		timeLeft -= dt;
		if(timeLeft<=0) {
			current.timeOver();
		}
		if(current.done) {
			nextGame();
		}
		batch.begin();
		greenBar.setSize(W*(timeLeft / timeLimit), 40);
		greenBar.draw(batch);
		batch.draw(barCover, -W/2, -H/2, 0, 0, W, 50, 1, 1, 0);
		batch.end();
	}
	
	private void gameover(float dt) {
		batch.begin();
		background.draw(batch);
		font.setColor(Color.WHITE);
		font.setScale(4);
		String t = "Time Survived " + (int)(survivalTime)+"s";
		font.draw(batch, t, -font.getBounds(t).width/2, 0);
		batch.end();
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			lifes = 1;
			current = null;
			success = true;
			state = States.START;
			gameoversound.stop();
		}
	}
	
	private void startRender(float dt) {
		batch.begin();
		background.draw(batch);
		font.setColor(Color.WHITE);
		font.setScale(4);
		font.draw(batch, "Use Space and arrows", -font.getBounds("Use space and arrows").width/2, 0);
		batch.end();
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			lifes = 1;
			survivalTime = 0;
			state = States.TRANSITION;
			fanfare.play(0.1f);
			nextGame();
		}
	}
	
	private void nextGame() {
		pasueTimer = 0;
		tm.killAll();
		state = States.TRANSITION;
		if(current != null) {
			gamesCompleted++;
			if(gamesCompleted%4 == 0){
				speed += 0.01;
			}
			success = current.success;
			if(current.success) {
				fanfare.play(0.1f);
				endText = current.getSuccessText();
			} else {
				lifes--;
				pasueTimer = 1;
				explo.play(0.5f);
				endText = current.getFailText();
			}
		}
		Minigame temp = current;
		int i = 0;
		while(current == temp && i < 10) {
			i++;
			current = minigames.get(MathUtils.random(minigames.size()-1));
		}
		gameIndex++;
		gameIndex = gameIndex == minigames.size() ? 0 : gameIndex;
		current.reset();
		current.create(tm);
		timeLimit = current.getWantedTime();
		timeLeft = timeLimit;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}
}
