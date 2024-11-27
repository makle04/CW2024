package com.example.demo.Levels;

import com.example.demo.Actors.BossPlane;
import com.example.demo.Views.BossView;
import com.example.demo.Views.LevelView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelFour";
    private final BossPlane boss;
    private BossView levelView;
    private Text bosshealthText;
    private Text level3Text;
    private Text ConText;
    private static final Font digitalfont= Font.loadFont(LevelThree.class.getResourceAsStream("/Fonts/digitalfont.ttf"), -1);


    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new BossPlane();
        boss.isShielded = false;
        boss.setHealth(boss.getHealth() - 50);
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
        bosshealthText = new Text("BOSS HEALTH: " + boss.getHealth());
        bosshealthText.setFill(Color.WHITE);
        bosshealthText.setFont(digitalfont);
        bosshealthText.setFont(Font.font(digitalfont.getName(), 27));
        bosshealthText.setX(getScreenWidth() - 250);
        bosshealthText.setY(40);
        getRoot().getChildren().add(bosshealthText);

        ConText = new Text("KILL BOSS TO PROCEED");
        ConText.setFill(Color.LIGHTGREEN);
        ConText.setFont(digitalfont);
        ConText.setFont(Font.font(digitalfont.getName(), 27));
        ConText.setX(getScreenWidth() - 250);
        ConText.setY(75);
        getRoot().getChildren().add(ConText);

        level3Text = new Text("LEVEL 3");
        level3Text.setFill(Color.WHITE);
        level3Text.setFont(digitalfont);
        level3Text.setFont(Font.font(digitalfont.getName(), 35));
        level3Text.setX((getScreenWidth() /2) -65 );
        level3Text.setY(50);
        getRoot().getChildren().add(level3Text);
    }

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

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
            boss.isShielded = false;
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new BossView(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }


    @Override
    protected void updateScene() {
        super.updateScene();
        bosshealthText.setText("BOSS HEALTH: " + boss.getHealth());
        boss.isShielded = false;
    }
}