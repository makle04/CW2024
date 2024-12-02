package com.example.demo.Actors;

import javafx.scene.shape.Rectangle;

/**
 * The {@code ActiveActorDestructible} class extends {@link ActiveActor} and implements {@link Destructible},
 * providing a base for actors that can take damage and be destroyed.
 * <p>
 * This abstract class includes additional functionality for tracking and managing an actor's destruction
 * state, as well as defining behavior for updating and interacting with other game elements.
 * </p>
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/ActiveActorDestructible.java">Original ActiveActorDestructible</a>
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * Tracks whether the actor has been destroyed.
	 */
	private boolean isDestroyed;

	/**
	 * Constructs an {@code ActiveActorDestructible} with the specified image, size, and initial position.
	 *
	 * @param imageName    the name of the image file to use for the actor
	 * @param imageHeight  the desired height of the image
	 * @param initialXPos  the initial X-coordinate of the actor
	 * @param initialYPos  the initial Y-coordinate of the actor
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor.
	 * <p>
	 * This method must be implemented by subclasses to define the specific movement behavior of the actor.
	 * </p>
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the actor's state.
	 * <p>
	 * This method must be implemented by subclasses to define how the actor interacts with the game world,
	 * including logic for movement, collisions, and animations.
	 * </p>
	 */
	public abstract void updateActor();

	/**
	 * Applies damage to the actor.
	 * <p>
	 * This method must be implemented by subclasses to define how the actor reacts to damage, such as
	 * reducing health or triggering animations.
	 * </p>
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed.
	 * <p>
	 * This method sets the internal state of the actor to indicate it has been destroyed, allowing for
	 * further actions such as removal from the game world.
	 * </p>
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed {@code true} to mark the actor as destroyed, {@code false} otherwise
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, {@code false} otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Retrieves the hitbox of the actor.
	 * <p>
	 * This method must be implemented by subclasses to return a {@link Rectangle} representing the
	 * actor's hitbox, which can be used for collision detection.
	 * </p>
	 *
	 * @return a {@link Rectangle} representing the hitbox of the actor
	 */
	public abstract Rectangle getHitbox();
}
