package hex.music.io.out;

import hex.music.core.domain.Tune;
import hex.music.io.file.AbcFileWriter;
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
public class MidiStreamProducer extends AbstractProducer<InputStream> {

    private static final String MIDI_FILE_FLAG = "-o";

    public MidiStreamProducer(Tune tune) {
        super(tune);
    }

    @Override
    public InputStream produce() {
        try {
            File result = process();
            return result != null ? new FileInputStream(result) : null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MidiStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private File process() {
        File result = null;
        try {
            File abcFile = new File(Path.ZIPPED_MIDI_FILE);
            new AbcFileWriter(getAbcDocumentAsString(), abcFile).write();
            List<String> commands = new ArrayList<>();
            commands.add(Command.ABC_2_MIDI);
            commands.add(abcFile.getAbsolutePath());
            commands.add(MIDI_FILE_FLAG);
            commands.add(Path.MIDI_FILE);
            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();
            process.waitFor();
            result = new File(Path.MIDI_FILE);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ZippedMidiFilesStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
