package hex.music.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 2014-mar-10
 *
 * @author jep
 */
@JsonInclude(Include.NON_NULL)  
public abstract class AbstractDTO {

    private List<LinkDTO> links;
    
    public void addLink(LinkDTO link) {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
    }

    public List<LinkDTO> getLinks() {
        return links;
    }
}
