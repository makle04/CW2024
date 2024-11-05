package com.example.demo;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY=8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		horizontalVelocityMultiplier=0;
	}

	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPositionY = getLayoutY() + getTranslateY();
			if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		if (isMovingHorizontally()) {
			double newXPosition = getTranslateX() + (HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			if (newXPosition >= 0 && newXPosition <= getMaxXPosition()) {
				moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			}
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMovingVertically() {
		return velocityMultiplier != 0;
	}

	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void moveLeft(){
		horizontalVelocityMultiplier=-1;
	}

	public void moveRight(){
		horizontalVelocityMultiplier=1;
	}

	public void stop() {
		velocityMultiplier = 0;
		horizontalVelocityMultiplier=0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	private double getMaxXPosition() {
		return this.getScene().getWidth() - this.getBoundsInLocal().getWidth();
	}



}
