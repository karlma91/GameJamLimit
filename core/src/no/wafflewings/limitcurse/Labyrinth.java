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

public class Labyrinth extends Minigame implements InputProcessor{

	TweenManager tm;
	Sprite player;
	Sprite wall, background;
	
	boolean map[][];
	Vector2 pos, newpos;
	Sound snap;
	float speed = 200;
	float doneTimer = 0;
	boolean goal = false;
	float mapsize = 14;
	float tilesize = 30;
	float mapx = -(tilesize * mapsize)/2;
	float mapy = -(tilesize * mapsize)/2;
	int goalx, goaly;
	public Labyrinth() {
		background = LimitCurse.atlas.createSprite("floor");
		wall = LimitCurse.atlas.createSprite("pigg");
		player = LimitCurse.atlas.createSprite("monster");
		pos = new Vector2();
		newpos = new Vector2();
		snap = Gdx.audio.newSound(Gdx.files.internal("sounds/foot_snap.mp3"));
		map = new boolean[(int)mapsize][(int)mapsize];
	}
	
	@Override
	public String getSuccessText() {
		return "Freedom";
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
		player.setSize(50, 50);
		player.setPosition(0, 0);
		player.setOriginCenter();
		wall.setSize(30, 30);
		Gdx.input.setInputProcessor(this);
		doneTimer = 0;
		goal = false;
		
		for (int i = 0; i < map.length; i++) {
			map[i][0] = true;
			map[i][map.length-1] = true;
		}
		for (int i = 0; i < map.length; i++) {
			map[0][i] = true;
			map[map.length-1][i] = true;
		}
		
		goalx = MathUtils.random(1, map.length-2);
		goaly = MathUtils.random(1)>0 ? map.length-1 : 0;
	}

	@Override
	public void render(SpriteBatch sb, float dt, float timeleft) {
		sb.begin();
		background.draw(sb);
		if(!goal) {
			player.draw(sb);
		}else{
			doneTimer+=dt;
		}
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if(map[i][j]){
					wall.setPosition(i*tilesize + mapx, j*tilesize + mapy);
					if(i == goalx && j == goaly) {
						wall.setColor(1, 0.5f, 0, 1);
					}else{
						wall.setColor(1, 1, 1, 1);
					}
					wall.draw(sb);
				}
			}
		}
		
		sb.end();
		
		float cspeed = speed*dt;
		pos.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
		newpos.set(pos);
		if(Gdx.input.isKeyPressed(Keys.UP)){
			newpos.add(0, cspeed);
		}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			newpos.add(0, -cspeed);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			newpos.add(-cspeed, 0);
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			newpos.add(cspeed, 0);
		}
		int x = (int)((newpos.x + (mapsize * tilesize)/2)/ 30);
		int y = (int)((newpos.y + (mapsize * tilesize)/2)/ 30);
		//Gdx.app.log("map", "mapx: " + x + " mapy: " + y);
		if(!goal && y>=0 && y<map.length && x>=0 && x < map.length && !map[x][y]){
			player.setPosition(newpos.x-player.getWidth() / 2, newpos.y - player.getHeight() / 2);
		}
		if(x == goalx && y == goaly){
			goal = true;
		}
		
		if(doneTimer > 1) {
			complete(true);
		}
		
	}

	@Override
	public void timeOver() {
		if(!goal){
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
		return "Fail";
	}
}
