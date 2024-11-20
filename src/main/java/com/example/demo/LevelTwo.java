package com.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final BossPlane boss;
	private LevelTwoView levelView;
	private ShieldImage shieldImage;
	private Text bosshealthText;
	private Text shieldText;
	private Text level2Text;
	private static final Font digitalfont= Font.loadFont(LevelTwo.class.getResourceAsStream("/com/example/demo/images/digitalfont.ttf"), 27);


	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new BossPlane();
		shieldImage = new ShieldImage(boss.getLayoutX(), boss.getLayoutY());
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		bosshealthText = new Text("BOSS HEALTH: " + boss.getHealth());
		bosshealthText.setFill(Color.BLACK);
		bosshealthText.setFont(digitalfont);
		bosshealthText.setX(getScreenWidth() - 250);
		bosshealthText.setY(40);
		getRoot().getChildren().add(bosshealthText);
		shieldText = new Text();
		shieldText.setFill(Color.BLACK);
		shieldText.setFont(digitalfont);
		shieldText.setX(getScreenWidth() - 250);
		shieldText.setY(70);
		getRoot().getChildren().add(shieldText);
		level2Text = new Text("LEVEL 2");
		level2Text.setFill(Color.BLACK);
		level2Text.setFont(digitalfont);
		level2Text.setX((getScreenWidth() /2) -60 );
		level2Text.setY(40);
		getRoot().getChildren().add(level2Text);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);

		}
	}


	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelTwoView(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	private void updateShieldPosition() {
		if (!boss.shieldExhausted()) {
			double offsetX = -65;
			double offsetY = 50;
			shieldImage.setTranslateX(boss.getTranslateX() + offsetX);
			shieldImage.setTranslateY(boss.getTranslateY() + offsetY);
		}
	}

	@Override
	protected void updateScene() {
		super.updateScene();
		bosshealthText.setText("BOSS HEALTH: " + boss.getHealth());
		if (boss.isShielded()) {
			shieldText.setText("SHIELD: Activated");
			shieldText.setFill(Color.LIGHTGREEN);
			updateShieldPosition();
		} else {
			shieldText.setText("SHIELD: Deactivated");
			shieldText.setFill(Color.RED);
			updateShieldPosition();
		}
		if (boss.getFramesWithShieldActivated()==0) {
			getRoot().getChildren().remove(shieldImage);
			shieldImage.hideShield();
			boss.deactivateShield();
		}
		if (!boss.isShielded() && boss.shieldShouldBeActivated() && boss.getFramesWithShieldActivated()==0) {
			boss.activateShield();
			getRoot().getChildren().add(shieldImage);
			shieldImage.showShield();
		}
	}
}