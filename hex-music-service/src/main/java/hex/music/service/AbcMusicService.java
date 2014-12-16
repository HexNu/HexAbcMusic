package hex.music.service;

import hex.music.core.domain.Tune;
import java.util.List;

/**
 *
 * @author hln
 */
public interface AbcMusicService {

    Tune getTune(Long id);
    
    Tune saveTune(Tune tune);

    List<Tune> getAllTunes();

    String getAbcDocument(List<Tune> tunes);

}
