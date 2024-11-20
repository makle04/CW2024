package com.example.demo.Levels;

import com.example.demo.Actors.ActiveActorDestructible;
import com.example.demo.Actors.EnemyPlane;
import com.example.demo.Views.LevelView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private Text killCountText;
	private Text level1Text;
	private Text killConText;
	private static final Font digitalfont= Font.loadFont(LevelOne.class.getResourceAsStream("/fonts/digitalfont.ttf"), 27);

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
			timeline.stop();
		}
		else if (userHasReachedKillTarget()) {
			timeline.stop();
			goToNextLevel(NEXT_LEVEL);
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		killCountText = new Text("KILLCOUNT: " + user.getNumberOfKills());
		killCountText.setFill(Color.BLACK);
		killCountText.setFont(digitalfont);
		killCountText.setX(getScreenWidth() - 250);
		killCountText.setY(40);
		getRoot().getChildren().add(killCountText);
		killConText = new Text("KILL TO PROCEED: 10");
		killConText.setFill(Color.LIGHTGREEN);
		killConText.setFont(digitalfont);
		killConText.setX(getScreenWidth() - 250);
		killConText.setY(70);
		getRoot().getChildren().add(killConText);
		level1Text = new Text("LEVEL 1");
		level1Text.setFill(Color.BLACK);
		level1Text.setFont(digitalfont);
		level1Text.setX((getScreenWidth() /2) -60 );
		level1Text.setY(40);
		getRoot().getChildren().add(level1Text);
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	@Override
	protected void updateKillCount() {
		super.updateKillCount();
		killCountText.setText("KILLCOUNT: " + user.getNumberOfKills());
	}

}
