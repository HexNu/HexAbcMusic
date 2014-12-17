package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.api.dto.LinkDTOBuilder;
import hex.music.core.domain.Tune;

/**
 *
 * @author hln
 */
public class TuneListItemDTO extends AbstractDTO {

    private final String title;
    private final String composer;
    private final String originator;
    private final String rythm;
    private final String region;
    private final String key;

    public TuneListItemDTO(Tune tune, LinkDTOBuilder linkBuilder) {
        addLink(linkBuilder.createTuneDownloadLink(tune));
        addLink(linkBuilder.createTuneViewAbcLink(tune));
        addLink(linkBuilder.createTuneEditLink(tune));
        title = tune.getSubheader() != null ? tune.getTitle() + " - " + tune.getSubheader() : tune.getTitle();
        composer = tune.getComposer() != null ? tune.getComposer() : "";
        originator = tune.getOriginator() != null ? tune.getOriginator() : "";
        rythm = tune.getRythm() != null ? tune.getRythm() : "";
        region = tune.getRegion() != null ? tune.getRegion() : "";
        key = tune.getKey().getSignature().getCode();
    }

    public String getTitle() {
        return title;
    }

    public String getComposer() {
        return composer;
    }

    public String getOriginator() {
        return originator;
    }

    public String getRythm() {
        return rythm;
    }

    public String getRegion() {
        return region;
    }

    public String getKey() {
        return key;
    }
}
