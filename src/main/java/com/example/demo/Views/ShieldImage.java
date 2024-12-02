package com.example.demo.Views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The ShieldImage class represents a visual shield in the game.
 * It is used to display and hide a shield image for a character (like the boss).
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/images/shield.png";  // Path to the shield image
	private static final int SHIELD_SIZE = 200;  // The size (both width and height) of the shield image

	/**
	 * Constructor for the ShieldImage class.
	 * Initializes the shield's position and properties such as size and visibility.
	 *
	 * @param xPosition The X position to display the shield.
	 * @param yPosition The Y position to display the shield.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);  // Sets the X position of the shield image
		this.setLayoutY(yPosition);  // Sets the Y position of the shield image
		// Loads the shield image from the specified path
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
		this.setVisible(false);  // Initially, the shield is not visible
		this.setFitHeight(SHIELD_SIZE);  // Sets the height of the shield image
		this.setFitWidth(SHIELD_SIZE);  // Sets the width of the shield image
	}

	/**
	 * Makes the shield visible.
	 * This method is called when the shield is activated or needed in the game.
	 */
	public void showShield() {
		this.setVisible(true);  // Sets the shield to be visible
	}

	/**
	 * Hides the shield.
	 * This method is called when the shield is deactivated or no longer needed in the game.
	 */
	public void hideShield() {
		this.setVisible(false);  // Sets the shield to be invisible
	}
}
