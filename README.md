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

   3.4. Added mouse leftclick to shoot from UserPlane

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

   3.12. Change BGM
      
      3.12.1. Users can change between original and calm background music by pressing "L" key.
   
   **4. Implemented but not Working Properly**

   4.1 UserPlane not kept in bounds

      4.1.1. UserPlane does not have a bound for the right side of the screen meaning it will travel off the screen since horizontal movement has been implemented.
   
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
              - New Variables: -> isPaused(boolean): Tracks whether game is paused or not
                               -> pauseText(Text): Displays text when paused
                               
              - New Methods: -> pauseGame(): Pauses the game by stopping the timeline and displaying the pause text.
                             -> resumeGame(): Resumes the game by restarting the timeline and hiding the pause text.
                             -> togglePause(): Toggles between paused and unpaused states when KeyCode.P is pressed.
      6.1.4.                 

   6.2. ShieldImage

      6.2.1 Change directory path for shield image
              - From .jpg to .png
              - In the original code given, shield image couldn't be found to proceed to (initial)LevelTwo
         
