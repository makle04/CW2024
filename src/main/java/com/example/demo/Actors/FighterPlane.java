package com.example.demo.Actors;

/**
 * The {@code FighterPlane} class represents a base class for all fighter planes in the game.
 * <p>
 * This abstract class extends {@link ActiveActorDestructible} and defines shared properties
 * and behaviors for all fighter planes, such as firing projectiles, managing health, and
 * handling damage.
 * </p>
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/FighterPlane.java">Original FighterPlane</a>
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	// Field for storing the health of the fighter plane
	private int health;

	/**
	 * Constructs a {@code FighterPlane} with the specified image, dimensions, position, and health.
	 *
	 * @param imageName   the name of the image file for the fighter plane
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X-coordinate of the fighter plane
	 * @param initialYPos the initial Y-coordinate of the fighter plane
	 * @param health      the initial health of the fighter plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method for firing a projectile. Subclasses must implement this method
	 * to define their specific projectile-firing behavior.
	 *
	 * @return an {@link ActiveActorDestructible} representing the projectile fired by the plane,
	 *         or {@code null} if no projectile is fired
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the fighter plane by one when it takes damage.
	 * If the health reaches zero, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X-coordinate for firing a projectile, considering an offset.
	 *
	 * @param xPositionOffset the horizontal offset for the projectile's initial position
	 * @return the X-coordinate for the projectile's initial position
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y-coordinate for firing a projectile, considering an offset.
	 *
	 * @param yPositionOffset the vertical offset for the projectile's initial position
	 * @return the Y-coordinate for the projectile's initial position
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks whether the fighter plane's health has reached zero.
	 *
	 * @return {@code true} if the health is zero, {@code false} otherwise
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Retrieves the current health of the fighter plane.
	 *
	 * @return the current health value
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the health of the fighter plane.
	 *
	 * @param health the new health value
	 */
	public void setHealth(int health) {
		this.health = health;
	}
}

