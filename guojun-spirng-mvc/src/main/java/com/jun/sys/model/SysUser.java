package com.jun.sys.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jun.common.model.BaseModel;

/**
 * 
 * @Description 用户实体类
 * @author Guojun
 * @Date 2018年3月29日 下午11:21:32
 *
 */
@Table(name = "sys_user")
public class SysUser extends BaseModel {
	
	private static final long serialVersionUID = 492269240003365436L;

	/**
     * 用户Id
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 已绑定邮箱
     */
    private String email;

    /**
     * 性别(0:代表男 1:代表女)
     */
    private Boolean sex;

    /**
     * 用户头像源路径
     */
    private String photo;

    /**
     * 角色信息
     */
    @Transient
    private List<SysRole> sysRoles;

    /**
     * 用户Id
     */
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 用户名称
     */
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 登录密码
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 手机号
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 已绑定邮箱
     */
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 性别(0:代表男 1:代表女)
     */
    public Boolean getSex() {
        return this.sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * 用户头像源路径
     */
    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

	public List<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
    
}
