package com.jun.common.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jun.common.annotation.service.NeedUpdate;
import com.jun.common.annotation.service.UpdateType;
import com.jun.common.constants.UpdateTypeEnum;
import com.jun.common.model.BaseModel;

/**
 * https://blog.csdn.net/qq_19558705/article/details/50134755
 * @Order(n) : 切面的优先级，n越小，级别越高 
 * @Description 自定义AOP，在service更新时加入更新者或者创建者
 * @author Guojun
 * @Date 2018年4月5日 下午7:08:03
 *
 */
@Order(1)
@Aspect
@Component
public class UpdateAsept {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateAsept.class);

	/**
	 * http://loveshisong.cn/%E7%BC%96%E7%A8%8B%E6%8A%80%E6%9C%AF/2016-06-01-AOP%E4%B8%AD%E8%8E%B7%E5%8F%96%E6%96%B9%E6%B3%95%E4%B8%8A%E7%9A%84%E6%B3%A8%E8%A7%A3%E4%BF%A1%E6%81%AF.html
	 * 定义一个前置通知，拦截使用UpdateType注解的方法
	 * @param point
	 * @param updateType
	 */
	@Before("@annotation(updateType)")
	public void beforeUpdate(JoinPoint point, UpdateType updateType) throws Exception {
		//打印日志
		this.logInfo(point);
		
		Signature signature = point.getSignature();
		Class<?> clazz = signature.getDeclaringType();
		Object[] args = point.getArgs();
		
		Class<?>[] parameterTypes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (arg instanceof BaseModel) {
				parameterTypes[i] = BaseModel.class;
			} else {
				parameterTypes[i] = arg.getClass();
			}
		}
		
		Method method = clazz.getMethod(point.getSignature().getName(), parameterTypes);
		if (updateType != null) {
			Parameter[] parameters = method.getParameters();
			BaseModel updateParam = null;
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				NeedUpdate needUpdate = parameter.getAnnotation(NeedUpdate.class);
				if (needUpdate != null) {
					updateParam = (BaseModel) args[i];
					break;
				}
			}
			
			if (updateParam == null ) {
				return;
			}
			
			Date now = new Date();
			if (updateType.value().equals(UpdateTypeEnum.TYPE_ADD)) {
				updateParam.setCreater("张小凡");
				updateParam.setCreateTime(now);
				updateParam.setModify("张小凡");
				updateParam.setModifyTime(now);
			} else if (updateType.value().equals(UpdateTypeEnum.TYPE_CHANGE)) {
				updateParam.setModify("张小凡");
				updateParam.setModifyTime(now);
			}
		}
		
	}
	
	/**
	 * 打印日志的方法
	 * @param point
	 */
	private void logInfo(JoinPoint point) {
		Object target = point.getTarget();
		Signature signature = point.getSignature();
		String targetClassName = signature.getDeclaringTypeName();
		String targetMethodName = signature.getName();
		Object[] args = point.getArgs();
		
		logger.info("*******************************************************************");
		logger.info(StringUtils.join("UpdateAsept@Before被织入的目标对象为：", target));
        logger.info(StringUtils.join("UpdateAsept@Before目标方法为：", targetClassName, ".", targetMethodName));
		logger.info(StringUtils.join("UpdateAsept@Before参数为：", JSON.toJSONString(args)));
		logger.info("*******************************************************************");
	}
}
