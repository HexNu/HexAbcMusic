package hex.music.io.out;

import hex.music.core.domain.Tune;
import hex.music.io.file.AbcFileWriter;
import java.io.File;
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
public class ZippedGifFilesStreamProducer extends AbstractProducer<InputStream> {

    public ZippedGifFilesStreamProducer(List<Tune> tunes) {
        super(tunes);
    }

    @Override
    public InputStream produce() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void process() {
//        try {
//            File abcFile = new File(Path.ZIPPED_MIDI_FILE);
//            new AbcFileWriter(getAbcDocumentAsString(), abcFile).write();
//            List<String> commands = new ArrayList<>();
//            commands.add(Command.ABC_2_MIDI);
//            commands.add(abcFile.getAbsolutePath());
//            ProcessBuilder builder = new ProcessBuilder(commands);
//            Process process = builder.start();
//            process.waitFor();
//        } catch (IOException | InterruptedException ex) {
//            Logger.getLogger(ZippedMidiFilesStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
