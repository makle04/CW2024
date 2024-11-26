package com.example.demo.Levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Actors.ActiveActorDestructible;
import com.example.demo.Actors.FighterPlane;
import com.example.demo.Actors.UserPlane;
import com.example.demo.Music.MusicManager;
import com.example.demo.Views.LevelView;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	protected final Timeline timeline;
	protected final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	private LevelView levelView;
	private boolean isPaused = false;
	private Text pauseText;
	private static final Font digitalfont= Font.loadFont(LevelTwo.class.getResourceAsStream("/Fonts/digitalfont.ttf"), 45);

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		initializePauseText();
		friendlyUnits.add(user);
		MusicManager.playBackgroundMusic("/Audio/bgm.wav");
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

	protected void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		handleOutOfBoundsProjectiles();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.P) togglePause();
				if (kc == KeyCode.L) toggleBackgroundMusic();
				if (!isPaused) {
					if (kc == KeyCode.UP || kc == KeyCode.W) user.moveUp();
					if (kc == KeyCode.DOWN || kc == KeyCode.S) user.moveDown();
					if (kc == KeyCode.LEFT || kc == KeyCode.A) user.moveLeft();
					if (kc == KeyCode.RIGHT || kc == KeyCode.D) user.moveRight();
					if (kc == KeyCode.SPACE) fireProjectile();
				}
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if(!isPaused) {
					if (kc == KeyCode.UP || kc == KeyCode.DOWN || kc == KeyCode.RIGHT || kc == KeyCode.LEFT || kc == KeyCode.W || kc == KeyCode.A || kc == KeyCode.S || kc == KeyCode.D)
						user.stop();
				}
			}
		});
		background.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!isPaused) fireProjectile();
			}
		});
		root.getChildren().add(background);
	}

	private void initializePauseText(){
		pauseText=new Text("Press P to resume...");
		pauseText.setFont(digitalfont);
		pauseText.setFill(Color.BLACK);
		pauseText.setVisible(false);
		pauseText.setX(450);
		pauseText.setY(350);
	}

	private void togglePause(){
		if (isPaused){
			resumeGame();
		}
		else{
			pauseGame();
		}
	}

	private void pauseGame(){
		isPaused=true;
		timeline.pause();
		pauseText.setVisible(true);
		getRoot().getChildren().add(pauseText);
	}

	private void resumeGame(){
		isPaused=false;
		timeline.play();
		pauseText.setVisible(false);
		getRoot().getChildren().remove(pauseText);
		updateScene();
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getHitbox().intersects(otherActor.getHitbox().getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	protected void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		MusicManager.stopBackgroundMusic();
		MusicManager.playWinClip("/Audio/WinAudio.wav");
		timeline.stop();
		root.getChildren().clear();
		levelView.setBackground("/com/example/demo/images/winBG.jpg");
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		MusicManager.playLoseClip("/Audio/LoseAudio.wav");
		root.getChildren().clear();
		levelView.setBackground("/com/example/demo/images/gameoverBG.jpg");
		levelView.showGameOverImage(0.5,0.5);
		MusicManager.stopBackgroundMusic();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	private void handleOutOfBoundsProjectiles() {
		removeOutOfBoundsActors(userProjectiles);
		removeOutOfBoundsActors(enemyProjectiles);
	}

	private void removeOutOfBoundsActors(List<ActiveActorDestructible> projectiles) {
		List<ActiveActorDestructible> outOfBoundsActors = projectiles.stream()
				.filter(projectile -> isOutOfBounds(projectile))
				.collect(Collectors.toList());
		getRoot().getChildren().removeAll(outOfBoundsActors);
		projectiles.removeAll(outOfBoundsActors);
	}

	private boolean isOutOfBounds(ActiveActorDestructible actor) {
		double x = actor.getTranslateX();
		double y = actor.getTranslateY();
		return x > screenWidth || y > screenHeight;
	}

	private void toggleBackgroundMusic() {
		MusicManager.toggleBackgroundMusic("/Audio/bgm.wav", "/Audio/bgm2.wav");
	}

}