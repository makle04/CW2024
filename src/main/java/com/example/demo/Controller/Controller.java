package com.example.demo.Controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;

/**
 * The {@code Controller} class is responsible for controlling the flow of the game, including
 * navigating between different levels and managing the game window.
 * <p>
 * The controller listens for updates from game levels (using the {@code Observer} pattern)
 * and responds by loading and transitioning to different levels dynamically.
 * </p>
 */
public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Levels.LevelOne";
	private final Stage stage;

	/**
	 * Constructs a {@code Controller} object with the specified game stage.
	 *
	 * @param stage the main stage of the game where scenes are displayed
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by displaying the main stage and starting the first level.
	 *
	 * @throws ClassNotFoundException if the class for the level cannot be found
	 * @throws NoSuchMethodException if the constructor for the level class is not found
	 * @throws SecurityException if a security manager denies access
	 * @throws InstantiationException if the level class cannot be instantiated
	 * @throws IllegalAccessException if access to the level constructor is not allowed
	 * @throws IllegalArgumentException if an invalid argument is passed to the constructor
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.show();
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Navigates to the specified level by dynamically loading the class and initializing the level scene.
	 *
	 * @param className the fully qualified name of the level class to load
	 * @throws ClassNotFoundException if the level class cannot be found
	 * @throws NoSuchMethodException if the constructor for the level class is not found
	 * @throws SecurityException if a security manager denies access
	 * @throws InstantiationException if the level class cannot be instantiated
	 * @throws IllegalAccessException if access to the level constructor is not allowed
	 * @throws IllegalArgumentException if an invalid argument is passed to the constructor
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.addObserver(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startCountdown();
	}

	/**
	 * Updates the controller when the observed level notifies of an event, such as completing a level
	 * or transitioning to a new level.
	 * <p>
	 * This method triggers the transition to the next level by invoking {@code goToLevel}.
	 * </p>
	 *
	 * @param arg0 the observable object (level)
	 * @param arg1 the argument passed by the observable (class name of the next level)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

}
