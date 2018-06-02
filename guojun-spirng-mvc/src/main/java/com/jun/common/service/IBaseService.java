package com.jun.common.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.jun.common.model.BaseModel;

public interface IBaseService<T extends BaseModel> {

	/**
	 * 查询所有数据
	 * @return
	 */
    List<T> selectAll();
    
    /**
     * 分页查询
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> select( T condition, Integer pageNum, Integer pageSize);
    
    /**
     * 根据主键查询单个数据
     * @param record
     * @return
     */
    T selectByPrimaryKey(T record);

    /**
     * 新增，所有属性
     * @param record
     * @return
     */
    T insert(T record);

    /**
     * 新增，不为空的属性
     * @param record
     * @return
     */
    T insertSelective(T record);

    /**
     * 更新，所有属性
     * @param record
     * @return
     */
    T updateByPrimaryKey(T record);
    
    /**
     * 更新，不为空的属性
     * @param record
     * @return
     */
    T updateByPrimaryKeySelective(T record);
    
    /**
     * 批量更新，更具useUpdateSelective方法判断是否使用选择性更新，默认为true<br>
     * 如果要修改，在接口的实现类重写该方法
     * @param list
     * @return
     */
    List<T> batchUpdate(List<T> list);
    
    /**
     * 删除
     * @param record
     * @return
     */
    Integer deleteByPrimaryKey(T record);

    /**
     * 批量删除
     * @param list
     * @return
     */
    Integer batchDelete(List<T> list);
}
