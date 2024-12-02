package com.example.demo.Levels;

import com.example.demo.Actors.ActiveActorDestructible;
import com.example.demo.Actors.EnemyPlane;
import com.example.demo.Views.LevelView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The {@code LevelOne} class represents the first level of the game.
 * It extends the {@link LevelParent} class and includes specific gameplay mechanics
 * for the first level, such as enemy spawning, kill count tracking, and level progression.
 * <p>
 * This level introduces the player to the game mechanics by allowing them to destroy enemies,
 * while tracking their kills and advancing to the next level once a target number of kills is reached.
 * </p>
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/LevelOne.java">Original LevelOne</a>
 */
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
	private static final Font digitalfont = Font.loadFont(LevelOne.class.getResourceAsStream("/Fonts/digitalfont.ttf"), -1);

	/**
	 * Constructs a new {@code LevelOne} instance with the specified screen height and width.
	 * Initializes the level with the player and enemy settings.
	 *
	 * @param screenHeight the height of the screen for this level
	 * @param screenWidth the width of the screen for this level
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the game is over. The game ends if the player is destroyed or if the player
	 * reaches the required kill count to proceed to the next level.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
			timeline.stop();
		} else if (userHasReachedKillTarget()) {
			timeline.stop();
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Initializes the friendly units (player and UI elements such as kill count, level text, and kill target).
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());

		killCountText = new Text("KILLCOUNT: " + user.getNumberOfKills());
		killCountText.setFill(Color.WHITE);
		killCountText.setFont(digitalfont);
		killCountText.setFont(Font.font(digitalfont.getName(), 27));
		killCountText.setX(getScreenWidth() - 250);
		killCountText.setY(40);
		getRoot().getChildren().add(killCountText);

		killConText = new Text("KILL TO PROCEED: 10");
		killConText.setFill(Color.LIGHTGREEN);
		killConText.setFont(digitalfont);
		killConText.setFont(Font.font(digitalfont.getName(), 27));
		killConText.setX(getScreenWidth() - 250);
		killConText.setY(75);
		getRoot().getChildren().add(killConText);

		level1Text = new Text("LEVEL 1");
		level1Text.setFill(Color.WHITE);
		level1Text.setFont(digitalfont);
		level1Text.setFont(Font.font(digitalfont.getName(), 35));
		level1Text.setX((getScreenWidth() / 2) - 65);
		level1Text.setY(50);
		getRoot().getChildren().add(level1Text);
	}

	/**
	 * Spawns enemy units for the level based on the spawn probability.
	 * Adds enemy planes to the level until the total number of enemies reaches the specified limit.
	 */
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

	/**
	 * Instantiates the {@code LevelView} for this level.
	 *
	 * @return the {@code LevelView} for this level
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the player has reached the required kill target to advance to the next level.
	 *
	 * @return {@code true} if the player has reached or exceeded the required kill count, otherwise {@code false}
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	/**
	 * Updates the kill count display in the UI.
	 */
	@Override
	protected void updateKillCount() {
		super.updateKillCount();
		killCountText.setText("KILLCOUNT: " + user.getNumberOfKills());
	}
}
