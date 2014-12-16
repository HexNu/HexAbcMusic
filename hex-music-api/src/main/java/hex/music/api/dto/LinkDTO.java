package hex.music.api.dto;

import java.net.URI;

/**
 * Created 2014-mar-10
 *
 * @author jep
 */
public class LinkDTO {

    public String rel;
    public String uri;

    public LinkDTO() {
    }

    public LinkDTO(String rel, URI uri) {
        this.rel = rel;
        this.uri = uri.toString();
    }

    @Override
    public String toString() {
        return "rel=\"" + rel + "\" uri=\"" + uri + "\"";
    }

}
