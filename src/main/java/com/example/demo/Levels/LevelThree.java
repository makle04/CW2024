package com.example.demo.Levels;

import com.example.demo.Actors.BossPlane;
import com.example.demo.Views.LevelThreeView;
import com.example.demo.Views.LevelView;
import com.example.demo.Views.ShieldImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelThree extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final BossPlane boss;
	private LevelThreeView levelView;
	private ShieldImage shieldImage;
	private Text bosshealthText;
	private Text shieldText;
	private Text level3Text;
	private static final Font digitalfont= Font.loadFont(LevelThree.class.getResourceAsStream("/Fonts/digitalfont.ttf"), -1);


	public LevelThree(double screenHeight, double screenWidth) {
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

		level3Text = new Text("LEVEL 3");
		level3Text.setFill(Color.BLACK);
		level3Text.setFont(digitalfont);
		level3Text.setFont(Font.font(digitalfont.getName(), 35));
		level3Text.setX((getScreenWidth() /2) -65 );
		level3Text.setY(50);
		getRoot().getChildren().add(level3Text);
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
		levelView = new LevelThreeView(getRoot(), PLAYER_INITIAL_HEALTH);
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