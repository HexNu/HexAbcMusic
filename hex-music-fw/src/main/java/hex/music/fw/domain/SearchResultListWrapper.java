package hex.music.fw.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class SearchResultListWrapper {

    private final List<SearchResult> results;
    private final Integer limit;
    private Integer previous;
    private Integer next;
    private final String query;

    public SearchResultListWrapper(Integer limit, String query) {
        this.results = new ArrayList<>();
        this.limit = limit;
        this.query = query;
    }
    
    public void addResult(SearchResult result) {
        results.add(result);
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPrevious() {
        return previous;
    }

    public void setPrevious(Integer previous) {
        this.previous = previous;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public String getQuery() {
        return query;
    }
}
