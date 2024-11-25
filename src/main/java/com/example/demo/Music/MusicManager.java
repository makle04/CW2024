package com.example.demo.Music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicManager {
    private static Clip bgmClip;

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
}

