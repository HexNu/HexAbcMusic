package hex.music.service;

import hex.music.service.command.CommandExecutor;

/**
 *
 * @author hln
 */
public class AbstractService {

    protected CommandExecutor executor = new CommandExecutor();

    protected final String fingerprint;

    public AbstractService(String fingerprint) {
        this.fingerprint = fingerprint;
    }

}
