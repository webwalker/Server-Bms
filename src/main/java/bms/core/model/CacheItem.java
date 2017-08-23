package bms.core.model;

/**
 * @author xujian
 * 
 */
public class CacheItem {

	private String key;
	private Object value;
	private Object ext;
	private long expiredTime;

	public CacheItem(String key) {
		this.key = key;
	}

	public CacheItem(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public CacheItem(String key, Object value, long time) {
		this.key = key;
		this.value = value;
		this.expiredTime = time;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getExt() {
		return ext;
	}

	public void setExt(Object ext) {
		this.ext = ext;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

}
