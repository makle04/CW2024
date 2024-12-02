package com.example.demo.Views;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The LevelView class is responsible for managing the visual elements of a game level.
 * It handles the display of the player's hearts, win image, game over image, and the level's background.
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;  // X position for displaying hearts
	private static final double HEART_DISPLAY_Y_POSITION = 25;  // Y position for displaying hearts
	private static final int WIN_IMAGE_X_POSITION = 355;  // X position for the win image
	private static final int WIN_IMAGE_Y_POSITION = 175;  // Y position for the win image
	private static final int LOSS_SCREEN_X_POSITION = -35;  // X position for the loss screen image
	private static final int LOSS_SCREEN_Y_POSISITION = -310;  // Y position for the loss screen image
	private final Group root;  // The root group where all visual elements are added
	private final WinImage winImage;  // The image that represents winning the game
	private final GameOverImage gameOverImage;  // The image displayed when the game is over
	private final HeartDisplay heartDisplay;  // The heart display for showing health

	/**
	 * Constructor for the LevelView class.
	 * Initializes the root container, heart display, win image, and game over image.
	 *
	 * @param root The root group where the visual elements will be displayed.
	 * @param heartsToDisplay The number of hearts to display, representing the player's health.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
	}

	/**
	 * Displays the heart container in the root group.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image in the root group.
	 * The win image is shown when the player wins the game.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game over image in the root group.
	 * The game over image is shown when the player loses the game.
	 *
	 * @param scaleX The scale factor for the X axis of the game over image.
	 * @param scaleY The scale factor for the Y axis of the game over image.
	 */
	public void showGameOverImage(double scaleX, double scaleY) {
		root.getChildren().add(gameOverImage);
		gameOverImage.setScaleX(scaleX);  // Sets the scale of the game over image on X axis
		gameOverImage.setScaleY(scaleY);  // Sets the scale of the game over image on Y axis
	}

	/**
	 * Removes hearts from the display based on the remaining number of hearts.
	 * This method is used to update the heart display when the player's health decreases.
	 *
	 * @param heartsRemaining The number of hearts to remain on the display.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Sets the background image for the level.
	 *
	 * @param backgroundImagePath The path to the background image.
	 */
	public void setBackground(String backgroundImagePath) {
		ImageView background = new ImageView(new Image(getClass().getResource(backgroundImagePath).toExternalForm()));
		background.setFitWidth(root.getScene().getWidth());  // Sets the width of the background image
		background.setFitHeight(root.getScene().getHeight());  // Sets the height of the background image
		background.toBack();  // Places the background behind other elements
		root.getChildren().add(background);  // Adds the background image to the root group
	}
}
