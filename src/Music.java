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
     * @param file String that contains the url of the sound file.
     */
    public Music(String file) {
        try {
            musicFile = file;
            AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResource(file));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restarts the clip.
     */

    public void play() {
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
    public void playonce() {
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
    public void stop() {
            clip.stop();
    }

    /**
     * Loops the clip
     */
    public void loop() {
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

}