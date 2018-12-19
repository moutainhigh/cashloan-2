package com.xiji.cashloan.cl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiji.cashloan.cl.domain.OperatorVoiceCnt;
import com.xiji.cashloan.cl.domain.UserContacts;
import com.xiji.cashloan.cl.domain.operator.OperatorVoiceCntMeta;
import com.xiji.cashloan.cl.mapper.OperatorVoiceCntMapper;
import com.xiji.cashloan.cl.mapper.UserContactsMapper;
import com.xiji.cashloan.cl.service.OperatorVoiceCntService;
import com.xiji.cashloan.cl.util.MobileUtil;
import com.xiji.cashloan.cl.util.black.CollectionUtil;
import com.xiji.cashloan.core.common.mapper.BaseMapper;
import com.xiji.cashloan.core.common.service.impl.BaseServiceImpl;
import com.xiji.cashloan.core.common.util.ShardTableUtil;
import com.xiji.cashloan.core.common.util.StringUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 通话详情统计ServiceImpl
 * 
 * @author king
 * @version 1.0.0
 * @date 2018-12-17 11:31:50
 */
 
@Service("operatorVoiceCntService")
public class OperatorVoiceCntServiceImpl extends BaseServiceImpl<OperatorVoiceCnt, Long> implements
	OperatorVoiceCntService {
	
    private static final Logger logger = LoggerFactory.getLogger(OperatorVoiceCntServiceImpl.class);
   
    @Resource
    private OperatorVoiceCntMapper operatorVoiceCntMapper;
	@Resource
	private UserContactsMapper userContactsMapper;

	@Override
	public BaseMapper<OperatorVoiceCnt, Long> getMapper() {
		return operatorVoiceCntMapper;
	}

	@Override
	public void paserReportDetail(String res, Long userId, Date createTime, Long reqLogId) {
		if (StringUtil.isNotBlank(res)) {
			JSONObject resJson = JSONObject.parseObject(res);
			String operatorVoices = String.valueOf(resJson.get("call_contact_detail"));
			if (StringUtil.isNotBlank(operatorVoices)) {
				List<OperatorVoiceCntMeta> cntMetas = JSONObject.parseArray(operatorVoices, OperatorVoiceCntMeta.class);
				if (CollectionUtil.isNotEmpty(cntMetas)) {
					// 分表
					String tableName = ShardTableUtil.generateTableNameById("cl_operator_voice_cnt", userId, 30000);
					int countTable = operatorVoiceCntMapper.countTable(tableName);
					if (countTable == 0) {
						operatorVoiceCntMapper.createTable(tableName);
					}

					String tableName1 = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
					Map<String, Object> params = new HashMap<>();
					params.put("userId", userId);
					List<UserContacts> contacts = userContactsMapper.listShardSelective1(tableName1, params);
					HashMap<String, String> contactMap = new HashMap<>();
					for (UserContacts userCon : contacts) {
						if (userCon != null) {
							if (StringUtil.isNotEmpty(userCon.getPhone())) {
								contactMap.put(userCon.getPhone(),StringUtil.isEmpty(userCon.getName())?"":userCon.getName());
							}
						}
					}

					for (OperatorVoiceCntMeta meta : cntMetas) {
						OperatorVoiceCnt voiceCnt = new OperatorVoiceCnt();
						voiceCnt.setCity(meta.getCity());
						voiceCnt.setUserId(userId);
						voiceCnt.setReqLogId(reqLogId);
						voiceCnt.setPeerNum(meta.getPeerNum());
						voiceCnt.setPeerName(MobileUtil.getMobileName(meta.getPeerNum()));
						String contactName = StringUtils.trimToEmpty(contactMap.get(meta.getPeerNum()));
						if (StringUtil.isNotEmpty(contactName)) {
							voiceCnt.setContactPhone(meta.getPeerNum());
							voiceCnt.setContactName(contactName);
						}else {
							voiceCnt.setContactPhone("无匹配");
							voiceCnt.setContactName("无匹配");
						}
						voiceCnt.setCallCntNum(meta.getCallCnt6m()+"/"+meta.getCallTime6m()+"(秒)");
						voiceCnt.setDialCntNum(meta.getDialCnt6m()+"/"+meta.getDialTime6m()+"(秒)");
						voiceCnt.setDialedCntNum(meta.getDialedCnt6m()+"/"+meta.getDialedTime6m()+"(秒)");
						voiceCnt.setCreatetime(createTime);
						operatorVoiceCntMapper.saveShard(tableName, voiceCnt);
					}
				}
			}
		}
	}

	@Override
	public Page<OperatorVoiceCnt> page(long userId, int current, int pageSize) {
		String tableName = ShardTableUtil.generateTableNameById("cl_operator_voice_cnt", userId, 30000);
		OperatorVoiceCnt lastOne = operatorVoiceCntMapper.getLastRecord(tableName,userId);
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		if (lastOne != null) {
			map.put("reqLogId",lastOne.getReqLogId());
		}

		PageHelper.startPage(current, pageSize);
		Page<OperatorVoiceCnt> page = (Page<OperatorVoiceCnt>) operatorVoiceCntMapper
			.listShardSelective(tableName,map);

		return page;
	}

}