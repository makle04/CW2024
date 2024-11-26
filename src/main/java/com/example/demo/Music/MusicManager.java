package com.example.demo.Music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicManager {
    private static Clip bgmClip;
    private static Clip winClip;
    private static Clip loseClip;
    private static boolean isPlayingFirstTrack = true;

    public static void playBackgroundMusic(String audioFilePath) {
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    MusicManager.class.getResource(audioFilePath)
            );
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }

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
            FloatControl gainControl = (FloatControl) winClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-5.0f);
            winClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            FloatControl gainControl = (FloatControl) loseClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-5.0f);
            loseClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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



