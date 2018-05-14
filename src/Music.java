import javax.sound.sampled.*;

public class Music {

    private Clip clip;
    private String musicFile;

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

    void stop() {
            clip.stop();
    }

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

    public boolean isActive() {
        return clip.isActive();
    }
}