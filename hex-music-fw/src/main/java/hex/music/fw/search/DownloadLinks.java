package hex.music.fw.search;

import java.util.HashMap;
import java.util.Map;
import se.digitman.lightxml.XmlNode;

/**
 *
 * @author hln
 */
public class DownloadLinks extends AbstractFwPageReader {

    public DownloadLinks(String tuneId) {
        super("Musik/" + tuneId);
    }

    public Map<String, String> get() {
        Map<String, String> result = new HashMap<>();
        getAbcLinksNode().getChildren("a").stream().forEach((linkNode) -> {
            result.put(linkNode.getText(), linkNode.getAttribute("href"));
        });
        return result;
    }

    private XmlNode getAbcLinksNode() {
        for (XmlNode node : getWikiTextNode().getChildren("div")) {
            if (node.hasAttribute("class") && node.getAttribute("class").equals("abcmusic")) {
                for (XmlNode linksNode : node.getChildren("div")) {
                    if (linksNode.hasAttribute("class") && linksNode.getAttribute("class").equals("abclinks")) {
                        return linksNode;
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new DownloadLinks("1956").get();
    }
}
