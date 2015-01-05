package hex.music.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.service.command.AbstractServiceCommand;
import hex.music.service.command.io.GetFirstLineGifByteArrayCommand;
import hex.music.service.command.io.GetFirstLineGifStreamCommand;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class SaveTuneCommand extends AbstractServiceCommand<Tune> {

    private final Tune tune;

    public SaveTuneCommand(Tune tune) {
        this.tune = tune;
    }

    @Override
    public Tune execute() {
        Tune result = getDaoFactory().getTuneDao().save(tune);
        byte[] start = executeSubcommand(new GetFirstLineGifByteArrayCommand(tune));
        result.setFirstLine(start);
        return result;
    }

}
