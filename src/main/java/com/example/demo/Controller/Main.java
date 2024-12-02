package com.example.demo.Controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The {@code Main} class serves as the entry point to the Sky Battle game,
 * initializing and launching the game window and starting the game by invoking the {@code Controller}.
 * <p>
 * It extends the {@link javafx.application.Application} class and overrides the {@code start} method
 * to set up the main game window and start the game through the {@code Controller}.
 * </p>
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	/**
	 * The {@code start} method is called when the JavaFX application is launched.
	 * It sets up the main game window and invokes the {@code Controller} to start the game.
	 *
	 * @param stage the primary stage for this application
	 * @throws ClassNotFoundException if a class required for the game cannot be found
	 * @throws NoSuchMethodException if the method for initializing a level is not found
	 * @throws SecurityException if a security manager denies access
	 * @throws InstantiationException if a required class cannot be instantiated
	 * @throws IllegalAccessException if access to a method or class is denied
	 * @throws IllegalArgumentException if an invalid argument is passed
	 * @throws InvocationTargetException if an exception is thrown during method invocation
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);  // Set the window title
		stage.setResizable(false);  // Prevent resizing of the window
		stage.setHeight(SCREEN_HEIGHT);  // Set the screen height
		stage.setWidth(SCREEN_WIDTH);  // Set the screen width
		myController = new Controller(stage);  // Create a new controller
		myController.launchGame();  // Launch the game
	}

	/**
	 * The {@code main} method is the entry point of the Java application.
	 * It launches the JavaFX application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		launch();  // Launch the JavaFX application
	}
}
