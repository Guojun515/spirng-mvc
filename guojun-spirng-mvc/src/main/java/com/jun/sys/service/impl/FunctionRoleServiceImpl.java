package com.jun.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.sys.service.IFunctionRoleService;

/**
 * 
 * @Description 功能角色对应关系，做权限使用
 * @author Guojun
 * @Date 2018年5月27日 下午10:57:10
 *
 */
@Service
public class FunctionRoleServiceImpl implements IFunctionRoleService {

	@Autowired
	SqlSession sqlSession;

	@Override
	public Map<String, List<String>> queryAllRFunctionRoles() {
		Map<String, List<String>> result = new HashMap<>();

		List<Map<String, String>> queryResult = sqlSession.selectList("com.jun.sys.mapper.FunctionRoleMapper.queryAllRFunctionRoles");
		if (queryResult == null || queryResult.size() <= 0) {
			return result;
		}

		queryResult.forEach(data -> {
			List<String> roles = new ArrayList<>();

			String functionUrl = data.get("function_url");
			String roleName = data.get("role_name");

			if (result.get(functionUrl) != null) {
				roles = result.get(functionUrl);
				roles.add(roleName);
			} else {
				roles.add(roleName);
				result.put(functionUrl, roles);
			}
		});

		return result;
	}

}
