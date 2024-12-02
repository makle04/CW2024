package com.example.demo.Actors;

import javafx.scene.shape.Rectangle;

/**
 * The {@code UserProjectile} class represents a projectile fired by the user plane.
 * <p>
 * This class extends {@link Projectile} and defines the behavior of the user plane's
 * projectiles, including movement and hitbox management.
 * </p>
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final int HORIZONTAL_VELOCITY = 15;
	private final Rectangle hitbox;

	/**
	 * Constructs a {@code UserProjectile} with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the projectile
	 * @param initialYPos the initial Y position of the projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		hitbox = new Rectangle(initialXPos, initialYPos, 25, 30);
	}

	/**
	 * Updates the position of the user projectile by moving it horizontally.
	 * The projectile moves with a fixed horizontal velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateHitbox();
	}

	/**
	 * Updates the user projectile by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Retrieves the hitbox of the user projectile.
	 *
	 * @return the hitbox rectangle
	 */
	@Override
	public Rectangle getHitbox() {
		return hitbox;
	}

	/**
	 * Updates the hitbox position based on the current position of the projectile.
	 */
	private void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX());
		hitbox.setY(getLayoutY() + getTranslateY());
	}
}

