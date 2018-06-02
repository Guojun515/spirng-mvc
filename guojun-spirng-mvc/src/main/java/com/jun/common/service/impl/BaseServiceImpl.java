package com.jun.common.service.impl;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jun.common.annotation.service.NeedUpdate;
import com.jun.common.annotation.service.UpdateType;
import com.jun.common.constants.UpdateTypeEnum;
import com.jun.common.exception.ServiceException;
import com.jun.common.model.BaseModel;
import com.jun.common.service.IBaseService;
import com.jun.common.utils.dto.DtoMagamer;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public abstract class BaseServiceImpl<T extends BaseModel> implements IBaseService<T> {
	/**
	 * 数据锁的字段
	 */
	private static final String DATA_LOCK_FIELD = "dataLock";
	
	/**
	 * spirng 4后可以实现泛型限定式依赖注入
	 */
	@Autowired
	private Mapper<T> mapper;
	
	@Override
	public List<T> selectAll() {
		return this.mapper.selectAll();
	}

	@Override
	public Page<T> select(T condition, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<T> datas = (Page<T>) this.mapper.select(condition);
		return datas;
	}

	@Override
	public T selectByPrimaryKey(T record) {
		return this.mapper.selectByPrimaryKey(record);
	}

	@Override
	@UpdateType(UpdateTypeEnum.TYPE_ADD)
	@Transactional(rollbackFor = Exception.class)
	public T insert(@NeedUpdate T record) {
		int result = this.mapper.insert(record);
		if (result <= 0) {
			throw new ServiceException("新增失败，数据版本号不正确");
		}
		
		return record;
	}

	@Override
	@UpdateType(UpdateTypeEnum.TYPE_ADD)
	@Transactional(rollbackFor = Exception.class)
	public T insertSelective(@NeedUpdate T record) {
		int result = this.mapper.insertSelective(record);
		if (result <= 0) {
			throw new ServiceException("新增失败，数据版本号不正确");
		}
		
		return record;
	}

	@Override
	@UpdateType(UpdateTypeEnum.TYPE_CHANGE)
	@Transactional(rollbackFor = Exception.class)
	public T updateByPrimaryKey(@NeedUpdate T record) {
		Example condition = this.buildUpdatePrimaryKeyCondition(record);
		if (condition == null) {
			throw new ServiceException("数据的主键不能为空");
		}
		
		int result = this.mapper.updateByExample(record, condition);
		
		if (result <= 0) {
			throw new ServiceException("更新失败，数据版本号不正确");
		}
		
		return record;
	}

	@Override
	@UpdateType(UpdateTypeEnum.TYPE_CHANGE)
	@Transactional(rollbackFor = Exception.class)
	public T updateByPrimaryKeySelective(@NeedUpdate T record) {
		Example condition = this.buildUpdatePrimaryKeyCondition(record);
		if (condition == null) {
			throw new ServiceException("数据的主键不能为空");
		}
		
		int result = this.mapper.updateByExampleSelective(record, condition);
		
		if (result <= 0) {
			throw new ServiceException("更新失败，数据版本号不正确");
		}
		
		return record;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> batchUpdate(List<T> list) {
		list.forEach(data -> {
			if (this.useUpdateSelective()) {
				self().updateByPrimaryKeySelective(data);
			} else {
				self().updateByPrimaryKey(data);
			}
		});
		return list;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByPrimaryKey(T record) {
		return this.mapper.deleteByPrimaryKey(record);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer batchDelete(List<T> list) {
		int deleteCount = 0;
		
		for (T t : list) {
			deleteCount += self().deleteByPrimaryKey(t);
		}
		
		return deleteCount;
	}

	protected boolean useUpdateSelective () {
		return true;
	}
	
	/**
	 * 获取代理属类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected IBaseService<T> self() {
		return (IBaseService<T>) AopContext.currentProxy();
	}
	
	/**
	 * 构建更新条件，加上数据锁
	 * @param t
	 * @return
	 * @throws FieldException 
	 */
	private Example buildUpdatePrimaryKeyCondition(T record) {
		try {
			if (record == null) {
				return null;
			}
			
			Example example = new Example(record.getClass());
			Criteria criteria = example.createCriteria();
			
			//获取实体类中注解的@Id字段
			DtoMagamer dtoMagamer = DtoMagamer.getInstance();
			String field = dtoMagamer.getDtoIdField(record.getClass());
			Long id = (Long) PropertyUtils.getProperty(record, field);
			criteria.andEqualTo(field, id);
			
			
			//获取数据中版本号
			Long dataLock = (Long) PropertyUtils.getProperty(record, DATA_LOCK_FIELD);
			//数据中的版本号作为更新条件
			criteria.andEqualTo(DATA_LOCK_FIELD, dataLock);
			//数据的版本号升1个
			PropertyUtils.setProperty(record, DATA_LOCK_FIELD,  ++dataLock);
			
			return example;
		} catch (Exception e) {
			throw new ServiceException("数据更新异常", e);
		}
	}
}
