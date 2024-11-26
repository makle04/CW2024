   1. GitHub: https://github.com/makle04/CW2024

   2. Compilation Instructions

            2.1. Ensure that computer that is going to run the program has volume loud enough for user to hear audio from the game.
   
            2.2. Download zipfile from Github and extract it and run it on Intellij
   
            2.3. Load Maven when it is prompted at the bottom right
   
            2.4. Open file Main from file path src/main/java/com.example.demo/Controller/Main.java

            2.5. Once Maven has fully been loaded and Main.java is open in the editor, User can run main to start the game.

   3. Implemented and Working Properly
   
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

          3.11.1 Added background music and audioclip whenever user reaches gameover or win to make users be more immersive.

      3.12. 

   4. Implemented but not Working Properly

     4.1 UserPlane not kept in bounds

          4.1.1. UserPlane does not have a bound for the right side of the screen meaning it will travel off the screen since horizontal movement has been implemented

  
