package com.jun.common.intercept.mybatis;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jun.common.model.BaseModel;
import com.jun.common.utils.dto.DtoDataTokenUtils;

/**
 * 学习地址：https://blog.csdn.net/top_code/article/details/55657776
 * @Description 给返回的实体类设置token属性
 * @author Guojun
 * @Date 2018年4月5日 下午2:12:12
 * 注解：@Signature method的值对应type类里边的方法，args的值对应的是method的参数
 */
@Intercepts({
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
	@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class})
})
public class DtoTokenBuildInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(DtoTokenBuildInterceptor.class);
	
	/**
	 * 存放dto的token属性，通过DtoTokenInterceptor拦截器赋值
	 */ 
	public static final ThreadLocal<String> LOCAL_TOKEN = new ThreadLocal<>();
	
	/**
	 * 实现拦截的方法
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//执行SQL并返回结果，这里主要是对结果进行处理
		Object result = invocation.proceed();
		
		if (StringUtils.isBlank(LOCAL_TOKEN.get())) {
			return result;
		}
		
		String token = LOCAL_TOKEN.get();
		
		//myBatis XML里的SQL映射对象
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		
		//查询动作
		if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
			//返回结果为list集合
			if (result instanceof List<?>) {
				((List<?>) result).forEach(dto -> {
					if (dto instanceof BaseModel) {
						DtoDataTokenUtils.setDtoToken(token, (BaseModel)dto);
					}
				});
			}
		}
		//新增动作
		else if (mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT)) {
			Object insertData = invocation.getArgs()[1];
			if (insertData instanceof BaseModel) {
				DtoDataTokenUtils.setDtoToken(token, (BaseModel)insertData);
			}
		}
		
		return result;
	}

	/**
	 * 调用拦截的方法
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		logger.info(StringUtils.join("给返回的实体类设置token属性：", JSON.toJSONString(properties)));
	}

}
