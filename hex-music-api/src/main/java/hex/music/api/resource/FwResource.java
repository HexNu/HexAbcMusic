package hex.music.api.resource;

import hex.music.api.dto.LinkDTOBuilder;
import hex.music.api.dto.out.FwTuneListDTO;
import hex.music.api.dto.out.FwTuneListItemDTO;
import hex.music.api.dto.out.TuneDTO;
import hex.music.core.domain.Tune;
import hex.music.fw.domain.SearchResult;
import hex.music.service.command.tune.DownloadFromFwCommand;
import hex.music.service.command.tune.GetTuneCommand;
import hex.music.service.command.tune.SearchTunesOnFolkWikiCommand;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hln
 */
@Path("tunes/fw")
public class FwResource extends AbstractResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKeyList(@QueryParam("search") String search) {
        LinkDTOBuilder linkDTOBuilder = new LinkDTOBuilder(getBaseUri());
        List<SearchResult> searchResults = commandExecutor.execute(new SearchTunesOnFolkWikiCommand(search), getKey());
        FwTuneListDTO result = new FwTuneListDTO();
        searchResults.stream().forEach((s) -> {
            result.addFwSearchListItem(new FwTuneListItemDTO(s, linkDTOBuilder));
        });
        return Response.ok(result).build();
    }

    @GET
    @Path("download/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadFromFw(@PathParam("id") String id) {
        List<Tune> resultList = commandExecutor.executeInTransaction(new DownloadFromFwCommand(id), getKey());
        if (resultList.size() < 1) {
            return Response.noContent().build();
        }
        Tune tune = commandExecutor.execute(new GetTuneCommand(resultList.get(0).getId()), getKey());
        TuneDTO tuneDTO = new TuneDTO(tune);
        return Response.ok(tuneDTO).build();
    }

}
