package hex.music.core.domain.impl;

import hex.music.core.domain.TuneListWrapper;
import hex.music.core.domain.Tune;
import java.util.List;

/**
 *
 * @author hln
 */
public class ResultListWrapper implements TuneListWrapper {

    private final List<Tune> tunes;
    private final Integer limit;
    private Integer previous;
    private Integer next;
    private String query;

    public ResultListWrapper(List<Tune> tunes, Integer limit, String query) {
        this.tunes = tunes;
        this.limit = limit;
        this.query = query;
    }

    @Override
    public List<Tune> getTunes() {
        return tunes;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public Integer getPrevious() {
        return previous;
    }

    @Override
    public void setPrevious(Integer previous) {
        this.previous = previous;
    }

    @Override
    public Integer getNext() {
        return next;
    }

    @Override
    public void setNext(Integer next) {
        this.next = next;
    }

    @Override
    public String getQuery() {
        return query;
    }
}
