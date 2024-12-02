package com.example.demo.Actors;

import javafx.scene.shape.Rectangle;

/**
 * The {@code EnemyPlane} class represents a standard enemy in the game.
 * <p>
 * This class extends {@link FighterPlane} and includes properties and behavior
 * specific to enemy planes, such as movement, firing projectiles, and collision detection.
 * </p>
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/EnemyPlane.java">Original EnemyPlane</a>
 */
public class EnemyPlane extends FighterPlane {

	// Constants for enemy configuration
	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = 0.01;
	private static final double HITBOX_X_OFFSET = -30.0;
	private static final double HITBOX_Y_OFFSET = 25.0;

	// Field for managing the hitbox
	private final Rectangle hitbox;

	/**
	 * Constructs an {@code EnemyPlane} with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the enemy plane
	 * @param initialYPos the initial Y-coordinate of the enemy plane
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		hitbox = new Rectangle(initialXPos, initialYPos, 150, 30);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 * Also updates the hitbox to match the new position.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateHitbox();
	}

	/**
	 * Fires a projectile from the enemy plane with a defined probability.
	 *
	 * @return a {@link EnemyProjectile} if the enemy fires during this frame, otherwise {@code null}
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Retrieves the hitbox of the enemy plane for collision detection.
	 *
	 * @return a {@link Rectangle} representing the hitbox of the enemy plane
	 */
	@Override
	public Rectangle getHitbox() {
		return hitbox;
	}

	/**
	 * Updates the position of the enemy plane's hitbox to match its current position.
	 */
	private void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX() + HITBOX_X_OFFSET);
		hitbox.setY(getLayoutY() + getTranslateY() + HITBOX_Y_OFFSET);
	}
}
