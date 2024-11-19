package com.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private ShieldImage shieldImage;
	private Text bosshealthText;
	private Text shieldText;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
		shieldImage = new ShieldImage(boss.getLayoutX(), boss.getLayoutY());
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		bosshealthText = new Text("BOSS HEALTH: " + boss.getHealth());
		bosshealthText.setFill(Color.WHITE);
		bosshealthText.setStyle("-fx-font-size: 24;");
		bosshealthText.setX(getScreenWidth() - 250);
		bosshealthText.setY(30);
		getRoot().getChildren().add(bosshealthText);
		shieldText = new Text();
		shieldText.setFill(Color.WHITE);
		shieldText.setStyle("-fx-font-size: 24;");
		shieldText.setX(getScreenWidth() - 250);
		shieldText.setY(60);
		getRoot().getChildren().add(shieldText);
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
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
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
			updateShieldPosition();
		} else {
			shieldText.setText("SHIELD: Deactivated");
		}
		updateShieldPosition();
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
