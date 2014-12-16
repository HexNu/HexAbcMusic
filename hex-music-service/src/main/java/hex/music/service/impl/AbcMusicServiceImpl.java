package hex.music.service.impl;

import hex.music.core.domain.Tune;
import hex.music.service.AbcMusicService;
import hex.music.service.AbstractService;
import hex.music.service.command.tune.GetAbcDocCommand;
import hex.music.service.command.tune.GetAllTunesCommand;
import hex.music.service.command.tune.GetTuneCommand;
import hex.music.service.command.tune.SaveTuneCommand;
import java.util.List;

/**
 *
 * @author hln
 */
public class AbcMusicServiceImpl extends AbstractService implements AbcMusicService {

    public AbcMusicServiceImpl(String fingerprint) {
        super(fingerprint);
    }

    @Override
    public Tune getTune(Long id) {
        return executor.execute(new GetTuneCommand(id), fingerprint);
    }

    @Override
    public Tune saveTune(Tune tune) {
        return executor.executeInTransaction(new SaveTuneCommand(tune), fingerprint);
    }

    @Override
    public List<Tune> getAllTunes() {
        return executor.execute(new GetAllTunesCommand(), fingerprint);
    }

    @Override
    public String getAbcDocument(List<Tune> tunes) {
        return executor.execute(new GetAbcDocCommand(tunes), fingerprint);
    }
}
