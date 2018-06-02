package com.jun.common.job;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import com.jun.common.annotation.job.JobDesc;
import com.jun.common.redis.RedisService;

/**
 * 参考org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
 * @Description 自动扫描job信息
 * @author Guojun
 * @Date 2018年5月5日 下午6:13:26
 *
 */
public class JobInitService implements InitializingBean {
	
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
	
	private String packageNames;
	
	@Autowired
	private RedisService<String> redeisService;

	@Override
	public void afterPropertiesSet() throws Exception {
		//如果包路径为空，不做任何处理
		if (StringUtils.isEmpty(packageNames)) {
			return;
		}
		
		//把包名解析成数组
		String[] packageNameArr = StringUtils.tokenizeToStringArray(packageNames,ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		
		//获取Spring资源解析器
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		//创建Spring中用来读取resource为class的工具类
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
		
		for (String basePackage : packageNameArr) {
			//获取包的路径
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + '/' + DEFAULT_RESOURCE_PATTERN;
			//获取包下边的所有资源
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for(Resource resource : resources) {
				if (!resource.isReadable()) {
					continue;
				}
				
				//返回类的元数据
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				if(metadataReader == null) {
					continue;
				}
				
				String clazzName = metadataReader.getClassMetadata().getClassName();
				Class<?> clazz = ClassUtils.forName(clazzName, ClassUtils.getDefaultClassLoader());
				if(clazz.getAnnotation(JobDesc.class) != null) {
					System.out.println("job的类" + clazzName);
					redeisService.set("job:" + clazzName, clazzName);
				}
			}
		}
	}

	public String getPackageNames() {
		return packageNames;
	}

	public void setPackageNames(String packageNames) {
		this.packageNames = packageNames;
	}
}
