package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.service.command.AbstractServiceCommand;
import hex.music.service.command.tune.SaveTuneCommand;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class GetFirstLineGifByteArrayCommand extends AbstractServiceCommand<byte[]> {

    private final Tune tune;

    public GetFirstLineGifByteArrayCommand(Tune tune) {
        this.tune = tune;
    }

    @Override
    public byte[] execute() {
        InputStream firstLine = executeSubcommand(new GetFirstLineGifStreamCommand(tune));
        return getBytesFromInputStream(firstLine);
    }

    private byte[] getBytesFromInputStream(InputStream is) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = is.read(buffer)) != -1;) {
                os.write(buffer, 0, len);
            }
            os.flush();
            return os.toByteArray();
        } catch (IOException e) {
            Logger.getLogger(SaveTuneCommand.class.getName()).log(Level.SEVERE, "Could not create byte array");
            return null;
        }
    }
}
