package com.jun.quartz.mapper;

import java.util.List;

import com.jun.quartz.model.JobInfo;

import tk.mybatis.mapper.common.Mapper;

public interface JobInfoMapper extends Mapper<JobInfo> {

	/**
	 * 查询所有的job信息
	 * @param queryParam
	 * @return
	 */
	public List<JobInfo> queryAllJobDetails(JobInfo queryParam);
}
