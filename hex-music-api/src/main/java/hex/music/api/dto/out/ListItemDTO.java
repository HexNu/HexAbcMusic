package hex.music.api.dto.out;

/**
 *
 * @author hln
 */
public class ListItemDTO {

    private final String name;

    public ListItemDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
