package com.example.demo.Levels;

import com.example.demo.Actors.ActiveActorDestructible;
import com.example.demo.Actors.EnemyPlane;
import com.example.demo.Views.LevelView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private static final Font digitalfont= Font.loadFont(LevelTwo.class.getResourceAsStream("/Fonts/digitalfont.ttf"), -1);

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
        killCountText.setFill(Color.WHITE);
        killCountText.setFont(digitalfont);
        killCountText.setFont(Font.font(digitalfont.getName(), 27));
        killCountText.setX(getScreenWidth() - 250);
        killCountText.setY(40);
        getRoot().getChildren().add(killCountText);

        killConText = new Text("KILL TO PROCEED: 20");
        killConText.setFill(Color.LIGHTGREEN);
        killConText.setFont(digitalfont);
        killConText.setFont(Font.font(digitalfont.getName(), 27));
        killConText.setX(getScreenWidth() - 250);
        killConText.setY(75);
        getRoot().getChildren().add(killConText);

        level2Text = new Text("LEVEL 2");
        level2Text.setFill(Color.WHITE);
        level2Text.setFont(digitalfont);
        level2Text.setFont(Font.font(digitalfont.getName(), 35));
        level2Text.setX((getScreenWidth() /2) -65 );
        level2Text.setY(50);
        getRoot().getChildren().add(level2Text);
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
