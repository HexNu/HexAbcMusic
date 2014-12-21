package hex.music.fw.search;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.digitman.lightxml.DocumentToXmlNodeParser;
import se.digitman.lightxml.NodeFactory;
import se.digitman.lightxml.XmlNode;

/**
 *
 * @author hln
 * @param <T>
 */
public abstract class AbstractFwPageReader<T> {

    private static final String BASE_URL = "http://www.folkwiki.se/";
    private final XmlNode wikiRootNode;
    private final String param;

    public AbstractFwPageReader(String path, String param) {
        this.param = param;
        this.wikiRootNode = createWikiRootNode(path);
    }

    public AbstractFwPageReader(String path) {
        this(path, null);
    }

    public abstract T execute();

    private XmlNode createWikiRootNode(String path) {
        try {
            String url = BASE_URL + path;
            if (param != null) {
                url += URLEncoder.encode(param, "UTF-8"); 
            }
            URLConnection connection = new URL(url).openConnection();
            InputStream stream = connection.getInputStream();
            return new DocumentToXmlNodeParser(stream).parse();
        } catch (IOException ex) {
            Logger.getLogger(TitleSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NodeFactory.createNode("no-result");
    }

    protected XmlNode getWikiRootNode() {
        return wikiRootNode;
    }

    protected XmlNode getWikiTextNode() {
        XmlNode wikiBody = getWikiBodyNode();
        if (wikiBody != null) {
            for (XmlNode div : wikiBody.getChildren("div")) {
                if (div.getAttribute("id").equals("wikitext")) {
                    return div;
                }
            }
        }
        return null;
    }

    protected XmlNode getWikiBodyNode() {
        for (XmlNode td : wikiRootNode.getXmlNodeByPath("body/table/tr").getChildren("td")) {
            if (td.getAttribute("id").equals("wikibody")) {
                return td;
            }
        }
        return null;
    }

}
