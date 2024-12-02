package com.example.demo.Actors;

import java.util.*;

import javafx.scene.shape.Rectangle;

/**
 * The {@code BossPlane} class represents a boss enemy in the game, extending {@link FighterPlane}.
 * <p>
 * This class includes custom behavior for movement, firing projectiles, and using shields.
 * The boss follows a randomized movement pattern and has unique shield mechanics to protect
 * it from damage.
 * </p>
 */
public class BossPlane extends FighterPlane {

	// Constants for boss configuration
	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = 0.04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.002;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -50;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	protected static final int MAX_FRAMES_WITH_SHIELD = 500;
	private static final double HITBOX_Y_OFFSET = 80.0;

	// Fields for managing boss state
	private final List<Integer> movePattern;
	public boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private final Rectangle hitbox;

	/**
	 * Constructs a {@code BossPlane} object with predefined properties for position,
	 * size, health, and behavior.
	 */
	public BossPlane() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
		hitbox = new Rectangle(INITIAL_X_POSITION, INITIAL_Y_POSITION, 200, 80);
	}

	/**
	 * Updates the position of the boss plane based on its movement pattern.
	 * Prevents the boss from moving outside defined boundaries.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
		updateHitbox();
	}

	/**
	 * Updates the state of the boss plane, including its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile from the boss plane based on a random probability.
	 *
	 * @return a {@link BossProjectile} if the boss fires during this frame, otherwise {@code null}
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Applies damage to the boss plane. Damage is only applied if the shield is not active.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the movement pattern of the boss plane with a shuffled sequence
	 * of vertical moves and pauses.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the boss plane. Activates or deactivates the shield
	 * based on random probabilities and elapsed frames.
	 */
	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Retrieves the next move in the movement pattern. Shuffles the pattern if the
	 * current move has been repeated too many times.
	 *
	 * @return the next vertical move value
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the boss fires a projectile during the current frame.
	 *
	 * @return {@code true} if the boss fires, {@code false} otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial position for the projectile fired by the boss.
	 *
	 * @return the Y-coordinate for the projectile's starting position
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the shield should be activated based on a random probability.
	 *
	 * @return {@code true} if the shield should be activated, {@code false} otherwise
	 */
	public boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks if the shield's activation period has expired.
	 *
	 * @return {@code true} if the shield is exhausted, {@code false} otherwise
	 */
	public boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the shield for the boss plane.
	 */
	public void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the shield for the boss plane and resets the shield activation frame counter.
	 */
	public void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Retrieves the number of frames the shield has been active.
	 *
	 * @return the number of frames the shield has been active
	 */
	public int getFramesWithShieldActivated() {
		return framesWithShieldActivated;
	}

	/**
	 * Checks if the boss plane's shield is currently active.
	 *
	 * @return {@code true} if the shield is active, {@code false} otherwise
	 */
	public boolean isShielded() {
		return isShielded;
	}

	/**
	 * Updates the position of the boss plane's hitbox to match its current position.
	 */
	private void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX());
		hitbox.setY(getLayoutY() + getTranslateY() + HITBOX_Y_OFFSET);
	}

	/**
	 * Retrieves the hitbox of the boss plane for collision detection.
	 *
	 * @return a {@link Rectangle} representing the hitbox of the boss plane
	 */
	@Override
	public Rectangle getHitbox() {
		return hitbox;
	}
}
