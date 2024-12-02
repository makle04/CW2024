package com.example.demo.Actors;

import javafx.scene.image.*;

/**
 * The {@code ActiveActor} class is an abstract class representing an active graphical
 * object in a JavaFX application. It extends {@link ImageView} to provide image display
 * functionality and includes methods for position updates.
 * <p>
 * This class serves as a base for all moving or interactive actors within the application.
 * Subclasses must implement the {@link #updatePosition()} method to define specific behavior.
 * </p>
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * The base location of the images used by the actor.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an {@code ActiveActor} with the specified image, size, and initial position.
	 *
	 * @param imageName    the name of the image file to use for the actor, located in {@code IMAGE_LOCATION}
	 * @param imageHeight  the desired height of the image
	 * @param initialXPos  the initial X-coordinate of the actor
	 * @param initialYPos  the initial Y-coordinate of the actor
	 * @throws NullPointerException if the specified image cannot be found
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor.
	 * <p>
	 * This method must be implemented by subclasses to define the actor's specific behavior
	 * and movement logic.
	 * </p>
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified distance.
	 *
	 * @param horizontalMove the distance to move the actor horizontally (positive for right, negative for left)
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified distance.
	 *
	 * @param verticalMove the distance to move the actor vertically (positive for down, negative for up)
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
