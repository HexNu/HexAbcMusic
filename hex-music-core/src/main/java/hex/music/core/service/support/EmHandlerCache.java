package hex.music.core.service.support;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public class EmHandlerCache {

    private final long period = 1800000L;
    private final Map<String, ValueContainer> delegatedMap = new ConcurrentHashMap<>();
    private final Timer cleaningScheduler = new Timer();

    public EmHandlerCache() {
        cleaningScheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                checkAndCleanCache();
            }
        }, period, period);
    }

    public EmHandler getHandler(EmHandler.Type type, String databaseName) {
        synchronized (databaseName) {
            if (!delegatedMap.containsKey(databaseName)) {
                delegatedMap.put(databaseName, new ValueContainer(new ThreadSafeEmHandler(type, databaseName)));
            }
            return delegatedMap.get(databaseName).get();
        }
    }

    public void removeHandler(String databaseName) {
        if (delegatedMap.containsKey(databaseName)) {
            delegatedMap.get(databaseName).get().close();
            delegatedMap.remove(databaseName);
        }
    }

    private void checkAndCleanCache() {
        delegatedMap.keySet().stream().filter((key) -> (delegatedMap.get(key).isUnusedForHours(4))).forEach((key) -> {
            removeHandler(key);
        });
    }

    private class ValueContainer {

        private final EmHandler handler;
        private long lastFetchTimestamp = getMilliseconds();

        ValueContainer(EmHandler handler) {
            this.handler = handler;
        }

        EmHandler get() {
            lastFetchTimestamp = getMilliseconds();
            return handler;
        }

        boolean isUnusedForHours(int hours) {
            return getMilliseconds() > lastFetchTimestamp + hours * 3600000L;
        }

        private long getMilliseconds() {
            return new Date().getTime();
        }
    }
}
