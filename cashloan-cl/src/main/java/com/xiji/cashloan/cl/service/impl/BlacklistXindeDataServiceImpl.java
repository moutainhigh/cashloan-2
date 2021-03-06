package com.xiji.cashloan.cl.service.impl;

import com.xiji.cashloan.cl.domain.BlacklistXindeData;
import com.xiji.cashloan.cl.mapper.BlacklistXindeDataMapper;
import com.xiji.cashloan.cl.service.BlacklistXindeDataService;
import com.xiji.cashloan.core.common.mapper.BaseMapper;
import com.xiji.cashloan.core.common.service.impl.BaseServiceImpl;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 信德数聚灰名单ServiceImpl
 * 
 * @author king
 * @version 1.0.0
 * @date 2018-12-19 12:47:55
 */
 
@Service("blacklistXindeDataService")
public class BlacklistXindeDataServiceImpl extends BaseServiceImpl<BlacklistXindeData, Long> implements BlacklistXindeDataService {
	
    private static final Logger logger = LoggerFactory.getLogger(BlacklistXindeDataServiceImpl.class);
   
    @Resource
    private BlacklistXindeDataMapper blacklistXindeDataMapper;

	@Override
	public BaseMapper<BlacklistXindeData, Long> getMapper() {
		return blacklistXindeDataMapper;
	}

	@Override
	public BlacklistXindeData findBlackData(long borrowId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("borrowId", borrowId);
		return blacklistXindeDataMapper.findSelective(paramMap);
	}
}