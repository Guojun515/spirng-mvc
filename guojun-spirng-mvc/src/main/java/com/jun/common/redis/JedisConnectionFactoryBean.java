package com.jun.common.redis;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @Description jedisConnectionFactory的bean
 * @author Guojun
 * @Date 2018年5月6日 下午12:06:12
 *
 */
public class JedisConnectionFactoryBean implements InitializingBean, FactoryBean<JedisConnectionFactory> {
	
	private String host;
	
	private int port;
	
	private int dbName;
	
	private String password;
	
	private JedisPoolConfig jedisPoolConfig;
	
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(host, port);
		standaloneConfig.setPassword(RedisPassword.of(password));
		standaloneConfig.setDatabase(dbName);
		
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling()
				.poolConfig(jedisPoolConfig).build();
		
		synchronized (this) {
			jedisConnectionFactory = new JedisConnectionFactory(standaloneConfig, jedisClientConfiguration);
			jedisConnectionFactory.afterPropertiesSet();
		}
	}

	@Override
	public JedisConnectionFactory getObject() throws Exception {
		return jedisConnectionFactory;
	}

	@Override
	public Class<?> getObjectType() {
		return JedisConnectionFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getDbName() {
		return dbName;
	}

	public void setDbName(int dbName) {
		this.dbName = dbName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}
}
