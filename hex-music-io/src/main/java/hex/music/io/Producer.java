package hex.music.io;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 * @param <T>
 */
public interface Producer<T> {

    T produce();

    public interface Command {

        public static final String ABCM_2_PS = "abcm2ps",
                PS_2_GIF = "ps2gif",
                PS_2_PDF = "ps2pdf",
                ABC_2_MIDI = "abc2midi";
    }

    public interface Path {

        public static final String BASE_DIR = "/tmp/hex/",
                MIDI_DIR = BASE_DIR + "midi/",
                ABC_FILE = BASE_DIR + "result.abc",
                GIF_FILE = BASE_DIR + "result.gif",
                GIF_START_FILE = BASE_DIR + "result-start.gif",
                PDF_FILE = BASE_DIR + "result.pdf",
                PS_FILE = BASE_DIR + "result.ps",
                ZIPPED_ABC_FILE = MIDI_DIR + "result.abc";
    }

    public enum FileExtension {

        ABC, GIF, MID, PDF, PS, ZIP;

        public String get() {
            return name().toLowerCase();
        }

        public static List<String> getAll() {
            ArrayList<String> result = new ArrayList<>();
            for (FileExtension e : values()) {
                result.add(e.name().toLowerCase());

            }
            return result;
        }
    }
}
