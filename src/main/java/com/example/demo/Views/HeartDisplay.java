package com.example.demo.Views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The HeartDisplay class is responsible for managing and displaying the player's health
 * as a series of heart images on the screen. The class allows hearts to be displayed
 * or removed as the player's health changes.
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";  // Path to the heart image
	private static final int HEART_HEIGHT = 50;  // Height of each heart image
	private static final int INDEX_OF_FIRST_ITEM = 0;  // Index for removing the first heart
	private HBox container;  // Container for holding heart images
	private double containerXPosition;  // X position of the container
	private double containerYPosition;  // Y position of the container
	private int numberOfHeartsToDisplay;  // Number of hearts to display

	/**
	 * Constructor for the HeartDisplay class. It initializes the heart container and
	 * populates it with the specified number of hearts at the given position on the screen.
	 *
	 * @param xPosition The X position where the heart container will be displayed.
	 * @param yPosition The Y position where the heart container will be displayed.
	 * @param heartsToDisplay The number of hearts to display in the container.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();  // Initializes the container for the hearts
		initializeHearts();  // Adds heart images to the container
	}

	/**
	 * Initializes the container (HBox) where the heart images will be displayed.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);  // Sets the X position of the container
		container.setLayoutY(containerYPosition);  // Sets the Y position of the container
	}

	/**
	 * Initializes the heart images and adds them to the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);  // Sets the height of the heart image
			heart.setPreserveRatio(true);  // Preserves the aspect ratio of the heart image
			container.getChildren().add(heart);  // Adds the heart image to the container
		}
	}

	/**
	 * Removes one heart from the display.
	 * This method is typically used when the player loses health.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);  // Removes the first heart from the container
		}
	}

	/**
	 * Gets the container (HBox) that holds the heart images.
	 *
	 * @return The container holding the heart images.
	 */
	public HBox getContainer() {
		return container;
	}
}
