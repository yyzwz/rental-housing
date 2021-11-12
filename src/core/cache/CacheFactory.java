package core.cache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;

/**
 * @author 郑为中
 */
public class CacheFactory {

	public static CacheManager cacheManager;

	static {
		try {
			cacheManager = CacheManager.create(CacheFactory.class.getResource("/ehcache.xml"));
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	private CacheFactory() {
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

}
