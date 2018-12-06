package com.xiji.cashloan.rc.mapper;

import java.util.List;
import java.util.Map;

import com.xiji.cashloan.core.common.mapper.BaseMapper;
import com.xiji.cashloan.core.common.mapper.RDBatisDao;
import com.xiji.cashloan.rc.domain.Tpp;
import com.xiji.cashloan.rc.model.ManageTppModel;
import com.xiji.cashloan.rc.model.TppModel;

/**
 * 第三方征信信息Dao
 *
 * @author wnb
 * @version 1.0.0
 * @date 2018/11/27
 *
 *
 *
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface TppMapper extends BaseMapper<Tpp,Long> {

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<TppModel> listAll();

	/**
	 * 条件查询List
	 * 
	 * @param paramMap
	 * @return
	 */
	List<ManageTppModel> list(Map<String, Object> paramMap);

	/**
	 * 遍历所有第三方信息
	 * 
	 * @return
	 */
	List<TppModel> listAllWithBusiness();
}
