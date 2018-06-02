package com.jun.security;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jun.common.utils.PatternManager;
import com.jun.security.utils.AntUrlPathMatcher;

/**
 * 
 * @Description 对某些URL不进行 crsf拦截
 * @author Guojun
 * @Date 2018年3月26日 下午10:27:04
 *
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {
	
	/**
	 * 判断请求的方法的正则
	 */
	private static final String ALLOWED_METHOD_REGEX = "^(GET|HEAD|TRACE|OPTIONS)$";
	
	/**
	 * 需要排除不进行CRSF拦截的URL
	 */
	private List<String> excludeURls ;
	
	public List<String> getExcludeURls() {
		return excludeURls;
	}

	public void setExcludeURls(List<String> excludeURls) {
		this.excludeURls = excludeURls;
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		PatternManager patternManager = PatternManager.getInstance();
		Pattern methodPattern = patternManager.buildPattern(ALLOWED_METHOD_REGEX);
		if (methodPattern.matcher(request.getMethod()).matches()) {
			return false;
		}
		
		boolean isFilter = true;
		if (excludeURls != null && excludeURls.size() > 0) {
			String requestUrl = request.getServletPath();
			for (String excludeUrl : excludeURls) {
				if (AntUrlPathMatcher.antPathMatchesUrl(excludeUrl, requestUrl)){
					isFilter = false;
				}
			}
		}
		return isFilter;
	}

}
