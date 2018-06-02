package com.jun.common.utils.dto;

import java.util.Collection;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import com.jun.common.exception.TokenException;
import com.jun.common.model.BaseModel;

/**
 * 
 * @Description 实体类Token相关
 * @author Guojun
 * @Date 2018年4月5日 上午9:47:16
 *
 */
public class DtoDataTokenUtils {
	
	private static final String DTO_DATA_TOKEN_KEY = "dto_data_token_key";
	
	/**
	 * 私有化构造方法
	 */
	private DtoDataTokenUtils(){
		
	}
	
	/**
	 * 从session中获取实体类的token，有可能 是null
	 * @param session 用户登录的session，有可能是null
	 * @return 
	 * 		
	 */
	public static String getDtoDataToken (HttpSession session) {
		if (session == null) {
			return null;
		}
		return (String) session.getAttribute(DTO_DATA_TOKEN_KEY);
	}
	
	/**
	 * 通过UUID生成实体类的token，并返回该值
	 * @param session
	 * @return
	 */
	public static String buildDtoDataToken(HttpSession session) {
		String dataToken = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
		session.setAttribute(DTO_DATA_TOKEN_KEY, dataToken);
		return dataToken;
	}
	
	/**
	 * 根据token与实体类升token的值
	 * @param token
	 * @param dto
	 * @return
	 */
	public static String generatorToken(String token, BaseModel dto){
		if (StringUtils.isBlank(token)) {
			throw new TokenException(dto, TokenException.TOKEN_NOT_EXIST);
		}
		
		StringBuilder tokenSb = new StringBuilder(token);
		tokenSb.append("&").append(dto.getClass().getName());
		try {
			DtoMagamer dtoMagamer = DtoMagamer.getInstance();
			tokenSb.append("&").append(PropertyUtils.getProperty(dto, dtoMagamer.getDtoIdField(dto.getClass())));
		} catch (Exception e) {
			throw new TokenException(dto, TokenException.TOKEN_BUILD_ERROR, e);
		}
		
		return DigestUtils.md5DigestAsHex(tokenSb.toString().getBytes());
	}
	
	/**
	 * 给实体类赋值token
	 * @param token
	 * @param dto
	 */
	public static void setDtoToken(String token, BaseModel dto) {
		if (dto == null) {
			return;
		}
		
		String generatorToken = DtoDataTokenUtils.generatorToken(token, dto);
		dto.setToken(generatorToken);
	}
	
	/**
	 * 给实体类赋值token
	 * @param token
	 * @param dtos
	 */
	public static void setDtoToken(String token, Collection<? extends BaseModel> dtos){
		if (dtos == null || dtos.size() <= 0) {
			return;
		}
		dtos.forEach(dto -> {
			DtoDataTokenUtils.setDtoToken(token, dto);
		});
	}
	
	/**
	 * 校验token是否匹配
	 * @param token
	 * @param dto
	 */
	public static void checkDtoToken(String token, BaseModel dto){
		if (StringUtils.isBlank(token)) {
			throw new TokenException(dto, TokenException.TOKEN_NOT_EXIST);
		}
		
		String generatorToken = DtoDataTokenUtils.generatorToken(token, dto);
		if (!generatorToken.equals(dto.getToken())) {
			throw new TokenException(dto);
		}
	}
	
}
