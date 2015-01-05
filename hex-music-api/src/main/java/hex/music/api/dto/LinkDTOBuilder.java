package hex.music.api.dto;

import hex.music.core.domain.Tune;
import hex.music.fw.domain.SearchResult;
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

    public LinkDTO createPreviousFwLink(Integer previous, Integer limit, String query) {
        if (previous == null) {
            return null;
        }
        String url = "tunes/fw/?limit=" + limit + "&offset=" + previous;
        if (query != null) {
            url += "&q=" + query;
        }
        LinkDTO linkDTO = new LinkDTO("previous", createURI(url));
        return linkDTO;
    }

    public LinkDTO createPreviousLink(Integer previous, Integer limit, String query, String notes) {
        if (previous == null) {
            return null;
        }
        String url = "tunes/hex/?limit=" + limit + "&offset=" + previous;
        if (query != null) {
            url += "&q=" + query;
        }
        if (notes != null) {
            url += "&notes=" + notes;
        }
        LinkDTO linkDTO = new LinkDTO("previous", createURI(url));
        return linkDTO;
    }

    public LinkDTO createNextFwLink(Integer next, Integer limit, String query) {
        if (next == null) {
            return null;
        }
        String url = "tunes/fw/?limit=" + limit + "&offset=" + next;
        if (query != null) {
            url += "&q=" + query;
        }
        LinkDTO linkDTO = new LinkDTO("next", createURI(url));
        return linkDTO;
    }

    public LinkDTO createNextLink(Integer next, Integer limit, String query, String notes) {
        if (next == null) {
            return null;
        }
        String url = "tunes/hex/?limit=" + limit + "&offset=" + next;
        if (query != null) {
            url += "&q=" + query;
        }
        if (notes != null) {
            url += "&notes=" + notes;
        }
        LinkDTO linkDTO = new LinkDTO("next", createURI(url));
        return linkDTO;
    }

    public LinkDTO createTuneDownloadLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("download-abc", createURI("tunes/hex/download/" + tune.getId()));
        return linkDTO;
    }

    public LinkDTO createTuneDownloadPdfLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("download-pdf", createURI("tunes/hex/download/" + tune.getId() + "?format=pdf"));
        return linkDTO;
    }

    public LinkDTO createTuneViewAbcLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("view-abc", createURI("tunes/hex/preview/" + tune.getId() + "?view=abc"));
        return linkDTO;
    }
    
    public LinkDTO createTuneViewFirstLineGifLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("view-first-line", createURI("tunes/hex/firstline/" + tune.getId()));
        return linkDTO;
    }

    
    public LinkDTO createTuneViewGifLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("view-gif", createURI("tunes/hex/preview/" + tune.getId() + "?view=gif"));
        return linkDTO;
    }

    public LinkDTO createTuneAudioMidiLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("audio-midi", createURI("tunes/hex/preview/" + tune.getId() + "?view=mid"));
        return linkDTO;
    }

    public LinkDTO createTuneEditLink(Tune tune) {
        LinkDTO linkDTO = new LinkDTO("edit", createURI("tunes/hex/edit/" + tune.getId()));
        return linkDTO;
    }

    public LinkDTO createFwTuneDownloadLink(SearchResult result) {
        LinkDTO linkDTO = new LinkDTO("download-fw", createURI("tunes/fw/download/" + result.getFwId()));
        return linkDTO;
    }

    public LinkDTO createFwTunePageLink(SearchResult result) {
        LinkDTO linkDTO = new LinkDTO("view-fw-page", UriBuilder.fromPath(result.getPageUrl()).build());
        return linkDTO;
    }
}
