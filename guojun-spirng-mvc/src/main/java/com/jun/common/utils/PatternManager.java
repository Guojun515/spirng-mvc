package com.jun.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

/**
 * @Description 正则表达预编译
 * @author Guojun
 * @Date 2017年11月17日 下午11:57:40
 *
 */
public class PatternManager {
	
	/**
	 * 缓存预编译后的正则表达式，一个表达式实例化一个Pattern对象,这样就起到预编译过程
	 */
	private static Map<String, Pattern> cache = new HashMap<>(10000);
	
	/**
	 * lock，这里使用读写锁
	 */
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**
	 * 管理对象，通过单例模式获取
	 */
	private static volatile PatternManager patternManager;
	
	/**
	 * 私有化构造方法
	 */
	private PatternManager(){}
	
	/**
	 * 单例模式获取对象，这里使用双重锁实现
	 * @return
	 */
	public static PatternManager getInstance() {
		if (patternManager == null) {
			synchronized (PatternManager.class) {
				if (patternManager == null) {
					patternManager = new PatternManager();
				}
			}
		}
		return patternManager;
	}
	
	/**
	 * 构建Pattern对象
	 * @param regex
	 * @return
	 */
	public Pattern buildPattern (String regex) {
		//从缓存中获取
		try {
			lock.readLock().lock();
			Pattern pattern = cache.get(regex);
			
			if (pattern != null) {
				return pattern;
			}
		} finally {
			lock.readLock().unlock();
		}
		
		//缓存没有，从新写入
		return this.cachePattern(regex);
	}
	
	/**
	 * 缓存Pattern对象，写锁没有释放之前读锁只能等待
	 * @param regex
	 * @return
	 */
	private Pattern cachePattern (String regex) {
		try {
			lock.writeLock().lock();
			Pattern pattern =  cache.get(regex);
			if (pattern != null) {
				return pattern;
			}
			
			pattern = Pattern.compile(regex);
			cache.put(regex, pattern);
			
			return pattern;
		} finally {
			lock.writeLock().unlock();
		}
	}
}
