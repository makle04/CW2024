package com.example.demo.Levels;

import com.example.demo.Actors.ActiveActorDestructible;
import com.example.demo.Actors.EnemyPlane;
import com.example.demo.Views.LevelView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The LevelTwo class represents the second level in the game, where the player must eliminate a set number of enemies
 * to proceed to the next level. It includes enemy spawn mechanics, kill count tracking, and level-specific win/lose conditions.
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/LevelTwo.java">Original LevelTwo</a>
 */
public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelThree";
    private static final int TOTAL_ENEMIES = 7;
    private static final int KILLS_TO_ADVANCE = 20;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private Text killCountText;
    private Text level2Text;
    private Text killConText;
    private static final Font digitalfont = Font.loadFont(LevelTwo.class.getResourceAsStream("/Fonts/digitalfont.ttf"), -1);

    /**
     * Constructs a LevelTwo object, initializing the background, player health, and kill count requirements.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth  The width of the game screen.
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the game is over. The game ends if the player is destroyed or if the player has reached the required
     * kill count to advance to the next level.
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
     * Initializes the friendly units (player), and sets up texts for the kill count, level instructions, and current level.
     * Adds the necessary visual components to the root node.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());

        // Initialize kill count display
        killCountText = new Text("KILLCOUNT: " + user.getNumberOfKills());
        killCountText.setFill(Color.WHITE);
        killCountText.setFont(digitalfont);
        killCountText.setFont(Font.font(digitalfont.getName(), 27));
        killCountText.setX(getScreenWidth() - 250);
        killCountText.setY(40);
        getRoot().getChildren().add(killCountText);

        // Initialize kill count target instruction
        killConText = new Text("KILL TO PROCEED: 20");
        killConText.setFill(Color.LIGHTGREEN);
        killConText.setFont(digitalfont);
        killConText.setFont(Font.font(digitalfont.getName(), 27));
        killConText.setX(getScreenWidth() - 250);
        killConText.setY(75);
        getRoot().getChildren().add(killConText);

        // Initialize level indicator text
        level2Text = new Text("LEVEL 2");
        level2Text.setFill(Color.WHITE);
        level2Text.setFont(digitalfont);
        level2Text.setFont(Font.font(digitalfont.getName(), 35));
        level2Text.setX((getScreenWidth() / 2) - 65);
        level2Text.setY(50);
        getRoot().getChildren().add(level2Text);
    }

    /**
     * Spawns enemy units for the level. The number of enemies spawned is determined by the total number of enemies
     * minus the current number of enemies on screen, with a specified probability for each enemy.
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
     * Instantiates the level view and sets up the health for the player.
     *
     * @return The instantiated level view object for this level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks whether the player has reached the target kill count required to advance to the next level.
     *
     * @return True if the player has reached or exceeded the required kill count.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Updates the kill count displayed on the screen whenever the player kills an enemy.
     */
    @Override
    protected void updateKillCount() {
        super.updateKillCount();
        killCountText.setText("KILLCOUNT: " + user.getNumberOfKills());
    }
}
