package com.example.demo.Actors;

import javafx.scene.shape.Rectangle;

/**
 * The {@code UserPlane} class represents the player's fighter plane in the game.
 * <p>
 * This class extends {@link FighterPlane} and defines the player's specific behavior,
 * including movement, projectile firing, and tracking the number of kills.
 * The {@code UserPlane} can move up, down, left, and right with acceleration and deceleration.
 * </p>
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 25;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 150;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;
	private static final double ACCELERATION = 1.2;
	private static final double HORIZONTAL_VELOCITY = 14.0;
	private static final double VERTICAL_ACCELERATION = 1.5;
	private static final double VERTICAL_VELOCITY = 14.0;
	private double verticalVelocity = 0.0;
	private double horizontalVelocity = 0.0;
	private final Rectangle hitbox;
	private static final double HITBOX_X_OFFSET = 10.0;
	private static final double HITBOX_Y_OFFSET = 55.0;

	/**
	 * Constructs a {@code UserPlane} with the specified initial health.
	 *
	 * @param initialHealth the initial health of the user plane
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
		hitbox = new Rectangle(INITIAL_X_POSITION, INITIAL_Y_POSITION, 150, 20);
	}

	/**
	 * Updates the position of the user plane based on its current velocity.
	 * The plane can move vertically and horizontally, with acceleration applied
	 * to the velocities. The plane is restricted within vertical bounds.
	 */
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			verticalVelocity += VERTICAL_ACCELERATION * velocityMultiplier;
			verticalVelocity = Math.max(-VERTICAL_VELOCITY, Math.min(verticalVelocity, VERTICAL_VELOCITY));
		} else {
			verticalVelocity *= 0.9;
			if (Math.abs(verticalVelocity) < 0.1) verticalVelocity = 0;
		}

		double initialTranslateY = getTranslateY();
		this.moveVertically(verticalVelocity);
		double newPositionY = getLayoutY() + getTranslateY();
		if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
			this.setTranslateY(initialTranslateY);
		}

		if (isMovingHorizontally()) {
			horizontalVelocity += ACCELERATION * horizontalVelocityMultiplier;
			horizontalVelocity = Math.max(-HORIZONTAL_VELOCITY, Math.min(horizontalVelocity, HORIZONTAL_VELOCITY));
		} else {
			horizontalVelocity *= 0.9;
			if (Math.abs(horizontalVelocity) < 0.1) horizontalVelocity = 0;
		}

		double newXPosition = getTranslateX() + horizontalVelocity;
		if (newXPosition >= 0 && newXPosition <= getMaxXPosition()) {
			moveHorizontally(horizontalVelocity);
		}
		updateHitbox();
	}

	/**
	 * Updates the user plane by updating its position and handling other behaviors.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane at the calculated position.
	 *
	 * @return a new {@link UserProjectile} if fired, or {@code null} if no projectile is fired
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		double projectileX = this.getLayoutX() + this.getTranslateX() + PROJECTILE_X_POSITION;
		double projectileY = this.getLayoutY() + this.getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
		return new UserProjectile(projectileX, projectileY);
	}

	/**
	 * Checks if the user plane is currently moving vertically.
	 *
	 * @return {@code true} if the plane is moving vertically, {@code false} otherwise
	 */
	private boolean isMovingVertically() {
		return velocityMultiplier != 0;
	}

	/**
	 * Checks if the user plane is currently moving horizontally.
	 *
	 * @return {@code true} if the plane is moving horizontally, {@code false} otherwise
	 */
	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	/**
	 * Moves the user plane up by setting the vertical velocity multiplier to -1.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the user plane down by setting the vertical velocity multiplier to 1.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Moves the user plane left by setting the horizontal velocity multiplier to -1.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the user plane right by setting the horizontal velocity multiplier to 1.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops the movement of the user plane by setting both the vertical and horizontal velocity multipliers to 0.
	 */
	public void stop() {
		velocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Retrieves the number of kills made by the user plane.
	 *
	 * @return the number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the number of kills by 1.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Retrieves the maximum X position the user plane can occupy, constrained by the scene width.
	 *
	 * @return the maximum X position
	 */
	private double getMaxXPosition() {
		return this.getScene().getWidth() - this.getBoundsInLocal().getWidth();
	}

	/**
	 * Updates the hitbox of the user plane based on its current position.
	 */
	private void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX() + HITBOX_X_OFFSET);
		hitbox.setY(getLayoutY() + getTranslateY() + HITBOX_Y_OFFSET);
	}

	/**
	 * Retrieves the hitbox of the user plane.
	 *
	 * @return the hitbox rectangle
	 */
	@Override
	public Rectangle getHitbox() {
		return hitbox;
	}
}
