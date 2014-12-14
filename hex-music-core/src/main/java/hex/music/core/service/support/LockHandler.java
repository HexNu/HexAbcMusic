package hex.music.core.service.support;

import hex.music.core.service.exception.ServiceException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public class LockHandler {

   private final ConcurrentMap<Object, Future<Object>> cache = new ConcurrentHashMap<>();

   public Object getLockObject(final Object lockKey) {

       Callable<Object> callable = () -> new Object();
       FutureTask<Object> ft;
       Future<Object> f = cache.putIfAbsent(lockKey, ft = new FutureTask<>(callable));
       if (f == null) {
           f = ft;
           ft.run();
       }
       try {
           return f.get();
       } catch (InterruptedException | ExecutionException ex) {
           throw new ServiceException(ex.getMessage(), ex);
       }
   }
}