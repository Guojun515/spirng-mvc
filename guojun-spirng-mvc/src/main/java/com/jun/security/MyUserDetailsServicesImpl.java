package com.jun.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jun.sys.model.SysRole;
import com.jun.sys.model.SysUser;
import com.jun.sys.service.ISysUserService;

/**
 * 
 * @Description 获取用户的信息
 * @author Guojun
 * @Date 2018年3月12日 下午10:29:26
 *
 */
public class MyUserDetailsServicesImpl implements UserDetailsService {
	
	@Autowired
	private ISysUserService sysUserService;

	/**
	 * 登陆验证时，通过username获取用户的所有权限信息，
	 * 并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用  
	 * @param username
	 * @return 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		SysUser queryParam = new SysUser();
		queryParam.setUserName(username);
		
		List<SysUser> users = sysUserService.queryUserRoles(queryParam);
		if (users != null && users.size() > 0) {
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			//通过用户名获取用户信息，包括对应的角色信息
			SysUser sysUser = users.get(0);
			List<SysRole> sysRoles = sysUser.getSysRoles();
			sysRoles.forEach((sysRole) -> {
				GrantedAuthority authority = new SimpleGrantedAuthority(sysRole.getRoleName());
				authorities.add(authority);
			});
			
			//构造一个user
			User user = new User(username, sysUser.getPassword(), true, true, true, true, authorities);
			
			return user;
		}
		
		return null;
	}
	
}
