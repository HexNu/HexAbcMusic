package hex.music.io.out;

import hex.music.core.domain.Tune;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class GifStreamProducer extends AbstractProducer<InputStream> {

    public GifStreamProducer(Tune tune) {
        super(tune);
    }

    @Override
    public InputStream produce() {
        return process();
    }

    private InputStream process() {
        Tune tune = getTune();
        if (tune != null) {
            try {
                File psFile = getPsAsFile();
                File gifFile = new File(Path.GIF_FILE);
                List<String> commands = new ArrayList<>();
                commands.add(Command.PS_2_GIF);
                commands.add(psFile.getAbsolutePath());
                commands.add(Path.GIF_FILE);
                ProcessBuilder builder = new ProcessBuilder(commands);
                Process process = builder.start();
                process.waitFor();
                return new FileInputStream(gifFile);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(GifStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
