package com.xiji.cashloan.cl.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiji.cashloan.cl.domain.Adver;
import com.xiji.cashloan.cl.mapper.AdverMapper;
import com.xiji.cashloan.cl.service.AdverService;
import com.xiji.cashloan.core.common.mapper.BaseMapper;
import com.xiji.cashloan.core.common.service.impl.BaseServiceImpl;

/**
 * @author wnb
 * @date 2018/11/27
 * @version 1.0.0
 */
@Service("adverService")
public class AdverServiceImpl extends BaseServiceImpl<Adver, Long> implements AdverService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AdverServiceImpl.class);
   
    @Resource
    private AdverMapper adverMapper;

	@Override
	public BaseMapper<Adver, Long> getMapper() {
		return adverMapper;
	}
	
	@Override
	public boolean updateSelective(Map<String, Object> params) {
		int count = adverMapper.updateSelective(params);
		if (count > 0L) {
			return true;
		}
		return false;
	}
	
	@Override
	public Page<Adver> page(int current, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(current, pageSize);
		Page<Adver> page = (Page<Adver>) adverMapper.listSelective(params);
		return page;
	}

	@Override
	public List<Adver> getBanner() {
		return adverMapper.getBanner();
	}
}