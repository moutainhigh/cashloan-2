package com.xiji.cashloan.cl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xiji.cashloan.cl.model.dsdata.TKCreditRequest;
import com.xiji.cashloan.cl.domain.DhbHistoryOrg;
import com.xiji.cashloan.cl.domain.DhbRiskBlacklist;
import com.xiji.cashloan.cl.mapper.DhbBindingMapper;
import com.xiji.cashloan.cl.mapper.DhbReqLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tool.util.StringUtil;

import com.alibaba.fastjson.JSONObject;
import com.xiji.cashloan.cl.domain.DhbBinding;
import com.xiji.cashloan.cl.domain.DhbHistorySearch;
import com.xiji.cashloan.cl.domain.DhbReqLog;
import com.xiji.cashloan.cl.domain.DhbRiskSocialNetwork;
import com.xiji.cashloan.cl.domain.DhbUserBasic;
import com.xiji.cashloan.cl.mapper.DhbHistoryOrgMapper;
import com.xiji.cashloan.cl.mapper.DhbHistorySearchMapper;
import com.xiji.cashloan.cl.mapper.DhbRiskBlacklistMapper;
import com.xiji.cashloan.cl.mapper.DhbRiskSocialNetworkMapper;
import com.xiji.cashloan.cl.mapper.DhbUserBasicMapper;
import com.xiji.cashloan.cl.service.DhbReqLogService;
import com.xiji.cashloan.core.common.mapper.BaseMapper;
import com.xiji.cashloan.core.common.service.impl.BaseServiceImpl;
import com.xiji.cashloan.core.common.util.OrderNoUtil;
import com.xiji.cashloan.core.domain.Borrow;
import com.xiji.cashloan.core.domain.UserBaseInfo;
import com.xiji.cashloan.core.mapper.UserBaseInfoMapper;
import com.xiji.cashloan.rc.domain.TppBusiness;

import credit.Header;


/**
 * 贷后邦贷后邦反欺诈请求记录表ServiceImpl
 *
 * @author wnb
 * @date 2018/11/27
 * @version 1.0.0
 *
 *
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("dhbReqLogService")
public class DhbReqLogServiceImpl extends BaseServiceImpl<DhbReqLog, Long> implements DhbReqLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(DhbReqLogServiceImpl.class);
   
    @Resource
    private DhbReqLogMapper dhbReqLogMapper;
    
	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	
	@Resource
	private DhbBindingMapper dhbBindingMapper;
	
	@Resource
	private DhbHistoryOrgMapper dhbHistoryOrgMapper;
	
	@Resource
	private DhbHistorySearchMapper dhbHistorySearchMapper;
	
	@Resource
	private DhbRiskBlacklistMapper dhbRiskBlacklistMapper;
	
	@Resource
	private DhbUserBasicMapper dhbUserBasicMapper;
	
	@Resource
	private DhbRiskSocialNetworkMapper dhbRiskSocialNetworkMapper;
	
	@Override
	public BaseMapper<DhbReqLog, Long> getMapper() {
		return dhbReqLogMapper;
	}

	@Override
	public int queryDhbSauron(Borrow borrow, TppBusiness business) {
		int i = 0;
		UserBaseInfo userBaseinfo = userBaseInfoMapper.findByUserId(borrow.getUserId());
		String orderNo = OrderNoUtil.getSerialNumber();
		DhbReqLog log = new DhbReqLog();
		log.setBorrowId(borrow.getId());
		log.setCreateTime(new Date());
		log.setOrderNo(orderNo);
		log.setUserId(borrow.getUserId());
		long timestamp = log.getCreateTime().getTime();
		JSONObject apiParamJson = JSONObject.parseObject(business.getExtend());
		final String apikey = apiParamJson.getString("apiKey");
		final String secretKey = apiParamJson.getString("secretKey");
		final String channelNo = apiParamJson.getString("channelNo");
		final String interfaceName = apiParamJson.getString("interfaceName");
		Header header = new Header(apikey,channelNo,interfaceName,timestamp);
		final String url = business.getUrl();
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("name", userBaseinfo.getRealName());
		param.put("idCard", userBaseinfo.getIdNo());
		param.put("phone", userBaseinfo.getPhone());
		TKCreditRequest tkCreditRequest = new TKCreditRequest(url, param);
		String result = null;
		try {
			result = tkCreditRequest.request();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//logger.info("====响应结果="+result);
		if (StringUtil.isNotBlank(result)) {
			Map<String, Object> resultMap = JSONObject.parseObject(result);
			String code = String.valueOf(resultMap.get("code"));
			log.setRespCode(code);
			log.setRespParams(String.valueOf(resultMap.get("message")));
			log.setRespTime(new Date());
			log.setRespOrderNo(String.valueOf(resultMap.get("orderNo")));
			if(code.equals("200")){
				//保存信息
				i = saveSauronRes(String.valueOf(resultMap.get("res")),borrow.getUserId(),orderNo);
			}
		} else {
			logger.error("用户"+userBaseinfo.getRealName()+"，请求贷后邦，同步响应数据错误，result:"+result);
		}
		
		dhbReqLogMapper.save(log);
		return i;
	}
	/**
	 * 保存贷后邦-反欺诈索伦数据
	 * @param res
	 * @param userId
	 * @param orderNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int saveSauronRes(String res,long userId,String orderNo) {
		int i = 0;
		if(StringUtil.isNotBlank(res)){
			res = change(res);
			List<DhbUserBasic> basicList = JSONObject.parseArray("[" + res+ "]", DhbUserBasic.class);
			if (basicList != null && !basicList.isEmpty()) {
				DhbUserBasic basic = basicList.get(0);
				basic.setOrderNo(orderNo);
				basic.setUserId(userId);
				i = dhbUserBasicMapper.save(basic);
			}
			List<DhbHistoryOrg> orgList = JSONObject.parseArray("[" + res+ "]", DhbHistoryOrg.class);
			if (orgList != null && !orgList.isEmpty()) {
				DhbHistoryOrg org = orgList.get(0);
				org.setOrderNo(orderNo);
				org.setUserId(userId);
				i = dhbHistoryOrgMapper.save(org);
			}
			List<DhbHistorySearch> searchList = JSONObject.parseArray("[" + res+ "]", DhbHistorySearch.class);
			if (searchList != null && !searchList.isEmpty()) {
				DhbHistorySearch search = searchList.get(0);
				search.setOrderNo(orderNo);
				search.setUserId(userId);
				i = dhbHistorySearchMapper.save(search);
			}
			List<DhbRiskBlacklist> blackList = JSONObject.parseArray("[" + res+ "]", DhbRiskBlacklist.class);
			if (blackList != null && !blackList.isEmpty()) {
				DhbRiskBlacklist black = blackList.get(0);
				black.setOrderNo(orderNo);
				black.setUserId(userId);
				i = dhbRiskBlacklistMapper.save(black);
			}
			List<DhbRiskSocialNetwork> riskList = JSONObject.parseArray("[" + res+ "]", DhbRiskSocialNetwork.class);
			if (riskList != null && !riskList.isEmpty()) {
				DhbRiskSocialNetwork risk = riskList.get(0);
				risk.setOrderNo(orderNo);
				risk.setUserId(userId);
				i = dhbRiskSocialNetworkMapper.save(risk);
			}
			
			DhbBinding bind = new DhbBinding();
			Map<String,Object> resultMap = JSONObject.parseObject(res);
			String bindIds = String.valueOf(resultMap.get("bindingIdcards"));
			if(StringUtil.isNotBlank(bindIds)){
				bind.setBindingIdcardsDetail(bindIds);
				List idsList = JSONObject.parseArray(bindIds, Object.class);
				if (idsList != null && !idsList.isEmpty()) {
					bind.setBindingIdcardsSize(idsList.size());
				}else{
					bind.setBindingIdcardsSize(0);
				}
			}
			String bindPhones = String.valueOf(resultMap.get("bindingPhones"));
			if(StringUtil.isNotBlank(bindPhones)){
				bind.setBindingPhonesDetail(bindPhones);
				List phonesList = JSONObject.parseArray(bindPhones, Object.class);
				if (phonesList != null && !phonesList.isEmpty()) {
					bind.setBindingPhonesSize(phonesList.size());
				}else{
					bind.setBindingPhonesSize(0);
				}
			}
			bind.setOrderNo(orderNo);
			bind.setUserId(userId);
			i = dhbBindingMapper.save(bind);
			
		}
		
		return i;
	}
	
	//统一将 格式 user_id 转换成 userId 
	public   String change(String name) {
		StringBuffer sb = new StringBuffer();  
        sb.append(name);  
        int count = sb.indexOf("_");  
        while(count!=0){  
            int num = sb.indexOf("_",count);  
            count = num+1;  
            if(num!=-1){  
                char ss = sb.charAt(count); 
                char ia =ss; 
               boolean flag=StringUtil.isAllLowerCase(String.valueOf(ss));
               if(flag){
                 ia = (char) (ss - 32); 
               } 
               sb.replace(count,count+1,ia+"");  
            }  
        }  
        String data = sb.toString().replaceAll("_","");
		return data.toString();
	}
	   
	
	
	
	
}