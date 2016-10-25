package hex.music.fw.search;

import hex.music.fw.domain.SearchResultListWrapper;
import hex.music.fw.domain.SearchResult;
import java.util.ArrayList;
import java.util.List;
import se.digitman.lightxml.XmlNode;

/**
 *
 * @author hln
 */
public class TitleSearch extends AbstractFwPageReader {

    private static final String SEARCH_PATH = "?n=Meta.Start&action=search&q=",
            TUNE_PAGE_BASE_PATH = "http://www.folkwiki.se/Musik/";
//    private static final String SEARCH_PATH = "PmWikiSv/Sök?action=search&q=",
//            TUNE_PAGE_BASE_PATH = "http://www.folkwiki.se/Musik/";
    private List<SearchResult> searchResult;
    private final String queryString;
    private final ResultCache cache = ResultCache.getInstance();

    public TitleSearch(String queryString) {
        super(SEARCH_PATH, queryString);
        this.queryString = queryString;
        createResultList();
    }

    public SearchResultListWrapper getResult(int limit, int offset) {
        return cache.getPagedResult(limit, offset);
    }

    private void createResultList() {
        if (queryString != null && !cache.queryMatches(queryString)) {
            cache.clear();
            cache.setQueryString(queryString);
            searchResult = new ArrayList<>();
            XmlNode resultNode = getResultListNode();
            if (!resultNode.getName().equals("no-result")) {
                resultNode.getChildren("li").stream().filter((li) -> (li.getChild("a").getAttribute("href").startsWith(TUNE_PAGE_BASE_PATH))).forEach((li) -> {
                    String url = li.getChild("a").getAttribute("href");
                    String text = li.getChild("a").getText();
                    searchResult.add(new SearchResult(url, text));
                });
            }
            cache.setResults(searchResult);
        }
    }

    private XmlNode getResultListNode() {
        XmlNode wikiSearch = null;
        if (getWikiTextNode() != null) {
            for (XmlNode div : getWikiTextNode().getChildren("div")) {
                if (div.getAttribute("class").equals("wikisearch")) {
                    wikiSearch = div;
                    break;
                }
            }
            if (wikiSearch != null) {
                for (XmlNode div : wikiSearch.getChildren("div")) {
                    if (div.hasChildNamed("ul")) {
                        return div.getChild("ul");
                    }
                }
            }
        }
        return null;
    }
}
