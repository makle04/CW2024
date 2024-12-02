package com.example.demo.Actors;

import javafx.scene.shape.Rectangle;

/**
 * The {@code EnemyProjectile} class represents a projectile fired by enemy planes in the game.
 * <p>
 * This class extends {@link Projectile} and includes properties and behavior specific to
 * enemy projectiles, such as movement, hitbox management, and collision detection.
 * </p>
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/EnemyProjectile.java">Original EnemyProjectile</a>
 */
public class EnemyProjectile extends Projectile {

	// Constants for projectile configuration
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 40;
	private static final int HORIZONTAL_VELOCITY = -10;

	// Field for managing the hitbox
	private final Rectangle hitbox;

	/**
	 * Constructs an {@code EnemyProjectile} with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the projectile
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		hitbox = new Rectangle(initialXPos, initialYPos, 20, IMAGE_HEIGHT / 2);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 * Also updates the hitbox to match the new position.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateHitbox();
	}

	/**
	 * Updates the state of the projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Retrieves the hitbox of the projectile for collision detection.
	 *
	 * @return a {@link Rectangle} representing the hitbox of the projectile
	 */
	@Override
	public Rectangle getHitbox() {
		return hitbox;
	}

	/**
	 * Updates the position of the projectile's hitbox to match its current position.
	 */
	private void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX());
		hitbox.setY(getLayoutY() + getTranslateY());
	}
}
