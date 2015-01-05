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
public class PdfStreamProducer extends AbstractProducer<InputStream> {


    public PdfStreamProducer(List<Tune> tunes) {
        super(tunes);
    }

    @Override
    public InputStream produce() {
        return process();
    }

    private InputStream process() {
        try {
            File psFile = getPsAsFile();
            File pdfFile = new File(Path.PDF_FILE);
            pdfFile.deleteOnExit();
            List<String> commands = new ArrayList<>();
            commands.add(Command.PS_2_PDF);
            commands.add(psFile.getAbsolutePath());
            commands.add(Path.PDF_FILE);
            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();
            process.waitFor();
            return new FileInputStream(pdfFile);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PdfStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
