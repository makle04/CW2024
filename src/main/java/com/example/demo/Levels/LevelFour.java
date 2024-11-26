package com.example.demo.Levels;

import com.example.demo.Actors.BossPlane;
import com.example.demo.Views.BossView;
import com.example.demo.Views.LevelView;
import com.example.demo.Views.ShieldImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelFour extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final BossPlane boss;
	private BossView levelView;
	private ShieldImage shieldImage;
	private Text bosshealthText;
	private Text shieldText;
	private Text level4Text;
	private static final Font digitalfont= Font.loadFont(LevelFour.class.getResourceAsStream("/Fonts/digitalfont.ttf"), -1);


	public LevelFour(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new BossPlane();
		shieldImage = new ShieldImage(boss.getLayoutX(), boss.getLayoutY());
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

		shieldText = new Text();
		shieldText.setFill(Color.BLACK);
		shieldText.setFont(digitalfont);
		shieldText.setFont(Font.font(digitalfont.getName(), 27));
		shieldText.setX(getScreenWidth() - 250);
		shieldText.setY(75);
		getRoot().getChildren().add(shieldText);

		level4Text = new Text("LEVEL 4");
		level4Text.setFill(Color.BLACK);
		level4Text.setFont(digitalfont);
		level4Text.setFont(Font.font(digitalfont.getName(), 35));
		level4Text.setX((getScreenWidth() /2) -65 );
		level4Text.setY(50);
		getRoot().getChildren().add(level4Text);
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
		levelView = new BossView(getRoot(), PLAYER_INITIAL_HEALTH);
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