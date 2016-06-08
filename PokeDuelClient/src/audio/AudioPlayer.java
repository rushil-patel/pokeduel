package audio;

import java.io.*;
import javax.sound.sampled.*;

/**
 *
 * @author rushi_000
 */
public class AudioPlayer
{

    public static void play(String path)
    {
        try
        {
            File file = new File(path);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(file);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (Exception e)
        {
            //whatevers
        }
    }
}
