package bms.core.common;

import java.util.Date;
import java.util.Hashtable;

import bms.core.model.CacheItem;

/**
 * @author xu.jian
 * 
 */
public class CacheCore {
	private static Hashtable<String, Object> cacheList;

	static {
		cacheList = new Hashtable<String, Object>();
	}

	public synchronized static void set(String key, Object value) {
		if (key != null && value != null) {
			cacheList.put(key, value);
		}
	}

	public synchronized static void setItem(String key, Object value) {
		if (key != null && value != null) {
			CacheItem ci = new CacheItem(key, value, new Date().getTime());
			cacheList.put(key, ci);
		}
	}

	public static Object get(String key) {
		return cacheList.get(key);
	}

	public static Object getItem(String key) {
		if (isExpired(key))
			return null;
		CacheItem ci = (CacheItem) cacheList.get(key);
		return ci.getValue();
	}

	public synchronized static Object remove(String key) {
		return cacheList.remove(key);
	}

	public static boolean isExpired(String key) {
		if (!contains(key))
			return true;
		CacheItem item = (CacheItem) cacheList.get(key);
		if (item == null)
			return true;
		long cache_Interval = new Date().getTime()
				- (item.getExpiredTime() + 5 * 60 * 1000); // 统一5分钟过期
		if (cache_Interval > 0)
			return true;
		return false;
	}

	public static boolean contains(String key) {
		return cacheList.containsKey(key);
	}
}
