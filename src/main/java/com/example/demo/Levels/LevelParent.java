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

/**
 * The abstract base class for all game levels. It manages the game loop, user input,
 * enemy units, projectiles, and game state.
 */
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
	private Text x2DamageText;
	private Timeline blinkAnimation;
	private boolean isDoubleDamageActive = false;
	private boolean x2Activated = false;
	private boolean x2Cooldown = false;
	private static final Font digitalfont= Font.loadFont(LevelTwo.class.getResourceAsStream("/Fonts/digitalfont.ttf"),-1);

	/**
	 * Constructs a LevelParent object.
	 *
	 * @param backgroundImageName The name of the background image.
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 * @param playerInitialHealth The initial health of the player.
	 */
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
		MusicManager.playBackgroundMusic("/Audio/oriBGM.wav");
	}

	/**
	 * Initializes the friendly units (like the user plane).
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Checks whether the game is over.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Spawns the enemy units for the level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Instantiates the level-specific view.
	 *
	 * @return A LevelView object for the level.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene for the level.
	 *
	 * @return The scene object for the level.
	 */
	public Scene initializeScene() {
		startCountdown();
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		initializeX2DamageText();
		startX2Cooldown();
		x2Cooldown = true;
		return scene;
	}

	/**
	 * Starts the game by playing the timeline animation.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Proceeds to the next level in the game.
	 *
	 * @param levelName The name of the next level.
	 */
	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Updates the scene during each game loop.
	 */
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

	/**
	 * Initializes the timeline for the game loop.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background for the level and sets up various event handlers for user input.
	 * - Configures the background image to fit the screen dimensions.
	 * - Sets up key press and release event handlers to control the user plane's movement and actions.
	 * - Sets up mouse click event to fire projectiles.
	 * - Handles the activation of double damage, pause toggle, and background music toggle via specific keys.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.X) activateDoubleDamage();
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

	/**
	 * Initializes the pause text shown when the game is paused.
	 */
	private void initializePauseText(){
		pauseText=new Text("Press P to resume...");
		pauseText.setFont(digitalfont);
		pauseText.setFont(Font.font(digitalfont.getName(), 45));
		pauseText.setFill(Color.BLACK);
		pauseText.setVisible(false);
		pauseText.setX(450);
		pauseText.setY(350);
	}

	/**
	 * Toggles the pause state of the game.
	 */
	private void togglePause(){
		if (isPaused){
			resumeGame();
		}
		else{
			pauseGame();
		}
	}

	/**
	 * Pauses the game and displays the pause text.
	 */
	private void pauseGame(){
		isPaused=true;
		timeline.pause();
		pauseText.setVisible(true);
		getRoot().getChildren().add(pauseText);
	}

	/**
	 * Resumes the game and hides the pause text.
	 */
	private void resumeGame(){
		isPaused=false;
		timeline.play();
		pauseText.setVisible(false);
		getRoot().getChildren().remove(pauseText);
		updateScene();
	}

	/**
	 * Fires a projectile from the user plane.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Generates enemy projectiles for the level.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns an enemy projectile and adds it to the scene.
	 *
	 * @param projectile The projectile to spawn.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors (planes, projectiles) in the game.
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes all destroyed actors from the scene.
	 */
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

	/**
	 * Handles collisions between planes.
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 */
	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Handles collisions between two sets of actors.
	 *
	 * @param actors1 The first set of actors.
	 * @param actors2 The second set of actors.
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getHitbox().intersects(otherActor.getHitbox().getBoundsInParent())) {
					if (actor instanceof FighterPlane && isDoubleDamageActive) {
						actor.takeDamage();
						actor.takeDamage();
					} else {
						actor.takeDamage();
					}
					otherActor.takeDamage();
				}
			}
		}
	}

	/**
	 * Handles the case where an enemy unit penetrates the user's defenses.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();

			}
		}
	}


	/**
	 * Updates the level view to reflect the current health of the user.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the kill count of the user based on the number of enemies defeated.
	 */
	protected void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Determines if an enemy has penetrated the user's defenses.
	 *
	 * @param enemy The enemy to check.
	 * @return True if the enemy has penetrated, false otherwise.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Ends the game with a win, stopping the music and clearing the screen.
	 */
	protected void winGame() {
		MusicManager.stopBackgroundMusic();
		MusicManager.playWinClip("/Audio/WinAudio.wav");
		timeline.stop();
		root.getChildren().clear();
		levelView.setBackground("/com/example/demo/images/winBG.jpg");
		levelView.showWinImage();
	}

	/**
	 * Ends the game with a loss, stopping the music and clearing the screen.
	 */
	protected void loseGame() {
		timeline.stop();
		MusicManager.playLoseClip("/Audio/LoseAudio.wav");
		root.getChildren().clear();
		levelView.setBackground("/com/example/demo/images/gameoverBG.jpg");
		levelView.showGameOverImage(0.5,0.5);
		MusicManager.stopBackgroundMusic();
	}

	/**
	 * Returns the user plane object.
	 *
	 * @return The user plane.
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Returns the root group of the scene.
	 *
	 * @return The root group.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Returns the current number of enemy units in the level.
	 *
	 * @return The number of enemies.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the scene and the list of enemy units.
	 *
	 * @param enemy The enemy unit to add.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Returns the maximum Y position where enemies can spawn.
	 *
	 * @return The maximum Y position for enemy spawn.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Returns the screen width of the game.
	 *
	 * @return The screen width.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user plane is destroyed.
	 *
	 * @return True if the user plane is destroyed, false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the number of enemies in the level.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Handles the removal of projectiles that are out of bounds.
	 */
	private void handleOutOfBoundsProjectiles() {
		removeOutOfBoundsActors(userProjectiles);
		removeOutOfBoundsActors(enemyProjectiles);
	}

	/**
	 * Removes all projectiles that are out of bounds from the scene.
	 *
	 * @param projectiles The list of projectiles to check.
	 */
	private void removeOutOfBoundsActors(List<ActiveActorDestructible> projectiles) {
		List<ActiveActorDestructible> outOfBoundsActors = projectiles.stream()
				.filter(projectile -> isOutOfBounds(projectile))
				.collect(Collectors.toList());
		getRoot().getChildren().removeAll(outOfBoundsActors);
		projectiles.removeAll(outOfBoundsActors);
	}

	/**
	 * Determines if a projectile is out of bounds.
	 *
	 * @param actor The projectile to check.
	 * @return True if the projectile is out of bounds, false otherwise.
	 */
	private boolean isOutOfBounds(ActiveActorDestructible actor) {
		double x = actor.getTranslateX();
		double y = actor.getTranslateY();
		return x > screenWidth || y > screenHeight;
	}

	/**
	 * Toggles the background music between two tracks.
	 */
	private void toggleBackgroundMusic() {
		MusicManager.toggleBackgroundMusic("/Audio/oriBGM.wav", "/Audio/CalmBGM.wav");
	}

	/**
	 * Starts the countdown before the game begins.
	 */
	public void startCountdown() {
		final int[] countdown = {3};
		Text countdownText = new Text();
		countdownText.setFont(digitalfont);
		countdownText.setFont(Font.font(digitalfont.getName(), 75));
		countdownText.setFill(Color.LIGHTYELLOW);
		countdownText.setX(screenWidth / 2 - 30);
		countdownText.setY(screenHeight / 2);
		root.getChildren().add(countdownText);

		Timeline countdownTimeline = new Timeline(
				new KeyFrame(Duration.seconds(1), e -> {
					if (countdown[0] > 0) {
						countdownText.setText(String.valueOf(countdown[0]--));
					} else {
						updateScene();
						root.getChildren().remove(countdownText);
						startGame();
					}
				})
		);
		countdownTimeline.setCycleCount(4);
		countdownTimeline.play();
	}

	/**
	 * Initializes the text display for the "Double Damage" prompt.
	 * The text is configured with a custom font, color, and effect, and is initially hidden.
	 * A blinking animation is also set up to alternate the opacity of the text.
	 */
	private void initializeX2DamageText() {
		x2DamageText = new Text();
		x2DamageText.setFont(digitalfont);
		x2DamageText.setFont(Font.font(digitalfont.getName(), 35));
		x2DamageText.setFill(Color.RED);
		x2DamageText.setVisible(false);
		x2DamageText.setY(95);
		x2DamageText.setStyle("-fx-effect: dropshadow(gaussian, #de3c26, 10, 0.5, 0, 0);");
		root.getChildren().add(x2DamageText);
		blinkAnimation = new Timeline(
				new KeyFrame(Duration.seconds(0.5), e -> x2DamageText.setOpacity(1)),
				new KeyFrame(Duration.seconds(1), e -> x2DamageText.setOpacity(0))
		);
		blinkAnimation.setCycleCount(Timeline.INDEFINITE);
	}

	/**
	 * Displays the "PRESS X FOR DOUBLE DAMAGE!" message on the screen.
	 * The text appears at the center of the screen and starts blinking.
	 */
	private void showX2Text() {
		if (!x2Activated && !x2Cooldown) {
			x2DamageText.setVisible(true);
			x2DamageText.setText("PRESS X FOR DOUBLE DAMAGE!");
			x2DamageText.setX((getScreenWidth() /2) -185);
			blinkAnimation.play();
		}
	}

	/**
	 * Hides the "PRESS X FOR DOUBLE DAMAGE!" message and stops the blinking animation.
	 */
	private void hideX2Text() {
		x2DamageText.setVisible(false);
		blinkAnimation.stop();
	}

	/**
	 * Activates the double damage effect for a duration of 5 seconds.
	 * Displays the "Double Damage!" message and starts a cooldown for the next use.
	 * After 5 seconds, the double damage effect is deactivated.
	 */
    private void activateDoubleDamage() {
		if (!x2Cooldown) {
			x2DamageText.setText("Double Damage!");
			x2DamageText.setX((getScreenWidth() /2) -115);
			x2Activated = true;
			isDoubleDamageActive = true;
			hideX2Text();
			x2DamageText.setVisible(true);
			x2DamageText.setOpacity(1);
			Timeline x2ActivationTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(5), e -> deactivateDoubleDamage())
            );
			x2ActivationTimeline.setCycleCount(1);
			x2ActivationTimeline.play();
			startX2Cooldown();
		}
	}

	/**
	 * Deactivates the double damage effect and hides the "Double Damage!" text.
	 */
	private void deactivateDoubleDamage() {
		x2Activated = false;
		isDoubleDamageActive = false;
		x2DamageText.setVisible(false);
	}

	/**
	 * Starts the cooldown period for double damage, during which it cannot be activated again.
	 * The cooldown duration is set to 10 seconds.
	 */
	private void startX2Cooldown() {
		x2Cooldown = true;
        Timeline x2CooldownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> endX2Cooldown())
        );
		x2CooldownTimeline.setCycleCount(1);
		x2CooldownTimeline.play();
	}

	/**
	 * Ends the cooldown period for double damage, allowing it to be activated again.
	 * The "PRESS X FOR DOUBLE DAMAGE!" text is displayed once more.
	 */
	private void endX2Cooldown() {
		x2Cooldown = false;
		showX2Text();
	}
}