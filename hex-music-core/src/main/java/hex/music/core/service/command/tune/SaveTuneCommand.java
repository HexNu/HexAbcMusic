package hex.music.core.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.core.service.command.AbstractServiceCommand;

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
        return getDaoFactory().getTuneDao().save(tune);
    }
}