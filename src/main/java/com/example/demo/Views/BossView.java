package com.example.demo.Views;

import javafx.scene.Group;

/**
 * The BossView class represents the visual components specific to the boss level in the game.
 * It manages the display of elements such as the boss's shield.
 * This class extends the LevelView class, inheriting its functionality while adding additional
 * visual elements specific to the boss battle.
 *
 * @see <a href="https://github.com/kooitt/CW2024/blob/master/src/main/java/com/example/demo/LevelViewLevelTwo.java">Original BossView</a>
 */
public class BossView extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;  // X position of the shield on the screen
	private static final int SHIELD_Y_POSITION = 500;   // Y position of the shield on the screen
	private final Group root;  // The root container where the images will be added
	private final ShieldImage shieldImage;  // The shield image for the boss

	/**
	 * Constructor for the BossView class. Initializes the root, hearts to display,
	 * and the shield image, then adds the shield image to the root.
	 *
	 * @param root The root group where the images will be added.
	 * @param heartsToDisplay The number of hearts to display for the player (inherited from LevelView).
	 */
	public BossView(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);  // Calls the constructor of the parent class (LevelView)
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);  // Creates a new shield image
		addImagesToRoot();  // Adds the shield image to the root
	}

	/**
	 * Adds all the images (such as the shield) to the root group for rendering on the screen.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);  // Adds the shield image to the root container
	}
}
