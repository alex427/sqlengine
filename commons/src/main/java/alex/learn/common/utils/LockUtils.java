package alex.learn.common.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;


public class LockUtils {

    public synchronized static boolean contendLock(String key) {
        if (null == KEY_LOCK.get(key)) {
            // 如果不包含这个关键字  那么可以对该关键字进行缓存操作   并占有这个锁
            KEY_LOCK.put(key, "1");
            return true;
        } else {
            return false;
        }
    }

    public synchronized static void releaseLock(String key) {
        KEY_LOCK.remove(key);
    }
    
    public synchronized static boolean addFutureTask(String key, FutureTask task) {
        if (null == TASK_RETURN_LOCK.get(key)) {
            // 如果不包含这个关键字  那么可以对该关键字进行缓存操作   并占有这个锁
        	TASK_RETURN_LOCK.put(key, task);
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized static void releaseFutureTask(String key) {
    	TASK_RETURN_LOCK.remove(key);
    }
    
    // 缓存操作根据dsid加锁
    public static ConcurrentHashMap<String, String> KEY_LOCK = new ConcurrentHashMap<String, String>();
    
    // 缓存futuretask
    public static ConcurrentHashMap<String, FutureTask> TASK_RETURN_LOCK = new ConcurrentHashMap<String, FutureTask>();

}
