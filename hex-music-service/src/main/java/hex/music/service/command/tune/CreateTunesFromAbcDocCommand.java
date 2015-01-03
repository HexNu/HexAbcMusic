package hex.music.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.io.parse.AbcDocumentParser;
import hex.music.service.command.AbstractServiceCommand;
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
public class CreateTunesFromAbcDocCommand extends AbstractServiceCommand<List<Tune>> {

    private final InputStream stream;

    public CreateTunesFromAbcDocCommand(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public List<Tune> execute() {
        List<Tune> result = new ArrayList<>();
        try {
            List<Tune> tunesToSave = new AbcDocumentParser(stream).parse();
            tunesToSave.stream().forEach((tune) -> {
                result.add(executeSubcommand(new SaveTuneCommand(tune)));
            });
        } catch (IOException ex) {
            Logger.getLogger(CreateTunesFromAbcDocCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
