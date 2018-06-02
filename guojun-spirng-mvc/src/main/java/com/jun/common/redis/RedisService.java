package com.jun.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;  


/**
 * 基于spring和redis的redisTemplate工具类 
 * 针对所有的hash 都是以h开头的方法 
 * 针对所有的Set 都是以s开头的方法                    不含通用方法 
 * 针对所有的List 都是以l开头的方法 
 * @Description 
 * @author Guojun
 * @Date 2018年5月6日 下午3:34:21
 *
 */
public class RedisService<T extends Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

	private String keyPre;

	private RedisTemplate<String, T> redisTemplate;  

	public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {  
		this.redisTemplate = redisTemplate;  
	}  
	
	public void setKeyPre(String keyPre) {
		this.keyPre = keyPre;
	}

	/** 
	 * 指定缓存失效时间 
	 * @param key 键 
	 * @param time 时间(秒) 
	 * @return 
	 */  
	public boolean expire(String key,long time){  
		try {
			if (time > 0) {
				redisTemplate.expire(this.getFullKey(key), time, TimeUnit.SECONDS);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("RedisService.expire:",e);
		}

		return false;
	}  

	/** 
	 * 根据key 获取过期时间 
	 * @param key 键 不能为null 
	 * @return 时间(秒) 返回0代表为永久有效 
	 */  
	public long getExpire(String key){  
		return redisTemplate.getExpire(this.getFullKey(key),TimeUnit.SECONDS);  
	}  

	/** 
	 * 判断key是否存在 
	 * @param key 键 
	 * @return true 存在 false不存在 
	 */  
	public boolean hasKey(String key){  
		return redisTemplate.hasKey(this.getFullKey(key)); 
	}  

	//============================default=============================//

	/** 
	 * 删除缓存 
	 * @param key 可以传一个值 或多个 
	 */  
	public void delete(String ... keys){  
		if(keys != null && keys.length > 0){  
			for (String key : keys) {
				redisTemplate.delete(this.getFullKey(key));
			}
		}  
	}  

	/** 
	 * 普通缓存获取 
	 * @param key 键 
	 * @return 值 
	 */  
	public T get(String key){  
		return key == null ? null : redisTemplate.opsForValue().get(this.getFullKey(key));  
	}  

	/** 
	 * 普通缓存放入 
	 * @param key 键 
	 * @param value 值 
	 * @return true成功 false失败 
	 */  
	public boolean set(String key,T value) {  
		try {  
			redisTemplate.opsForValue().set(this.getFullKey(key), value);  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.set:",e);
			return false;  
		}  
	}  

	/** 
	 * 普通缓存放入并设置时间 
	 * @param key 键 
	 * @param value 值 
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期 
	 * @return true成功 false 失败 
	 */  
	public boolean set(String key, T value, long time){  
		try {  
			if(time>0){  
				redisTemplate.opsForValue().set(this.getFullKey(key), value, time, TimeUnit.SECONDS);  
			}else{  
				set(key, value);  
			}  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.:",e);  
			return false;  
		}  
	}  

	/** 
	 * 递增 
	 * @param key 键 
	 * @param by 要增加几(大于0) 
	 * @return 返回-1表示失败
	 */  
	public long incr(String key, long delta){    
		if(delta < 0){  
			return -1L;
		}  

		return redisTemplate.opsForValue().increment(this.getFullKey(key), delta);  
	}  

	/** 
	 * 递减 
	 * @param key 键 
	 * @param by 要减少几(小于0) 
	 * @return 返回-1表示失败
	 */  
	public long decr(String key, long delta){    
		if(delta<0){  
			return -1L;
		}  

		return redisTemplate.opsForValue().increment(this.getFullKey(key), -delta);    
	}    


	//============================hash=============================//

	/** 
	 * HashGet 
	 * @param key 键 不能为null 
	 * @param item 项 不能为null 
	 * @return 值 
	 */  
	public T hmget(String key, String item){  
		HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
		return hashOperations.get(this.getFullKey(key), item);  
	}  

	/** 
	 * 获取hashKey对应的所有键值 
	 * @param key 键 
	 * @return 对应的多个键值 
	 */  
	public Map<String, T> hmget(String key){
		HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
		return hashOperations.entries(this.getFullKey(key));  
	}  

	/** 
	 * HashSet 
	 * @param key 键 
	 * @param map 对应多个键值 
	 * @return true 成功 false 失败 
	 */  
	public boolean hmset(String key, Map<String, T> map){    
		try {  
			redisTemplate.opsForHash().putAll(this.getFullKey(key), map);  
			return true;  
		} catch (Exception e) { 
			LOGGER.error("RedisService.hmset:",e);
			return false;  
		}  
	}  

	/** 
	 * HashSet 并设置时间 
	 * @param key 键 
	 * @param map 对应多个键值 
	 * @param time 时间(秒) 
	 * @return true成功 false失败 
	 */  
	public boolean hmset(String key, Map<String, T> map, long time){    
		try {  
			redisTemplate.opsForHash().putAll(this.getFullKey(key), map);  
			if (time >0 ) {  
				this.expire(key, time);  
			}  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.hmset:",e);
			return false;  
		}  
	}  

	/** 
	 * 向一张hash表中放入数据,如果不存在将创建 
	 * @param key 键 
	 * @param item 项 
	 * @param value 值 
	 * @return true 成功 false失败 
	 */  
	public boolean hset(String key,String item, T value) {  
		try {  
			redisTemplate.opsForHash().put(this.getFullKey(key), item, value);  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.hset:",e);
			return false;  
		}  
	}  

	/** 
	 * 向一张hash表中放入数据,如果不存在将创建 
	 * @param key 键 
	 * @param item 项 
	 * @param value 值 
	 * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间 
	 * @return true 成功 false失败 
	 */  
	public boolean hset(String key,String item,Object value,long time) {  
		try {  
			redisTemplate.opsForHash().put(this.getFullKey(key), item, value);  
			if(time>0){  
				expire(key, time);  
			}  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.hset:",e);
			return false;  
		}  
	}  

	/** 
	 * 删除hash表中的值 
	 * @param key 键 不能为null 
	 * @param item 项 可以使多个 不能为null 
	 */  
	public void hdel(String key, Object... item){    
		redisTemplate.opsForHash().delete(this.getFullKey(key),item);  
	}   

	/** 
	 * 判断hash表中是否有该项的值 
	 * @param key 键 不能为null 
	 * @param item 项 不能为null 
	 * @return true 存在 false不存在 
	 */  
	public boolean hHasKey(String key, String item){  
		return redisTemplate.opsForHash().hasKey(this.getFullKey(key), item);  
	}   

	/** 
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回 
	 * @param key 键 
	 * @param item 项 
	 * @param by 要增加几(大于0) 
	 * @return 
	 */  
	public double hincr(String key, String item,double by){    
		return redisTemplate.opsForHash().increment(this.getFullKey(key), item, by);  
	}  

	/** 
	 * hash递减 
	 * @param key 键 
	 * @param item 项 
	 * @param by 要减少记(小于0) 
	 * @return 
	 */  
	public double hdecr(String key, String item,double by){    
		return redisTemplate.opsForHash().increment(this.getFullKey(key), item, -by);    
	}    

	//============================set=============================//

	/** 
	 * 根据key获取Set中的所有值 
	 * @param key 键 
	 * @return 
	 */  
	public Set<T> sGet(String key){  
		try {  
			return redisTemplate.opsForSet().members(key);  
		} catch (Exception e) {  
			LOGGER.error("RedisService.:",e);  
			return null;  
		}  
	}  

	/** 
	 * 根据value从一个set中查询,是否存在 
	 * @param key 键 
	 * @param value 值 
	 * @return true 存在 false不存在 
	 */  
	public boolean sHasKey(String key,Object value){  
		try {  
			return redisTemplate.opsForSet().isMember(key, value);  
		} catch (Exception e) {  
			LOGGER.error("RedisService.sHasKey:",e);
			return false;  
		}  
	}  

	/** 
	 * 将数据放入set缓存 
	 * @param key 键 
	 * @param values 值 可以是多个 
	 * @return 成功个数 
	 */  
	@SuppressWarnings("unchecked")
	public long sSet(String key, T... values) {  
		try {  
			return redisTemplate.opsForSet().add(this.getFullKey(key), values);  
		} catch (Exception e) {  
			LOGGER.error("RedisService.sSet:",e);
			return 0;  
		}  
	}  

	/** 
	 * 将set数据放入缓存 
	 * @param key 键 
	 * @param time 时间(秒) 
	 * @param values 值 可以是多个 
	 * @return 成功个数 
	 */  
	@SuppressWarnings("unchecked")
	public long sSetAndTime(String key,long time, T... values) {  
		try {  
			Long count = redisTemplate.opsForSet().add(this.getFullKey(key), values);  
			if(time > 0) {
				expire(key, time);  
			}
			return count;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.sSetAndTime:",e);
			return 0;  
		}  
	}  

	/** 
	 * 获取set缓存的长度 
	 * @param key 键 
	 * @return 
	 */  
	public long sGetSetSize(String key){  
		try {  
			return redisTemplate.opsForSet().size(this.getFullKey(key));  
		} catch (Exception e) {  
			LOGGER.error("RedisService.sGetSetSize:",e);  
			return 0;  
		}  
	}  

	/** 
	 * 移除值为value的 
	 * @param key 键 
	 * @param values 值 可以是多个 
	 * @return 移除的个数 
	 */  
	public long setRemove(String key, Object ...values) {  
		try {  
			Long count = redisTemplate.opsForSet().remove(this.getFullKey(key), values);  
			return count;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.setRemove:",e);  
			return 0;  
		}  
	}  
	//===============================list=================================  

	/** 
	 * 获取list缓存的内容 
	 * @param key 键 
	 * @param start 开始 
	 * @param end 结束  0 到 -1代表所有值 
	 * @return 
	 */  
	public List<T> lGet(String key,long start, long end){  
		try {  
			return redisTemplate.opsForList().range(this.getFullKey(key), start, end);  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lGet:",e);  
			return null;  
		}  
	}  

	/** 
	 * 获取list缓存的长度 
	 * @param key 键 
	 * @return 
	 */  
	public long lGetListSize(String key){  
		try {  
			return redisTemplate.opsForList().size(this.getFullKey(key));  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lGetListSize:",e);  
			return 0;  
		}  
	}  

	/** 
	 * 通过索引 获取list中的值 
	 * @param key 键 
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推 
	 * @return 
	 */  
	public T lGetIndex(String key,long index){  
		try {  
			return redisTemplate.opsForList().index(this.getFullKey(key), index);  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lGetIndex:",e);  
			return null;  
		}  
	}  

	/** 
	 * 将list放入缓存 
	 * @param key 键 
	 * @param value 值 
	 * @param time 时间(秒) 
	 * @return 
	 */  
	public boolean lSet(String key, T value) {  
		try {  
			redisTemplate.opsForList().rightPush(this.getFullKey(key), value);  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.:",e);  
			return false;  
		}  
	}  

	/** 
	 * 将list放入缓存 
	 * @param key 键 
	 * @param value 值 
	 * @param time 时间(秒) 
	 * @return 
	 */  
	public boolean lSet(String key, T value, long time) {  
		try {  
			redisTemplate.opsForList().rightPush(this.getFullKey(key), value);  
			if (time > 0) {
				expire(key, time);  
			}
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lSet1:",e);  
			return false;  
		}  
	}  

	/** 
	 * 将list放入缓存 
	 * @param key 键 
	 * @param value 值 
	 * @param time 时间(秒) 
	 * @return 
	 */  
	public boolean lSet(String key, List<T> value) {  
		try {  
			redisTemplate.opsForList().rightPushAll(this.getFullKey(key), value);  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lSet2:",e);  
			return false;  
		}  
	}  

	/** 
	 * 将list放入缓存 
	 * @param key 键 
	 * @param value 值 
	 * @param time 时间(秒) 
	 * @return 
	 */  
	public boolean lSet(String key, List<T> value, long time) {  
		try {  
			redisTemplate.opsForList().rightPushAll(this.getFullKey(key), value);  
			if (time > 0) {
				expire(key, time);  
			}
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lSet:",e);  
			return false;  
		}  
	}  

	/** 
	 * 根据索引修改list中的某条数据 
	 * @param key 键 
	 * @param index 索引 
	 * @param value 值 
	 * @return 
	 */  
	public boolean lUpdateIndex(String key, long index, T value) {  
		try {  
			redisTemplate.opsForList().set(this.getFullKey(key), index, value);  
			return true;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lUpdateIndex:",e);  
			return false;  
		}  
	}   

	/** 
	 * 移除N个值为value  
	 * @param key 键 
	 * @param count 移除多少个 
	 * @param value 值 
	 * @return 移除的个数 
	 */  
	public long lRemove(String key, long count, T value) {  
		try {  
			Long remove = redisTemplate.opsForList().remove(this.getFullKey(key), count, value);  
			return remove;  
		} catch (Exception e) {  
			LOGGER.error("RedisService.lRemove:",e);  
			return 0;  
		}  
	} 

	/**
	 * 自动给可以值加上固定的前缀
	 * @param key
	 * @return
	 */
	private String getFullKey(String key) {
		if (StringUtils.isBlank(keyPre)) {
			keyPre = "";
		}
		
		if (!StringUtils.endsWith(keyPre, ":")) {
			keyPre = StringUtils.join(keyPre, ":");
		}
		
		if (StringUtils.startsWith(key, ":")) {
			StringUtils.join(keyPre,StringUtils.substring(key, 1)); 
		}
		return StringUtils.join(keyPre,key);
	}
	
}
