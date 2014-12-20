package hex.music.service.command.tune;

import hex.music.core.domain.Key;
import hex.music.service.command.AbstractServiceCommand;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetAvailableKeysCommand extends AbstractServiceCommand<List<Key.Signature>> {

    @Override
    public List<Key.Signature> execute() {
        return Arrays.asList(Key.Signature.values());
    }
}
