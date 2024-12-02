package com.example.demo.Views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The GameOverImage class extends the ImageView class and is used to display
 * a "Game Over" image on the screen when the player loses the game.
 * It positions the image based on the provided coordinates.
 */
public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";  // Path to the game over image

	/**
	 * Constructor for the GameOverImage class. It loads the "Game Over" image,
	 * and sets its position on the screen using the provided X and Y coordinates.
	 *
	 * @param xPosition The X position where the "Game Over" image will be displayed.
	 * @param yPosition The Y position where the "Game Over" image will be displayed.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		// Sets the image from the specified file path
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		setLayoutX(xPosition);  // Sets the X position of the image
		setLayoutY(yPosition);  // Sets the Y position of the image
	}
}
