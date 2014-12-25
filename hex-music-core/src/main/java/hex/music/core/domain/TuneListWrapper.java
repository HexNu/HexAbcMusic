package hex.music.core.domain;

import java.util.List;

/**
 *
 * @author hln
 */
public interface TuneListWrapper {

    Integer getLimit();

    Integer getNext();

    void setNext(Integer next);

    Integer getPrevious();

    void setPrevious(Integer previous);

    List<Tune> getTunes();

    String getQuery();

    String getNotesQuery();
}
