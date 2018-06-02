package com.jun.common.cache;

/**
 * 
 * @Description 缓存需要实现的接口
 * @author Guojun
 * @Date 2018年5月6日 下午6:27:08
 *
 */
public interface ICacheService {
	/**
	 * 是否初始化缓存
	 * @return
	 */
	default boolean isStartupInitCache() {
		return true;
	}
	
	/**
	 * 初始化缓存的方法
	 */
	void initCache();
}
