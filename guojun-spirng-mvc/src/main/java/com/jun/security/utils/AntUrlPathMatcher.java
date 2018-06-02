package com.jun.security.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;   

/**
 * 
 * @Description Ant风格的URL判断
 * @author Guojun
 * @Date 2018年3月14日 下午10:34:56
 *
 */
public class AntUrlPathMatcher {
	/**
	 * 匹配所有"/"开头的路径
	 */
	private static String UNIVERSAL_MATCH_PATTERN = "/**";
	
	/**
	 * 匹配所有的路径
	 */
	private static String UNIVERSAL_MATCH_PATTERN_ALL = "**";
	
	/**
	 * Ant风格判断
	 */
	private volatile static PathMatcher pathMatcher;   

	/**
	 * 私有化构造方法
	 */
	private AntUrlPathMatcher(){}
	
	/**
	 * 使用单例构造对象
	 * @return
	 */
	public static PathMatcher getInstance() {
		if (pathMatcher == null) {
			synchronized (PathMatcher.class) {
				if (pathMatcher == null) {
					pathMatcher = new AntPathMatcher();
				}
			}
		}
		return pathMatcher;
		
	}

	/**
	 * 判断url是否属于path,如果是/**或**就返回true
	 * @param path
	 * @param url
	 * @return
	 */
	public static boolean pathMatchesUrl(Object path, String url) {   
		if ((UNIVERSAL_MATCH_PATTERN.equals(path)) || (UNIVERSAL_MATCH_PATTERN_ALL.equals(path))) {  
			return true;       
		}
		return antPathMatchesUrl((String)path, url);   
	}
	
	/**
	 * Ant风格判断，path是否是属于url的
	 * @param path
	 * @param url
	 * @return
	 */
	public static boolean antPathMatchesUrl(String path, String url) {
		PathMatcher pathMatcher = getInstance();
		return pathMatcher.match((String)path, url);  
	}

}
