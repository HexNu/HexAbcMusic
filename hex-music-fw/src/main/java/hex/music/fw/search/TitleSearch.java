package hex.music.fw.search;

import hex.music.fw.domain.SearchResult;
import java.util.ArrayList;
import java.util.List;
import se.digitman.lightxml.XmlNode;

/**
 *
 * @author hln
 */
public class TitleSearch extends AbstractFwPageReader<List<SearchResult>> {

    private static final String SEARCH_PATH = "PmWikiSv/Sök?action=search&q=",
            TUNE_PAGE_BASE_PATH = "http://www.folkwiki.se/Musik/";

    public TitleSearch(String queryString) {
        super(SEARCH_PATH, queryString);
    }

    @Override
    public List<SearchResult> execute() {
        return getResultList();
    }

    private List<SearchResult> getResultList() {
        List<SearchResult> result = new ArrayList<>();
        XmlNode resultNode = getResult();
        if (!resultNode.getName().equals("no-result")) {
            resultNode.getChildren("li").stream().filter((li) -> (li.getChild("a").getAttribute("href").startsWith(TUNE_PAGE_BASE_PATH))).forEach((li) -> {
                String url = li.getChild("a").getAttribute("href");
                String text = li.getChild("a").getText();
                result.add(new SearchResult(url, text));
            });
        }
        return result;
    }

    private XmlNode getResult() {
        XmlNode result = getResultListNode();
        if (result != null) {
            return result;
        }
        return null;
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
