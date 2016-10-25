package hex.music.fw.search;

import hex.music.fw.domain.SearchResultListWrapper;
import hex.music.fw.domain.SearchResult;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class ResultCache {

    private static final ResultCache INSTANCE = new ResultCache();
    private final List<SearchResult> resultList;
    private String queryString;

    private ResultCache() {
        this.resultList = new ArrayList<>();
        this.queryString = "";
    }

    public static ResultCache getInstance() {
        return INSTANCE;
    }

    public void clear() {
        resultList.clear();
        queryString = "";
    }

    public boolean queryMatches(String query) {
        return query != null && queryString.equals(query);
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString != null ? queryString : "";
    }

    public void setResults(List<SearchResult> resultList) {
        this.resultList.addAll(resultList);
    }

    public SearchResultListWrapper getPagedResult(int limit, int offset) {
        SearchResultListWrapper result = new SearchResultListWrapper(limit, queryString);
        if (offset >= limit) {
            result.setPrevious(offset - limit);
        }
        if (offset + limit < resultList.size()) {
            result.setNext(offset + limit);
        }
        for (int i = offset; i <= resultList.size() - 1 && i < offset + limit; i++) {
            result.addResult(resultList.get(i));
        }
        return result;
    }
}
