package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.api.dto.LinkDTOBuilder;
import hex.music.core.domain.Tune;

/**
 *
 * @author hln
 */
public class TuneListItemDTO extends AbstractDTO {

    private final String id;
    private final String title;
    private final String subheader;
    private final String composer;
    private final String source;
    private final String rythm;
    private final String region;
    private final String history;
    private final String notes;
    private final String transcriber;
    private final String bibliography;
    private final String discography;
    private final String keySignature;

    public TuneListItemDTO(Tune tune, LinkDTOBuilder linkBuilder) {
        addLink(linkBuilder.createTuneDownloadLink(tune));
        addLink(linkBuilder.createTuneDownloadPdfLink(tune));
        addLink(linkBuilder.createTuneViewAbcLink(tune));
        addLink(linkBuilder.createTuneEditLink(tune));
        id = String.valueOf(tune.getId());
        title = tune.getTitle();
        subheader = tune.getSubheader() != null ? tune.getSubheader() : "";
        composer = tune.getComposer() != null ? tune.getComposer() : "";
        source = tune.getSource()!= null ? tune.getSource(): "";
        rythm = tune.getRythm() != null ? tune.getRythm() : "";
        region = tune.getRegion() != null ? tune.getRegion() : "";
        keySignature = tune.getKey().getSignature().getCode();
        history = tune.getHistory();
        notes = tune.getNotes();
        transcriber = tune.getTranscriber();
        bibliography = tune.getBibliography();
        discography = tune.getDiscography();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubheader() {
        return subheader;
    }

    public String getComposer() {
        return composer;
    }

    public String getSource() {
        return source;
    }

    public String getRythm() {
        return rythm;
    }

    public String getRegion() {
        return region;
    }

    public String getKeySignature() {
        return keySignature;
    }

    public String getHistory() {
        return history;
    }

    public String getNotes() {
        return notes;
    }

    public String getTranscriber() {
        return transcriber;
    }

    public String getBibliography() {
        return bibliography;
    }

    public String getDiscography() {
        return discography;
    }
}
