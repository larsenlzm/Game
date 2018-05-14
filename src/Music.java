import javax.sound.sampled.*;

/**
 * Class for sound files
 *
 * @author s325919, s325894
 */

public class Music {

    private Clip clip;
    private String musicFile;

    /**
     * Initiates the clip
     *
     * @param fileName String that contains the url of the sound file.
     */
    Music(String fileName) {
        try {
            musicFile = fileName;
            AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResource(fileName));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restarts the clip.
     */

    void play() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.start();
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a clip that only gets played once.
     */
    void playonce() {
        Clip onceClip;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResource(musicFile));
            onceClip = AudioSystem.getClip();
            onceClip.open(ais);
            onceClip.setFramePosition(0);
            onceClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Stops the clip
     */

    void stop() {
            clip.stop();
    }

    /**
     * Starts the clip
     */

    public void resume() {
        clip.start();
    }

    /**
     * Loops the clip
     */

    void loop() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isActive() {
        return clip.isActive();
    }
}