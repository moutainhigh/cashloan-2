package com.xiji.cashloan.cl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xiji.cashloan.cl.domain.OperatorReqLog;
import com.xiji.cashloan.cl.mapper.OperatorReqLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tool.util.NumberUtil;

import com.xiji.cashloan.cl.service.OperatorReqLogService;
import com.xiji.cashloan.core.common.context.Global;
import com.xiji.cashloan.core.common.mapper.BaseMapper;
import com.xiji.cashloan.core.common.service.impl.BaseServiceImpl;
import com.xiji.cashloan.core.common.util.StringUtil;
 


/**
 * 运营商认证中间表ServiceImpl
 *
 * @author wnb
 * @date 2018/11/27
 * @version 1.0.0
 *
 *
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("operatorReqLogService")
public class OperatorReqLogServiceImpl extends BaseServiceImpl<OperatorReqLog, Long> implements OperatorReqLogService {
   
	public static final Logger logger = LoggerFactory.getLogger(OperatorReqLogServiceImpl.class);
    @Resource
    private OperatorReqLogMapper operatorReqLogMapper;

	@Override
	public BaseMapper<OperatorReqLog, Long> getMapper() {
		return operatorReqLogMapper;
	}

	@Override
	public OperatorReqLog findSelective(Map<String, Object> paramMap) {
		return operatorReqLogMapper.findSelective(paramMap);
	}

	@Override
	public boolean checkUserOperator(long userId) {
		boolean result=true; 
		String daysMostTimes = Global.getValue("operatorCredit_day_most_times");
		if (StringUtil.isNotBlank(daysMostTimes)) {
			int times = NumberUtil.getInt(daysMostTimes);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("userId", userId);
			List<OperatorReqLog> logs=operatorReqLogMapper.listByUserId(paramMap);
			if(!logs.isEmpty() && logs.size()>=times){
				logger.error("用户"+userId+"今天请求认证次数超过"+times+",请明日再来认证");
				result=false;
			}
		}

		return result;
	}

	@Override
	public OperatorReqLog findLastRecord(Map<String, Object> paramMap) {
		return operatorReqLogMapper.findLastRecord(paramMap);
	}
}