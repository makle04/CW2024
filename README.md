   **1. GitHub: https://github.com/makle04/CW2024**

   **2. Compilation Instructions**

        2.1. Ensure that computer that is going to run the program has volume loud enough for user to hear audio from the game.
   
        2.2. Download zipfile from Github and extract it and run it on Intellij
   
        2.3. Load Maven when it is prompted at the bottom right
   
        2.4. Open file Main from file path src/main/java/com.example.demo/Controller/Main.java

        2.5. Once Maven has fully been loaded and Main.java is open in the editor, User can run main to start the game.
   
   **3. Implemented and Working Properly**
   
   3.1. Allow UserPlane to move left and right

        3.1.1. Implemented methods to allow UserPlane to move horizontally in game
   
   3.2. Momentum to UserPlane Movements

        3.2.1. Made UserPlane movements around the game to be more smooth. Since left and right movements have been implemented,
        movements will look choppy if momentum of plane movements is not added.

   3.3. Added WASD keys for UserPlane movements

        3.3.1. Other keys other than the arrow keys can now move the UserPlane to allow users to have more versatility when playing.

   3.4. Added mouse leftclick to shoot from UserPlane **(Innovative Features)**

        3.4.1. Other ways to shoot down EnemyPlane and Boss other than space key to allow users to have more versatility when playing.

   3.5. Added killcount Text to LevelOne and LevelTwo

        3.5.1. Killcount was added at the top right of the screen to tell the users how much they have killed in that level.

   3.6. Added NextLevelCondition Text To LevelOne, LevelTwo, LevelThree

        3.6.1. Added NextLevelCondtion text to respective levels to move on to next level. 

   3.7. Added Level Text to all levels

        3.7.1. Added text to whichever level users are currently playing in.

   3.8 Added Boss health Text in LevelThree and LevelFour

        3.8.1. Added Boss health to show how much health Boss has left before getting destroyed.

   3.9. Added ShieldActivation Text in LevelFour

        3.9.1. ShieldActivation Text will change from being "deactivated" or "activated" whenever the shield is active or not.

   3.10. Pause Feature

        3.10.1. Press P to pause the game whenever user wants to stop gameplay.
        3.10.2. To Resume is to just press P again.

   3.11. Added Audio

        3.11.1. Added background music and audioclip whenever user reaches gameover or win to make users be more immersive.

   3.12. Change BGM **(Innovative Features)**
      
        3.12.1. Users can change between original and calm background music by pressing "L" key.
   
   3.13. Countdown Timer **(Innovative Features)**

        3.13.1. Countdown will start so that it will not immediate start when next level or start game
        3.13.2. Allows players to get ready for the next level

   3.14. Added LevelThree and LevelFour 

        3.14.1. Additional levels compared to the only two in the original code. Total four levels now in the game
        3.14.2. Level Three is a boss level with 50 health. Boss cannot activate shield.
        3.14.3. Level Four is originally LevelTwo. 
        3.14.4. LevelTwo now needs to kill 20 enemy planes to advance. Total enemies also increase to 7.   

   3.15. Added DoubleDamage powerup **(Innovative Features)**
        
        3.15.1. Powerup for UserPlane to deal double damage when activated. 
        3.15.2. Press key "X" to activate when available
        3.15.3. Text will be shown at the top when doubledamage is available.
        3.15.4. DoubleDamage will only last for 5 seconds. 
        3.15.5. DoubleDamage has a cooldown of 10 seconds.   
   
   **4. Implemented but not Working Properly**

   4.1. Split second before countdown starts

        4.1.1. Userplane able to move or shoot slightly at the start before the countdown starts. 
        4.1.2. Logic wise, nothing should be able to move or shoot until countdown has ended.

   4.2. EnemyPlanes that go reach at the end of screen also counts as a kill

        4.2.1. Enemy Plane that reach the end shouldnt be counted as a kill.

   4.3. Bullets affect EnemyPlanes before it is shown clearly on the screen
        
        4.3.1. EnemyPlanes can be killed before it is shown on the screen. 
        
   4.4. Sometimes after countdown reaches 1, text is not gone straight away
   
        4.4.1  CountdownText should be removed once it reaches one and gameplay has started

   **5. New Java Classes**

   5.1 Class: MusicManager

        5.1.1. Location: com.example.demo.Music
      
        5.1.2. Purpose: Provides methods that handles playback audio for the game

                    - Play background music in a loop
                    - Stop Music when needed
                    - Play sound clips when losing or winning the game
                    - Toggle between backgound music
      
        5.1.3. Key Methods: - playBackgroundMusic(String audioFilePath)
                            - stopBackgroundMusic()
                            - playWinClip(String audioFilePath)
                            - playLoseClip(String audioFilePath)
                            - toggleBackgroundMusic(String track1, String track2)

   5.2 Class: LevelThree

        5.2.1. Location: com.example.demo.Levels
      
        5.2.2. Purpose: Defines the third level of the game, which introduces a boss enemy. The player must defeat the boss to progress to the next level. The class is responsible for setting up the
                      level environment, managing the boss's health, and handling game over and level transition conditions. It also updates the UI to display relevant information during gameplay.
                      
                    - Set up the level environment with a custom background image.
                    - Initialize and manage the boss enemy with health and shield properties.
                    - Display UI elements such as boss health, level objectives, and level name.
                    - Handle game over conditions and level progression when the boss is defeated.
      
        5.2.3. Key Methods: - initializeFriendlyUnits()
                            - checkIfGameOver()
                            - spawnEnemyUnits()
                            - instantiateLevelView()
                            - updateScene()

   5.3 Class: LevelTwo

        5.3.1. Location: com.example.demo.Levels
      
        5.3.2. Purpose: Defines the second level of the game. In this level, the player is tasked with killing a certain number of enemies to advance to the next level. The level includes dynamic
                        enemy spawning, where enemies appear based on a probability, and the player’s kill count is tracked to determine progression. The player must reach a kill target to move
                        forward.
                      
                    - Set up the level environment with a custom background image.
                    - Initialize the player and display the kill count and level instructions.
                    - Spawn enemies with a defined probability and limit the number of enemies.
                    - Track and update the player's kills to determine if they meet the target to proceed to the next level.
      
        5.3.3. Key Methods: - initializeFriendlyUnits()
                            - checkIfGameOver()
                            - spawnEnemyUnits()
                            - instantiateLevelView()
                            - updateKillCount()

   5.4 Class: LevelFour (Originally LevelTwo)

        5.4.1. Location: com.example.demo.Levels
      
        5.4.2. Purpose: Defines the fourth level of the game, where the player faces a boss enemy with an additional shield mechanic. The player must defeat the boss, managing both the boss's health
                        and shield status. The level provides a dynamic gameplay experience where the boss's shield can activate and deactivate, requiring the player to adjust their strategy 
                        accordingly.
                      
                    - Set up the level environment with a custom background image.
                    - Initialize the boss enemy with shield functionality.
                    - Display UI elements such as boss health, shield status, and level name.
                    - Manage the shield's activation, deactivation, and visual representation in the game.
                    - Handle game over and win conditions based on the boss's status.
      
        5.4.3. Key Methods: - initializeFriendlyUnits()
                            - checkIfGameOver()
                            - spawnEnemyUnits()
                            - instantiateLevelView()
                            - updateShieldPosition()
                            - updateScene()

   **6. Modified Java Class**

   6.1. LevelParent

        6.1.1. Stop loop of initiating boss
            - In the original code given, boss was being called many times which led the app to crash and load non stop java heap space 
            - Added timeline.stop() to NextLevel method
              
        6.1.2. Updating movement of UserPlane
            - To make the game more user-friendly and versatile, supporting different input preferences.
            - Allowed diagonal movement by combining WASD and arrow keys.
            - Added support for stopping movement when any directional key (W, A, S, D) is released.
            - Mouse click now fires a projectile.

        6.1.3. Pause functionality added
            - To provide the ability to pause the game, improving user control and enhancing the gameplay experience.
            - New Variables: -> isPaused(boolean): Tracks whether game is paused or not.
                            -> pauseText(Text): Displays text when paused.
                               
            - New Methods: -> pauseGame(): Pauses the game by stopping the timeline and displaying the pause text.
                            -> resumeGame(): Resumes the game by restarting the timeline and hiding the pause text.
                            -> togglePause(): Toggles between paused and unpaused states when KeyCode.P is pressed.
      
        6.1.4. Background Music Toggle
            - To let players personalize their gaming experience by toggling background music.  
            - New Method -> toggleBackgroundMusic(): Swtiches between background music tracks.
            - Added to the key press handler, mapped to KeyCode.L
      
        6.1.5. Projectile boundary Handling
            - Prevent memory leaks and reduce unnecessary computations for off screen objects.   
            - New Methods -> handleOutOfBoundsProjectiles(): Removes bullets that leave the screen
                            -> removeOutOfBoundsActors(): Helper method to handle the removal of such projectiles.
      
        6.1.6. Adding Hitbox 
            - To ensure accurate interactions between projectiles and actors
            - Replaced getBoundsInParent() with getHitbox() so hitbox isnt the image size but rather the actual position of the plane   
    
        6.1.7. Win and Loss enhancements
            - Custom win and gameover backgrounds
            - Custom win and gameover audio clips
            - Provide a more immersive feedback for critical game events

        6.1.8. Double Damage powerup
            - Users can use this powerup when available to defeat the boss. 
            - Mainly used at LevelThree and LevelFour. 

        6.1.9. Countdown Timer
            - Starts a timer of three seconds before each level.
            - Allows user to get ready for each level. 
    
   6.2. ShieldImage

        6.2.1. Change directory path for shield image
            - From .jpg to .png
            - In the original code given, shield image couldn't be found to proceed to (initial)LevelTwo
         
   6.3. UserPlane

        6.3.1. Enhanced Movement Mechanics
            - Enhances the user experience by enabling precise control over the plane's movement in both vertical and horizontal directions, improving gameplay dynamics and responsiveness.  
            - New Variables: -> velocityMultiplier(int)
                            -> horizontalVelocityMultiplier(int)
                            -> verticalVelocity(double)     
                            -> horizontalVelocity(double)
            - Vertical Movement: Implemented acceleration and deceleration for vertical movement, with restrictions to prevent the plane from going off-screen.
            - Horizontal Movement: Introduced left and right movement capabilities, with horizontal velocity and acceleration/deceleration logic.  
        
        6.3.2. Projectile Firing Mechanism
            - Ensures projectiles are fired from a consistent and accurate location relative to the user's plane, improving the game’s mechanics and visuals.
            - Modified the projectile firing logic so that projectiles are fired at an offset from the user plane’s position.
        
        6.3.3.  Enhanced functionality for hitbox
            - Accurate collision detection is crucial for ensuring interactions with other objects in the game and providing correct feedback for these interactions.  

        6.3.4. Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.  

   6.4. ActiveActorDestructible
      
        6.4.1. Enhanced functionality for hitbox
            - By adding getHitbox(), the destructible actor now explicitly supports integration with systems that rely on hitbox detection.
      
        6.4.2. Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.

   6.5. BossPlane
   
        6.5.1. Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.

        6.5.2. Hitbox implementations
            - Added hitbox so that is hits the where the boss is rather than hitting the whole image height of the boss
      
        6.5.3. Call method updateHitbox() to method updatePostion()
            - Ensure the hitbox is always being updated as the boss moves around.
        
        6.5.4. Adjusted vertical bounds for Boss
            - Doesn't allow boss to reach the display Text at the top.

        6.5.5. Changed isShielded() to public
            - To be used in LevelThree so that shield will not be activated.

   6.6. BossProjectile

        6.6.1. Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.

        6.6.2. Hitbox implementations
            - Added hitbox so that is more accurate according to the image when hitting UserPlane
            - Rather than the whole image size but just the front tip of the BossProjectile.
      
        6.6.3. Updates hitbox when the projectile is moving
            - method updateHitbox() is called now in method updatePosition().

   6.7. EnemyPlane
        
        6.7.1. Hitbox implementations
            - Added hitbox so that is hits the where the EnemyPlane is rather than hitting the whole image height of EnemyPlane.

        6.7.2. Updates hitbox when the plane is moving
            - method updateHitbox() is called now in method updatePosition().

        6.7.3. Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.

   6.8. EnemyProjectile

        6.8.1. Hitbox implementations
            - Added hitbox so that is hits the where the EnemyPlane is rather than hitting the whole image height of EnemyPlane

        6.8.2. Updates hitbox when the projectile is moving
            - method updateHitbox() is called now in method updatePosition().  

        6.8.3. Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.


   6.9.  FighterPlane
        
        6.9.1. Added method setHealth()
            - Method to change boss health in LevelThree.

        6.9.2. Updates hitbox when the plane is moving
            - method updateHitbox() is called now in method updatePosition().

        6.9.3 Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.

   6.10. UserProjectile
      
        6.10.1 Refactored package declaration
            - Moved into Actors subpackage for better organization of actor-related classes within the project.

        6.10.2 Hitbox implementations
            - The addition of a hitbox is crucial for precise collision detection, ensuring that the projectile interacts correctly with other game objects

        6.10.3. Updates hitbox when the projectile is moving
            - method updateHitbox() is called now in method updatePosition().

   6.11. LevelTwo
        
        6.11.1. Changed to a new level
            - LevelTwo shifted to Level4

        6.11.2. Condition to LevelThree
            - Goes to LevelThree when it 20 EnemyPlanes has been destroyed    
                 
        6.11.3. UI Design
            - Added Text in game to show the killcount, condition to go to the next level and the level which user is at.

   6.12. LevelOne
        
        6.12.1. Stop timeline when gameover or next level
            - Ensure it stops looping which creates more objects which may not be needed

        6.12.2. UI Design
            - Added Text in game to show the killcount, condition to go to the next level and the level which user is at.

   6.13. BossView(LevelViewLevelTwo)
        
        6.13.1. Refactored to BossView as view is used in two different levels
            - Name was too long and better name coordination within files
        
        6.13.2. Method showShield and hideShield removed as it is already in ShieldImage Class. 
            - Removed unused or repeated methods. 

   6.14. LevelView
        
        6.14.1. Changed position of Gameover
            - Make gameover image to the center to make gameover scene more organised.
            - Set scale to make gameover image smaller.
        
        6.14.2. Set background when gameover
            - Added background rather than the background of the gameplay. 

            New Methods -> setBackground() - method to set background not only for gameover but also win scene. 

   6.15. WinImage
        
        6.15.1. Position changes to image 
            - Makes the position of the win image to be centered

   **7. Unexpected Problems**
   
   7.1. Time to understand the code given
        
        7.1.1. Since the whole assignment is based on developing the software, time was pretty limited to unserstand the code. 
        7.1.2. Might also be the case of lectures still ongoing so parts of the code we may not understand and how to improve it.
