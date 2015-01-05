package hex.music.io.out;

import hex.music.core.domain.Tune;
import hex.music.io.file.AbcFileWriter;
import hex.music.io.file.ZipFileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class ZippedMidiFilesStreamProducer extends AbstractProducer<InputStream> {

    public ZippedMidiFilesStreamProducer(List<Tune> tunes) {
        super(tunes);
    }

    @Override
    public InputStream produce() {
        InputStream result = null;
        process();
        try {
            File zipFile = createZipFile();
            result = new FileInputStream(zipFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZippedMidiFilesStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private File createZipFile() {
        return new ZipFileWriter(new File(Path.MIDI_DIR), FileExtension.MID.get()).write();
    }

    private void process() {
        try {
            File abcFile = new File(Path.ZIPPED_MIDI_FILE);
            new AbcFileWriter(getAbcDocumentAsString(), abcFile).write();
            List<String> commands = new ArrayList<>();
            commands.add(Command.ABC_2_MIDI);
            commands.add(abcFile.getAbsolutePath());
            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ZippedMidiFilesStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
