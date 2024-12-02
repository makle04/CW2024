package com.example.demo.Music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * The MusicManager class is responsible for handling the playback of background music
 * and sound effects for win and lose conditions in the game. It allows for the playback,
 * stopping, and toggling of background music as well as playing specific sound clips.
 */
public class MusicManager {
    private static Clip bgmClip;
    private static Clip winClip;
    private static Clip loseClip;
    private static boolean isPlayingFirstTrack = true;

    /**
     * Plays background music in a loop. If music is already playing, it will not start another track.
     *
     * @param audioFilePath The file path to the audio file for the background music.
     */
    public static void playBackgroundMusic(String audioFilePath) {
        try {
            // Prevent multiple instances of the same background music playing
            if (bgmClip != null && bgmClip.isRunning()) {
                return;
            }
            // Load the audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    MusicManager.class.getResource(audioFilePath)
            );
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            // Adjust the volume (master gain)
            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Lower the volume
            // Loop the background music continuously
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing background music and releases the clip.
     */
    public static void stopBackgroundMusic() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }

    /**
     * Plays the win sound effect. If it's already playing, it stops the current clip and restarts it.
     *
     * @param audioFilePath The file path to the win sound effect audio file.
     */
    public static void playWinClip(String audioFilePath) {
        try {
            if (winClip != null && winClip.isRunning()) {
                winClip.stop();
                winClip.close();
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    MusicManager.class.getResource(audioFilePath)
            );
            winClip = AudioSystem.getClip();
            winClip.open(audioStream);
            // Adjust the volume (master gain)
            FloatControl gainControl = (FloatControl) winClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-5.0f); // Lower the volume
            winClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the lose sound effect. If it's already playing, it stops the current clip and restarts it.
     *
     * @param audioFilePath The file path to the lose sound effect audio file.
     */
    public static void playLoseClip(String audioFilePath) {
        try {
            if (loseClip != null && loseClip.isRunning()) {
                loseClip.stop();
                loseClip.close();
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    MusicManager.class.getResource(audioFilePath)
            );
            loseClip = AudioSystem.getClip();
            loseClip.open(audioStream);
            // Adjust the volume (master gain)
            FloatControl gainControl = (FloatControl) loseClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-5.0f); // Lower the volume
            loseClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Toggles between two background music tracks. Stops the current background music and plays the other track.
     *
     * @param track1 The file path for the first background music track.
     * @param track2 The file path for the second background music track.
     */
    public static void toggleBackgroundMusic(String track1, String track2) {
        stopBackgroundMusic();
        if (isPlayingFirstTrack) {
            playBackgroundMusic(track2);
        } else {
            playBackgroundMusic(track1);
        }
        isPlayingFirstTrack = !isPlayingFirstTrack;
    }
}
