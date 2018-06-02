package com.jun.common.utils.dto;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description 实体类相关的工具类
 * @author Guojun
 * @Date 2018年3月31日 下午9:24:22
 *
 */
public class DtoMagamer {
	/**
	 * 需要解析那些注解的字段
	 */
	private static final Object[] CONCERNED_ANNOTATION = new Object[] {Id.class};

	/**
	 * 缓存类与注解相对应的字段
	 */
	private static Map<String, List<String>> DTO_FIELD_INFO = new HashMap<>();

	/**
	 * lock，这里使用读写锁
	 */
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 管理对象，通过单例模式获取
	 */
	private static volatile DtoMagamer dtoMagamer;

	/**
	 * 私有化构造方法
	 */
	private DtoMagamer(){};

	/**
	 * 单例模式获取对象，这里使用双重锁实现
	 * @return
	 */
	public static DtoMagamer getInstance() {
		if (dtoMagamer == null) {
			synchronized (DtoMagamer.class) {
				if (dtoMagamer == null) {
					dtoMagamer = new DtoMagamer();
				}
			}
		}
		return dtoMagamer;
	}

	/**
	 * 解析实体类，把需要的字段缓存起来
	 * @param clazz
	 * @param getKey
	 */
	@SuppressWarnings("unchecked")
	private List<String> parsingDto(Class<?> clazz, String getKey) {
		if(clazz == null) {
			return null;
		}

		try {
			lock.writeLock().lock();
			Field[] fields = clazz.getDeclaredFields();
			for (Object annontationClazzObj : CONCERNED_ANNOTATION) {
				Class<Annotation> annontationClazz = (Class<Annotation>) annontationClazzObj;
				List<String> cacheFields = new ArrayList<>();
				for (Field field : fields) {
					Annotation annontation = field.getAnnotation(annontationClazz);
					if (annontation != null) {
						cacheFields.add(field.getName());
					}
				}
				String key = StringUtils.join(clazz.getName(), ":", annontationClazz.getName());
				DTO_FIELD_INFO.put(key, cacheFields);
			}

			return DTO_FIELD_INFO.get(getKey);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 获取实体类的ID字段
	 * @param clazz
	 * @return
	 */
	public String getDtoIdField(Class<?> clazz) {
		if(clazz == null) {
			return null;
		}

		String key = StringUtils.join(clazz.getName(), ":", Id.class.getName());

		try {
			lock.readLock().lock();
			List<String> result = DTO_FIELD_INFO.get(key);
			if (result != null) {
				return result.get(0);
			}
		} finally {
			lock.readLock().unlock();
		}

		List<String> result = this.parsingDto(clazz, key);
		return result == null ? null : result.get(0);
	}

}
