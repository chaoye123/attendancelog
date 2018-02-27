import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
/**
 * Created by wenchao.huang on 2017/7/17.
 */
public class ArrayBlockQ {

    static Queue<String> strs = new ArrayBlockingQueue<String>(10);


    public static void main(String[] args) {
        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
                                                          .maximumSize(1000)
                                                          .expireAfterWrite(10, TimeUnit.MINUTES)
                                                          .removalListener(null)
                                                          .build(
                                                          new CacheLoader<String, String>() {
                                                              public String load(String key) throws Exception {
                                                                  return (key);
                                                              }
                                                          });
    }
}
