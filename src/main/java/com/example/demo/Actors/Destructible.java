package com.example.demo.Actors;

/**
 * The {@code Destructible} interface defines objects that can take damage and be destroyed.
 * <p>
 * Classes implementing this interface must provide the ability to handle damage and destruction logic,
 * which is essential for objects like enemies, projectiles, and other game entities that can be destroyed.
 * </p>
 */
public interface Destructible {

	/**
	 * Handles the damage taken by the object. Implementing classes should define the logic for reducing
	 * health, triggering death, or any other consequence of taking damage.
	 */
	void takeDamage();

	/**
	 * Destroys the object. Implementing classes should define the logic for object destruction,
	 * such as removing it from the game scene or triggering an explosion animation.
	 */
	void destroy();
}