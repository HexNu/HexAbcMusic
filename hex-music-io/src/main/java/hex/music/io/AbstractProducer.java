package hex.music.io;

import hex.music.core.AbcConstants;
import hex.music.core.domain.Clef;
import hex.music.core.domain.Tune;
import hex.music.core.domain.Voice;
import hex.music.io.file.AbcFileWriter;
import hex.music.io.file.SimpleFileWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 * @param <T>
 */
public abstract class AbstractProducer<T> implements Producer {

    private final static String INDENT = "-I",
            DEFAULT_INDENT_VALUE = "0.7cm",
            PS_OUTPUT_FILE_FLAG = "-O";

    private final List<Tune> tunes = new ArrayList<>();
    private final StringBuilder docBuilder = new StringBuilder();

    public AbstractProducer(Tune tune) {
        cleanUpDirectories();
        tunes.add(tune);
    }

    public AbstractProducer(List<Tune> tunes) {
        cleanUpDirectories();
        this.tunes.addAll(tunes);
    }

    @Override
    public abstract T produce();

    protected String getAbcDocumentAsString() {
        String result = createResult();
        try {
            changeEncoding(result);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AbstractProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Returns the first or only tune from the list.
     *
     * Convenience method.
     *
     * @return
     */
    protected Tune getTune() {
        return tunes.get(0);
    }

    protected List<Tune> getTunes() {
        return tunes;
    }

    protected File getPsAsFile() {
        return getPsAsFile(DEFAULT_INDENT_VALUE);
    }

    private File createTempAbcFile() {
        File abcFile = new File(Path.ABC_FILE);
        abcFile.deleteOnExit();
        return new AbcFileWriter(getAbcDocumentAsString(), abcFile).write();
    }

    protected File getPsAsFile(String indentValue) {
        return getPsAsFile(indentValue, createTempAbcFile());
    }

    protected File getPsAsFile(String indentValue, File abcFile) {
        try {
            File psFile = new File(Path.PS_FILE);
            psFile.deleteOnExit();
            List<String> commands = new ArrayList<>();
            commands.add(Command.ABCM_2_PS);
            commands.add(PS_OUTPUT_FILE_FLAG);
            commands.add(Path.PS_FILE);
            commands.add(INDENT);
            commands.add(indentValue);
            commands.add(abcFile.getAbsolutePath());
            ProcessBuilder builder = new ProcessBuilder(commands);
            Process process = builder.start();
            process.waitFor();
            psFile = changeTitle(psFile);
            return psFile;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(AbstractProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String changeEncoding(String result) throws UnsupportedEncodingException {
        return new String(result.getBytes(AbcConstants.ABC_ENCODING), AbcConstants.ABC_ENCODING);
    }

    private String createResult() {
        for (Tune tune : getTunes()) {
            docBuilder.append("X: ").append(tune.getId()).append("\n");
            createTuneHeader(tune);
            createTuneMetaData(tune);
            createTuneBody(tune);
            docBuilder.append("\n\n");
        }
        return docBuilder.toString();
    }

    private void createTuneHeader(Tune tune) {
        createKeyValueRow(AbcConstants.Field.T, tune.getTitle());
        createKeyValueRow(AbcConstants.Field.T, tune.getSubheader());
        createKeyValueRow(AbcConstants.Field.C, tune.getComposer());
        createKeyValueRow(AbcConstants.Field.Q, tune.getTempo());
    }

    private void createKeyValueRow(AbcConstants.Field key, String value) {
        if (value != null) {
            if (value.contains("\n")) {
                String[] values = value.split("\\n");
                for (String v : values) {
                    docBuilder.append(key.name()).append(": ").append(v).append("\n");
                }
            } else {
                docBuilder.append(key.name()).append(": ").append(value).append("\n");
            }
        }
    }

    private void createTuneMetaData(Tune tune) {
        createKeyValueRow(AbcConstants.Field.R, tune.getRythm());
        createKeyValueRow(AbcConstants.Field.O, tune.getRegion());
        createKeyValueRow(AbcConstants.Field.S, tune.getSource());
        createKeyValueRow(AbcConstants.Field.H, tune.getHistory());
        createKeyValueRow(AbcConstants.Field.N, tune.getNotes());
        createKeyValueRow(AbcConstants.Field.Z, tune.getTranscriber());
        createKeyValueRow(AbcConstants.Field.B, tune.getBibliography());
        createKeyValueRow(AbcConstants.Field.D, tune.getDiscography());
        createKeyValueRow(AbcConstants.Field.F, tune.getUri());
        createKeyValueRow(AbcConstants.Field.M, tune.getMeter());
        createKeyValueRow(AbcConstants.Field.L, tune.getUnitNoteLength());
        createMusicKeyRow(tune);
    }

    private void createMusicKeyRow(Tune tune) {
        docBuilder.append("K: ").append(tune.getKey().getSignature().getCode());
        Clef clef = tune.getKey().getClef();
        if (!clef.getType().equals(Clef.DEFAULT_TYPE)) {
            docBuilder.append(" clef=").append(clef.getType().getCode());
        }
        if (clef.getMiddle() != null) {
            docBuilder.append(" middle=").append(clef.getMiddle());
        }
        if (clef.getTranspose() != Clef.DEFAULT_TRANSPOSE) {
            docBuilder.append(" transpose=").append(clef.getTranspose());
        }
        docBuilder.append("\n");
    }

    private void createTuneBody(Tune tune) {
        tune.getVoices().stream().map((voice) -> {
            docBuilder.append("V: ").append(voice.getVoiceCode() != null ? voice.getVoiceCode() : Voice.DEFAULT_VOICE_CODE);
            if (tune.getVoices().size() > 1) {
                if (voice.getName() != null) {
                    docBuilder.append(" name=\"").append(voice.getName()).append("\"");
                }
                if (voice.getSubname() != null) {
                    docBuilder.append(" subname=\"").append(voice.getSubname()).append("\"");
                }
            }
            docBuilder.append(" clef=").append(voice.getClef().getType().getCode());
            return voice;
        }).map((voice) -> {
            if (voice.getClef().getMiddle() != null) {
                docBuilder.append(" middle=").append(voice.getClef().getMiddle());
            }
            if (voice.getClef().getTranspose() != 0) {
                docBuilder.append(" transpose=").append(voice.getClef().getTranspose());
            }
            return voice;
        }).forEach((voice) -> {
            docBuilder.append("\n").append(voice.getBody());
        });
    }

    private void cleanUpDirectories() {
        FilenameFilter filter = (File dir, String name) -> {
            return FileExtension.getAll().stream().anyMatch((extension) -> (name.endsWith("." + extension)));
        };
        new File(Path.MIDI_DIR).mkdirs();
        for (File fileToDelete : new File(Path.BASE_DIR).listFiles(filter)) {
            fileToDelete.delete();
        }
        for (File fileToDelete : new File(Path.MIDI_DIR).listFiles(filter)) {
            fileToDelete.delete();
        }
    }

    private File changeTitle(File psFile) {
        try {
            StringBuilder fileContent = new StringBuilder();
            List<String> lines = Files.readAllLines(psFile.toPath(), Charset.forName(AbcConstants.UTF_8));
            lines.stream().filter((line) -> (!line.startsWith("%CommandLine"))).forEach((line) -> {
                if (line.startsWith("%%Title")) {
                    fileContent.append("%%Title: HexMusic").append("\n");
                } else {
                    fileContent.append(line).append("\n");
                }
            });
            return new SimpleFileWriter(fileContent.toString(), psFile).write();
        } catch (IOException ex) {
            Logger.getLogger(AbstractProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return psFile;
    }
}
