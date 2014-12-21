package hex.music.fw.domain;

/**
 *
 * @author hln
 */
public class SearchResult {

    private final String pageUrl;
    private final String text;
    private final String fwId;

    public SearchResult(String url, String text) {
        this.pageUrl = url;
        this.fwId = url.substring(url.lastIndexOf("Musik/") + 6);
        this.text = text;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public String getFwId() {
        return fwId;
    }

    public String getText() {
        return text;
    }
}
