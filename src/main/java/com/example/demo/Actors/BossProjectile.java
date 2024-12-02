package com.example.demo.Actors;

import javafx.scene.shape.Rectangle;

/**
 * The {@code BossProjectile} class represents a projectile fired by the boss in the game.
 * <p>
 * This class extends {@link Projectile} and includes properties and behavior specific to
 * the boss's projectiles, such as movement, hitbox management, and collision detection.
 * </p>
 */
public class BossProjectile extends Projectile {

	// Constants for projectile configuration
	private static final String IMAGE_NAME = "bossFire.png";
	private static final int IMAGE_HEIGHT = 65;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;
	private static final double HITBOX_Y_OFFSET = 10.0;

	// Field for managing the hitbox
	private final Rectangle hitbox;

	/**
	 * Constructs a {@code BossProjectile} with the specified initial Y-coordinate.
	 *
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
		hitbox = new Rectangle(INITIAL_X_POSITION, initialYPos, 50, 32);
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
		hitbox.setY(getLayoutY() + getTranslateY() + HITBOX_Y_OFFSET);
	}
}
