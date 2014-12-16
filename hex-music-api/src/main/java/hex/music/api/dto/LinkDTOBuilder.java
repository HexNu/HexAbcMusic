package hex.music.api.dto;

import hex.music.core.domain.Tune;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;

/**
 * Created 2014-sep-26
 *
 * @author jep
 */
public class LinkDTOBuilder {

    private final String baseUri;

    public LinkDTOBuilder(String baseUri) {
        this.baseUri = baseUri;
    }

    protected URI createURI(String append) {
        return UriBuilder.fromPath(appendToBaseUri(append)).build();
    }

    protected String appendToBaseUri(String value) {
        StringBuilder sb = new StringBuilder(baseUri);
        sb.append(value);
        return sb.toString();
    }

    public LinkDTO createLink(String rel, String path) {
        return new LinkDTO(rel, createURI(path));
    }

    public LinkDTO createTuneDownloadLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("download", createURI("tunes/abc/" + tune.getId()));
        System.out.println(linkDTO);
        return linkDTO;
    }
}
