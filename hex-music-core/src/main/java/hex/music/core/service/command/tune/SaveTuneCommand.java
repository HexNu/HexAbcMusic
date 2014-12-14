package hex.music.core.service.command.tune;

import hex.music.core.domain.MetaData;
import hex.music.core.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class SaveTuneCommand extends AbstractServiceCommand<MetaData> {

    private final MetaData metaData;

    public SaveTuneCommand(MetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public MetaData execute() {
        return getDaoFactory().getMetaDataDao().save(metaData);
    }
}
