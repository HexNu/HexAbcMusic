package hex.music.api.resource;

import hex.music.api.dto.LinkDTOBuilder;
import hex.music.api.dto.out.LimitedTuneListDTO;
import hex.music.api.dto.out.TuneDTO;
import hex.music.core.HexMediaType;
import hex.music.core.domain.Tune;
import hex.music.core.domain.TuneListWrapper;
import hex.music.service.command.io.CreateTunesFromAbcDocCommand;
import hex.music.service.command.io.GetAbcStreamCommand;
import hex.music.service.command.io.GetZippedMidiFilesStreamCommand;
import hex.music.service.command.tune.GetAllTunesCommand;
import hex.music.service.command.tune.GetTuneCommand;
import hex.music.service.command.tune.GetLimitedTuneListCommand;
import hex.music.service.command.io.GetPdfStreamCommand;
import hex.music.service.command.io.GetPsStreamCommand;
import hex.music.service.command.io.GetZippedGifFIlesStreamCommand;
import hex.music.service.command.io.PreviewAbcDocCommand;
import hex.music.service.command.io.PreviewGifCommand;
import hex.music.service.command.io.PreviewMidiCommand;
import hex.music.service.command.tune.SearchInNotesCommand;
import hex.music.service.command.tune.SearchTunesCommand;
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
@Path("tunes/hex")
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
    public Response downloadTunes(@QueryParam("tunes") String tuneIds, @DefaultValue("abc") @QueryParam("format") String format) throws UnsupportedEncodingException {
        String resultFileName = tuneIds == null || tuneIds.equals("") ? "Alla låtar" : "Låtsamling";
        String resultFileExtension = getFileExtensionFromFormat(format);
        List<Tune> tunes = new ArrayList<>();
        if (tuneIds == null || tuneIds.equals("")) {
            tunes.addAll(commandExecutor.execute(new GetAllTunesCommand(), getKey()));
        } else {
            for (String id : tuneIds.split(",")) {
                tunes.add(commandExecutor.execute(new GetTuneCommand(Long.valueOf(id)), getKey()));
            }
        }
        InputStream result = getTunesAsInputStream(format, tunes);
        if (result != null) {
            return Response.ok((Object) result).type(getContentTypeForSingleTune(format))
                    .header("Content-Disposition", "attachment; filename=\"" + resultFileName + resultFileExtension + "\"")
                    .build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("download/{id}")
    public Response downloadAbcTune(@PathParam("id") String id, @DefaultValue("abc") @QueryParam("format") String format) {
        Tune tune = commandExecutor.execute(new GetTuneCommand(Long.valueOf(id)), getKey());
        InputStream result = getTuneAsInputStream(format, tune);
        String resultFileExtension = getFileExtensionFromFormat(format);
        if (result != null) {
            return Response.ok((Object) result).type(getContentTypeForSingleTune(format))
                    .header("Content-Disposition", "attachment; filename=\"" + tune.getTitle() + resultFileExtension + "\"")
                    .build();
        }
        return Response.noContent().build();
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
        InputStream result;
        switch (view) {
            case "gif":
            case "giff":
                result = commandExecutor.execute(new PreviewGifCommand(tune), getKey());
                return Response.ok(result).type(HexMediaType.IMAGE_GIF).build();
            case "mid":
            case "midi":
                result = commandExecutor.execute(new PreviewMidiCommand(tune), getKey());
                return Response.ok(result).type(HexMediaType.AUDIO_MIDI).build();
            case "abc":
                String abcString = commandExecutor.execute(new PreviewAbcDocCommand(tune), getKey());
                return Response.ok(abcString)
                        .type(HexMediaType.TEXT_ABC)
                        .build();
        }
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTune(String jsonData, @PathParam("id") String id) {
        System.out.println(jsonData);
        return Response.ok().build();
    }

    @GET
    @Path("firstline/{id}")
    @Produces(HexMediaType.IMAGE_GIF)
    public Response getFirstLine(@PathParam("id") String id) {
        Tune tune = commandExecutor.execute(new GetTuneCommand(Long.valueOf(id)), getKey());
        InputStream result = tune.getFirstLine();
        return Response.ok(result).type(HexMediaType.IMAGE_GIF).build();
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upploadAbcDoc(@FormDataParam("file") InputStream inputStream) {
        List<Tune> newTunes = commandExecutor.executeInTransaction(new CreateTunesFromAbcDocCommand(inputStream), getKey());
        return Response.ok("Number of tunes created: " + newTunes.size()).build();
    }

    private String getContentTypeForSingleTune(String format) {
        switch (format) {
            case "abc":
                return HexMediaType.TEXT_ABC;
            case "gif":
            case "giff":
                return HexMediaType.IMAGE_GIF;
            case "mid":
            case "midi":
                return HexMediaType.AUDIO_MIDI;
            case "pdf":
                return HexMediaType.APPLICATION_POSTSCRIPT;
            case "ps":
                return HexMediaType.APPLICATION_PDF;
            default:
            return HexMediaType.APPLICATION_ZIP;
        }
    }
    private InputStream getTunesAsInputStream(String format, List<Tune> tunes) {
        switch (format) {
            case "abc":
                return commandExecutor.execute(new GetAbcStreamCommand(tunes), getKey());
            case "gif":
            case "giff":
                return commandExecutor.execute(new GetZippedGifFIlesStreamCommand(tunes), getKey());
            case "mid":
            case "midi":
                return commandExecutor.execute(new GetZippedMidiFilesStreamCommand(tunes), getKey());
            case "pdf":
                return commandExecutor.execute(new GetPdfStreamCommand(tunes), getKey());
            case "ps":
                return commandExecutor.execute(new GetPsStreamCommand(tunes), getKey());
        }
        return null;
    }

    private String getFileExtensionFromFormat(String format) {
        switch (format) {
            case "abc":
            case "gif":
            case "giff":
            case "pdf":
            case "ps":
                return "." + format;
            default:
                return ".zip";
        }
    }

    private InputStream getTuneAsInputStream(String format, Tune tune) {
        List<Tune> tunes = new ArrayList<>();
        tunes.add(tune);
        return getTunesAsInputStream(format, tunes);
    }
}
