package com.jun.met.model;

import com.jun.common.model.BaseModel;

/**
 * 
 * @Description 发送消息的实体类
 * @author Guojun
 * @Date 2018年5月27日 上午11:39:35
 *
 */
public class MyMet extends BaseModel {
	private static final long serialVersionUID = -5697767678308982005L;

	/**
	 * 昵称
	 */
	private String name;
	
	/**
	 * 签名
	 */
	private String sing;

	/**
	 * 博客
	 */
	private String blogUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSing() {
		return sing;
	}

	public void setSing(String sing) {
		this.sing = sing;
	}

	public String getBlogUrl() {
		return blogUrl;
	}

	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}
}
