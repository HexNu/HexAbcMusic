package hex.music.core.domain;

import java.io.Serializable;

/**
 *
 * @author hln
 */
public interface DomainEntity extends Serializable {

    public static final int KB = 1024,
            MB = KB * KB,
            GB = MB * KB;

    Long getId();

    void setId(Long id);

    String getName();
}
