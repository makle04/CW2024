package com.example.demo.Actors;

/**
 * The {@code Projectile} class represents a base class for all projectiles in the game.
 * <p>
 * This abstract class extends {@link ActiveActorDestructible} and defines shared properties
 * and behaviors for all projectiles, such as destruction upon taking damage.
 * Subclasses must implement the {@code updatePosition()} method to define their specific movement behavior.
 * </p>
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a {@code Projectile} with the specified image, dimensions, and initial position.
	 *
	 * @param imageName   the name of the image file for the projectile
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X-coordinate of the projectile
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles damage taken by the projectile. When a projectile takes damage,
	 * it is immediately destroyed.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Abstract method to update the position of the projectile.
	 * Subclasses must implement this method to define their specific movement behavior.
	 */
	@Override
	public abstract void updatePosition();
}
