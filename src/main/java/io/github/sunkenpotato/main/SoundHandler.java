package io.github.sunkenpotato.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;



public class SoundHandler {
    Logger LOGGER = LogManager.getLogger(this);
    Clip clip;
    URL[] soundURL = new URL[30];

    public SoundHandler() {
        soundURL[0] = getClass().getResource("/sound/music/ambient0.wav");
        soundURL[1] = getClass().getResource("/sound/sfx/coin.wav");
        soundURL[2] = getClass().getResource("/sound/sfx/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/sfx/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/sfx/fanfare.wav");
    }

    public void openAudioF(int index) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            LOGGER.error(e.fillInStackTrace());
        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}

