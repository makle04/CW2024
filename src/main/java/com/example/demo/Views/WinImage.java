package com.example.demo.Views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The WinImage class represents the "You Win" image shown when the player completes a level or wins the game.
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/WinImage.java">Original WinImage</a>
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";  // Path to the "You Win" image
	private static final int HEIGHT = 500;  // The height of the "You Win" image
	private static final int WIDTH = 600;   // The width of the "You Win" image

	/**
	 * Constructor for the WinImage class.
	 * Initializes the "You Win" image with position, size, and visibility properties.
	 *
	 * @param xPosition The X position to display the "You Win" image.
	 * @param yPosition The Y position to display the "You Win" image.
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));  // Loads the "You Win" image
		this.setVisible(false);  // Initially, the "You Win" image is not visible
		this.setFitHeight(HEIGHT);  // Sets the height of the image
		this.setFitWidth(WIDTH);    // Sets the width of the image
		this.setLayoutX(xPosition - 20);  // Sets the X position with a slight offset
		this.setLayoutY(yPosition - 50);  // Sets the Y position with a slight offset
	}

	/**
	 * Makes the "You Win" image visible.
	 * This method is called when the player wins and the "You Win" message should be displayed.
	 */
	public void showWinImage() {
		this.setVisible(true);  // Sets the "You Win" image to be visible
	}
}
