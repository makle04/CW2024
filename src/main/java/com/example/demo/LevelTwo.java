package com.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelThree";
    private static final int TOTAL_ENEMIES = 7;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private Text killCountText;
    private Text level1Text;
    private Text killConText;
    private static final Font digitalfont= Font.loadFont(LevelTwo.class.getResourceAsStream("/com/example/demo/images/digitalfont.ttf"), 27);

    public LevelTwo(double screenHeight, double screenWidth) {
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
        killConText = new Text("KILL TO PROCEED: 15");
        killConText.setFill(Color.LIGHTGREEN);
        killConText.setFont(digitalfont);
        killConText.setX(getScreenWidth() - 250);
        killConText.setY(70);
        getRoot().getChildren().add(killConText);
        getRoot().getChildren().add(killCountText);
        level1Text = new Text("LEVEL 2");
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
