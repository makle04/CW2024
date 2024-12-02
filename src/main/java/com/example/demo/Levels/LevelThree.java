package com.example.demo.Levels;

import com.example.demo.Actors.BossPlane;
import com.example.demo.Views.BossView;
import com.example.demo.Views.LevelView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The LevelThree class represents the third level in the game, where the player faces a boss enemy.
 * It extends from LevelParent and implements the specific mechanics, such as spawning the boss,
 * updating the boss's health, and determining the win/loss conditions for the level.
 */
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelFour";
    private final BossPlane boss;
    private BossView levelView;
    private Text bosshealthText;
    private Text level3Text;
    private Text ConText;
    private static final Font digitalfont = Font.loadFont(LevelThree.class.getResourceAsStream("/Fonts/digitalfont.ttf"), -1);

    /**
     * Constructs a LevelThree object, initializing the background, player health, and the boss enemy.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth  The width of the game screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new BossPlane();
        boss.isShielded = false;
        boss.setHealth(boss.getHealth() - 50);  // Boss starts with reduced health.
    }

    /**
     * Initializes the friendly units, including the user plane, the boss health text, and level instructions.
     * Adds the necessary visual components to the root node.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());

        // Initialize boss health display
        bosshealthText = new Text("BOSS HEALTH: " + boss.getHealth());
        bosshealthText.setFill(Color.WHITE);
        bosshealthText.setFont(digitalfont);
        bosshealthText.setFont(Font.font(digitalfont.getName(), 27));
        bosshealthText.setX(getScreenWidth() - 250);
        bosshealthText.setY(40);
        getRoot().getChildren().add(bosshealthText);

        // Initialize level instruction text
        ConText = new Text("KILL BOSS TO PROCEED");
        ConText.setFill(Color.LIGHTGREEN);
        ConText.setFont(digitalfont);
        ConText.setFont(Font.font(digitalfont.getName(), 27));
        ConText.setX(getScreenWidth() - 250);
        ConText.setY(75);
        getRoot().getChildren().add(ConText);

        // Initialize level indicator text
        level3Text = new Text("LEVEL 3");
        level3Text.setFill(Color.WHITE);
        level3Text.setFont(digitalfont);
        level3Text.setFont(Font.font(digitalfont.getName(), 35));
        level3Text.setX((getScreenWidth() / 2) - 65);
        level3Text.setY(50);
        getRoot().getChildren().add(level3Text);
    }

    /**
     * Checks if the game is over. If the player's plane is destroyed, the player loses.
     * If the boss is destroyed, the player proceeds to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            timeline.stop();
            loseGame();
        } else if (boss.isDestroyed()) {
            timeline.stop();
            goToNextLevel(NEXT_LEVEL);
        }
    }

    /**
     * Spawns enemy units for the level. In this case, it spawns the boss when there are no remaining enemies.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
            boss.isShielded = false;  // Ensuring boss shield is off initially.
        }
    }

    /**
     * Instantiates the level view for the boss fight and sets it up with the initial health.
     *
     * @return The instantiated level view object for the boss fight.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new BossView(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }

    /**
     * Updates the game scene, including updating the boss's health text and ensuring the boss is not shielded.
     */
    @Override
    protected void updateScene() {
        super.updateScene();
        bosshealthText.setText("BOSS HEALTH: " + boss.getHealth());
        boss.isShielded = false;  // Ensure the boss is not shielded each frame.
    }
}
