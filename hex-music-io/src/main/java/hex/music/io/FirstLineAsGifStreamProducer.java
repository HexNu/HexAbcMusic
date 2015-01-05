package hex.music.io;

import hex.music.core.domain.Tune;
import hex.music.io.file.AbcFileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class FirstLineAsGifStreamProducer extends AbstractProducer<InputStream> {

    public FirstLineAsGifStreamProducer(Tune tune) {
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
                File abcFile = createAbcStartFile(tune);
                File psFile = getPsAsFile("0cm", abcFile);
                File gifFile = new File(Path.GIF_START_FILE);
                List<String> commands = new ArrayList<>();
                commands.add(Command.PS_2_GIF);
                commands.add(psFile.getAbsolutePath());
                commands.add(Path.GIF_START_FILE);
                ProcessBuilder builder = new ProcessBuilder(commands);
                Process process = builder.start();
                process.waitFor();
                return new FileInputStream(gifFile);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(FirstLineAsGifStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private File createAbcStartFile(Tune tune) {
        StringBuilder tuneBuilder = new StringBuilder("X:1");
        tuneBuilder.append("M:").append(tune.getMeter()).append("\n");
        tuneBuilder.append("L:").append(tune.getUnitNoteLength()).append("\n");
        tuneBuilder.append("K:").append(tune.getKey().getSignature().getCode()).append("\n");
        tuneBuilder.append(getBody(tune)).append("\n");
        File abcFile = new AbcFileWriter(tuneBuilder.toString(), new File(Path.ABC_FILE)).write();
        abcFile.deleteOnExit();
        return abcFile;
    }

    private String getBody(Tune tune) {
        return stripBody(tune.getVoices().get(0).getBody().split("\n")[0]);
    }

    private String stripBody(String body) {
        StringBuilder result = new StringBuilder();
        StringReader reader = new StringReader(body);
        boolean cordStarted = false;
        boolean accentStarted = false;
        boolean plusAccentStarted = false;
        boolean inlineChangeStarted = false;
        for (int i = 0; i < body.length(); i++) {
            try {
                String s = Character.toString((char) reader.read());
                switch (s) {
                    case "\"":
                        cordStarted = !cordStarted;
                        break;
                    case "[":
                        inlineChangeStarted = true;
                        break;
                    case "]":
                        inlineChangeStarted = false;
                        break;
                    case "!":
                        accentStarted = !accentStarted;
                        break;
                    case "+":
                        plusAccentStarted = !plusAccentStarted;
                        break;
                }
                if (!cordStarted && !accentStarted && !plusAccentStarted && !inlineChangeStarted
                        && !s.matches("[\"\\[\\]!\\+]")) {
                    result.append(s);
                }
            } catch (IOException ex) {
                Logger.getLogger(FirstLineAsGifStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result.toString();
    }
}
