package com.firsttest.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.firsttest.game.somegame.service.Field;
import com.firsttest.game.somegame.units.Army;
import com.firsttest.game.somegame.units.unitsabstract.BaseUnit;

import java.util.Random;

public class FirstTestGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bcg, sniper, arbalester, wizard, priest, thief, swordsman, peasant, dead;
	private static final int ARMYSIZE = 10;
	private static final String ARMYONE = "Good Guys";
	private static final String ARMYTWO = "Bad Guys";
	private static int step = 0;
	private Army armyOne, armyTwo;
	
	@Override
	public void create () {
//		Game.gameStart();
		// устанавливаем размер квадратного поля
		Field.setSize(ARMYSIZE);

		// генерируем две армии
		armyOne = new Army(ARMYONE, ARMYSIZE, true);
		armyTwo = new Army(ARMYTWO, ARMYSIZE, false);

		// сортировка и заполнение очерёдности ходьбы юнитов в соответсвии с приоритетом
		Army.fillPriorityList(armyOne, armyTwo);


		batch = new SpriteBatch();

		String background = "bcg/field" + new Random().nextInt(1, 3) + ".png";
		bcg = new Texture(background);
		sniper = new Texture("units/to_use/sniper.png");
		arbalester = new Texture("units/to_use/arbalester.png");
		wizard = new Texture("units/to_use/wizard.png");
		priest = new Texture("units/to_use/priest.png");
		thief = new Texture("units/to_use/thief.png");
		swordsman = new Texture("units/to_use/swordsman.png");
		peasant = new Texture("units/to_use/peasant.png");
		dead = new Texture("units/to_use/dead.png");

	}

	@Override
	public void render () {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched() || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			step++;
			Gdx.graphics.setTitle("Step:" + step);
			for (BaseUnit unit : Army.getPriorityList()) {
				if (unit.isAlive()) {
					if (armyOne.getArmy().contains(unit)) unit.action(armyOne, armyTwo);
					else unit.action(armyTwo, armyOne);
				}
			}
		}
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(bcg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		for (int i = Army.getPriorityList().size()-1; i >=0; i--) {
			// расположение на поле кривенькое, ну и лан, пруф оф концепт , сойдёт пока так
			int x = (Gdx.graphics.getWidth() / 20) + (Army.getPriorityList().get(i).getPosition().getX()) * Gdx.graphics.getWidth() / 12;
			int y = (Gdx.graphics.getHeight() / 20) + (Army.getPriorityList().get(i).getPosition().getY()) * Gdx.graphics.getHeight() / 12;

			if (!Army.getPriorityList().get(i).isAlive()) {
				batch.draw(dead, x, y);
				continue;
			}
			switch (Army.getPriorityList().get(i).unitType()) {
				case "Pesnt": batch.draw(peasant, x, y); break;
				case "Arbst": batch.draw(arbalester, x, y); break;
				case "Snipr": batch.draw(sniper, x, y); break;
				case "Prist": batch.draw(priest, x, y); break;
				case "Wizrd": batch.draw(wizard, x, y); break;
				case "Sword": batch.draw(swordsman, x, y); break;
				case "Thief": batch.draw(thief, x, y); break;

			}

		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bcg.dispose();
	}
}
