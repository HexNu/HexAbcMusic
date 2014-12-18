package hex.music.api.resource;

import hex.music.api.dto.LinkDTOBuilder;
import hex.music.api.dto.out.TuneDTO;
import hex.music.api.dto.out.TuneListDTO;
import hex.music.api.dto.out.TuneListItemDTO;
import hex.music.core.AbcConstants;
import hex.music.core.domain.Tune;
import hex.music.service.command.tune.CreateTunesFromAbcDocCommand;
import hex.music.service.command.tune.GetAbcDocCommand;
import hex.music.service.command.tune.GetAllTunesCommand;
import hex.music.service.command.tune.GetTuneCommand;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author hln
 */
@Path("tunes/abc")
public class AbcResource extends AbstractResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTuneList() {
        TuneListDTO result = new TuneListDTO();
        LinkDTOBuilder linkDTOBuilder = new LinkDTOBuilder(getBaseUri());
        List<Tune> tunes = commandExecutor.execute(new GetAllTunesCommand(), getKey());
        tunes.stream().forEach((tune) -> {
            result.addTuneListItem(new TuneListItemDTO(tune, linkDTOBuilder));
        });
        return Response.ok(result).build();
    }
    
    @GET
    @Path("download")
    public Response downloadAllAbcTune(@PathParam("id") String id) throws UnsupportedEncodingException {
        List<Tune> tunes = commandExecutor.execute(new GetAllTunesCommand(), getKey());
        String abcString = commandExecutor.execute(new GetAbcDocCommand(tunes), getKey());
        InputStream result = new ByteArrayInputStream(abcString.getBytes(AbcConstants.ABC_ENCODING));
        return Response.ok((Object) result).type(MediaType.TEXT_PLAIN)
                .header("Content-Disposition", "attachment; filename=\"Alla låtar.abc\"")
                .build();
    }

    @GET
    @Path("download/{id}")
    public Response downloadAbcTune(@PathParam("id") String id) throws UnsupportedEncodingException {
        Tune tune = commandExecutor.execute(new GetTuneCommand(Long.valueOf(id)), getKey());
        String abcString = commandExecutor.execute(new GetAbcDocCommand(tune), getKey());
        InputStream result = new ByteArrayInputStream(abcString.getBytes(AbcConstants.ABC_ENCODING));
        return Response.ok((Object) result).type(MediaType.TEXT_PLAIN)
                .header("Content-Disposition", "attachment; filename=\"" + tune.getTitle() + ".abc\"")
                .build();
    }

    @GET
    @Path("edit/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTune(@PathParam("id") String id) {
        Tune tune = commandExecutor.execute(new GetTuneCommand(Long.valueOf(id)), getKey());
        TuneDTO result = new TuneDTO(tune);
        return Response.ok(result).build();
    }

    @GET
    @Path("preview/{id}")
    public Response previewAbcCode(@PathParam("id") String id, @QueryParam("view") String view) throws UnsupportedEncodingException {
        Tune tune = commandExecutor.execute(new GetTuneCommand(Long.valueOf(id)), getKey());
        String abcString = commandExecutor.execute(new GetAbcDocCommand(tune), getKey());
        if (view == null || view.equalsIgnoreCase("abc")) {
            return Response.ok(abcString)
                    .header("Content-Type", "text/plain; charset=iso-8859-1")
                    .build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upploadAbcDoc(@FormDataParam("file") InputStream inputStream) {
        List<Tune> newTunes = commandExecutor.executeInTransaction(new CreateTunesFromAbcDocCommand(inputStream), getKey());
        return Response.ok("Number of tunes created: " + newTunes.size()).build();
    }
}
