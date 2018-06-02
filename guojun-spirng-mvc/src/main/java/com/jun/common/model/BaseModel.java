package com.jun.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 
 * @Description 实体类的基类
 * @author Guojun
 * @Date 2018年3月29日 下午11:19:24
 *
 */
public class BaseModel implements Serializable {

	private static final long serialVersionUID = -3755443524924615358L;
	
	/**
	 * 数据锁
	 */
	private Long dataLock; 

	/**
	 * 创建人
	 */
	@Column(name = "creater")
	private String creater;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 修改人
	 */
	@Column(name = "modify")
	private String modify;

	/**
	 * 修改时间
	 */
	@Column(name = "modify_time")
	private Date modifyTime;

	/**
	 * 预留字段1
	 */
	@Column(name = "reserve_1")
	private String reserve1;

	/**
	 * 预留字段2
	 */
	@Column(name = "reserve_2")
	private String reserve2;

	/**
	 * 预留字段3
	 */
	@Column(name = "reserve_3")
	private String reserve3;
	
	/**
	 * 数据的token
	 */
	@Transient
	private String token;

	
	/**
	 * 数据锁 
	 */
	public Long getDataLock() {
		return dataLock;
	}

	public void setDataLock(Long dataLock) {
		this.dataLock = dataLock;
	}

	/**
	 * 创建人
	 */
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * 创建时间
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 修改人
	 */
	public String getModify() {
		return this.modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	/**
	 * 修改时间
	 */
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 预留字段1
	 */
	public String getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	/**
	 * 预留字段2
	 */
	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	/**
	 * 预留字段3
	 */
	public String getReserve3() {
		return this.reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	/**
	 * 保证数据唯一性的token
	 */
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
