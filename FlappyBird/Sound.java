import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    private static Clip bgmClip;

    // putar sfx singkat
    public static void play(String soundName) {
        try {
            AudioInputStream audioStream = getAudioStream(soundName);
            if (audioStream != null) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            }
        } catch (Exception e) {}
    }

    // putar bgm looping
    public static void playBGM(String soundName) {
        try {
            stopBGM();
            AudioInputStream audioStream = getAudioStream(soundName);
            if (audioStream != null) {
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioStream);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
                bgmClip.start();
            }
        } catch (Exception e) {}
    }

    // stop bgm
    public static void stopBGM() {
        try {
            if (bgmClip != null) {
                if (bgmClip.isRunning()) {
                    bgmClip.stop();
                }
                bgmClip.close();
                bgmClip = null;
            }
        } catch (Exception e) {}
    }

    private static AudioInputStream getAudioStream(String soundName) {
        try {
            File file = new File("assets/sounds/" + soundName);
            if (file.exists()) {
                return AudioSystem.getAudioInputStream(file);
            }
            URL res = Sound.class.getResource("/assets/sounds/" + soundName);
            if (res == null) res = Sound.class.getResource("assets/sounds/" + soundName);
            if (res != null) {
                return AudioSystem.getAudioInputStream(res);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
