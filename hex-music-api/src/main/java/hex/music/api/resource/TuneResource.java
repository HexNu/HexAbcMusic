package hex.music.api.resource;

import hex.music.api.dto.LinkDTOBuilder;
import hex.music.api.dto.out.LimitedTuneListDTO;
import hex.music.api.dto.out.TuneDTO;
import hex.music.core.AbcConstants;
import hex.music.core.domain.Tune;
import hex.music.core.domain.TuneListWrapper;
import hex.music.service.command.tune.CreateTunesFromAbcDocCommand;
import hex.music.service.command.tune.GetAbcDocCommand;
import hex.music.service.command.tune.GetAllTunesCommand;
import hex.music.service.command.tune.GetTuneCommand;
import hex.music.service.command.tune.GetLimitedTuneListCommand;
import hex.music.service.command.tune.SearchInNotesCommand;
import hex.music.service.command.tune.SearchTunesCommand;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
public class TuneResource extends AbstractResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLimitedTuneList(@DefaultValue("10") @QueryParam("limit") String limit,
            @DefaultValue("0") @QueryParam("offset") String offset,
            @QueryParam("q") String q,
            @QueryParam("notes") String notes) {
        TuneListWrapper wrapper;
        LinkDTOBuilder linkDTOBuilder = new LinkDTOBuilder(getBaseUri());
        if (q != null) {
            wrapper = commandExecutor.execute(new SearchTunesCommand(Integer.valueOf(limit),
                    Integer.valueOf(offset), q), getKey());
        } else if (notes != null) {
            wrapper = commandExecutor.execute(new SearchInNotesCommand(Integer.valueOf(limit),
                    Integer.valueOf(offset), notes), getKey());
        } else {
            wrapper = commandExecutor.execute(new GetLimitedTuneListCommand(Integer.valueOf(limit),
                    Integer.valueOf(offset)), getKey());
        }
        LimitedTuneListDTO result = new LimitedTuneListDTO(wrapper, linkDTOBuilder);
        return Response.ok(result).build();
    }

    @GET
    @Path("download")
    public Response downloadAllAbcTune(@QueryParam("tunes") String tuneIds) throws UnsupportedEncodingException {
        if (tuneIds == null || tuneIds.equals("")) {
            List<Tune> tunes = commandExecutor.execute(new GetAllTunesCommand(), getKey());
            String abcString = commandExecutor.execute(new GetAbcDocCommand(tunes), getKey());
            InputStream result = new ByteArrayInputStream(abcString.getBytes(AbcConstants.ABC_ENCODING));
            return Response.ok((Object) result).type(MediaType.TEXT_PLAIN)
                    .header("Content-Disposition", "attachment; filename=\"Alla låtar.abc\"")
                    .build();
        } else {
            List<Tune> tunes = new ArrayList<>();
            for (String id : tuneIds.split(",")) {
                tunes.add(commandExecutor.execute(new GetTuneCommand(Long.valueOf(id)), getKey()));
            }
            String abcString = commandExecutor.execute(new GetAbcDocCommand(tunes), getKey());
            InputStream result = new ByteArrayInputStream(abcString.getBytes(AbcConstants.ABC_ENCODING));
            return Response.ok((Object) result).type(MediaType.TEXT_PLAIN)
                    .header("Content-Disposition", "attachment; filename=\"Valda låtar.abc\"")
                    .build();
        }
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

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTune(String jsonData, @PathParam("id") String id) {
        System.out.println(jsonData);
        return Response.ok().build();
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upploadAbcDoc(@FormDataParam("file") InputStream inputStream) {
        List<Tune> newTunes = commandExecutor.executeInTransaction(new CreateTunesFromAbcDocCommand(inputStream), getKey());
        return Response.ok("Number of tunes created: " + newTunes.size()).build();
    }
}
