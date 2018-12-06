package com.xiji.cashloan.cl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import com.xiji.cashloan.cl.domain.UserBlackInfo;
import com.xiji.cashloan.cl.mapper.UserAuthMapper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiji.cashloan.cl.mapper.UserBlackInfoMapper;
import com.xiji.cashloan.cl.service.UserBlackInfoService;
import com.xiji.cashloan.core.common.mapper.BaseMapper;
import com.xiji.cashloan.core.common.service.impl.BaseServiceImpl;
import com.xiji.cashloan.core.common.util.StringUtil;
import com.xiji.cashloan.core.domain.UserBaseInfo;
import com.xiji.cashloan.core.mapper.UserBaseInfoMapper;
import com.xiji.cashloan.core.model.UserBaseInfoModel;


/**
 * 黑名单ServiceImpl
 *
 * @author wnb
 * @date 2018/11/27
 * @version 1.0.0
 *
 *
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("userBlackInfoService")
public class UserBlackInfoServiceImpl extends BaseServiceImpl<UserBlackInfo, Long> implements UserBlackInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserBlackInfoServiceImpl.class);
	
    @Resource
    private UserBlackInfoMapper userBlackInfoMapper;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private UserAuthMapper userAuthMapper;

	@Override
	public BaseMapper<UserBlackInfo, Long> getMapper() {
		return userBlackInfoMapper;
	}

	@Override
	public Page<UserBlackInfo> listInfo(Map<String, Object> params,
			int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<UserBlackInfo> list = userBlackInfoMapper.listSelective(params);
		return (Page<UserBlackInfo>) list;
	}

	@Override
	public List<List<String>> importUserInfo(MultipartFile userInfoFile,String type) throws Exception {
		List<List<String>> blackInfos = new ArrayList<List<String>>();
		List<String> values = new ArrayList<String>();
		values.add("姓名");
		values.add("身份证号码");
		values.add("手机号");
		values.add("处理结果");
		blackInfos.add(values);
		Workbook book = null;
		if (StringUtil.isNotBlank(userInfoFile.getOriginalFilename()) && userInfoFile.getOriginalFilename().endsWith(".xls")) {
			book = new HSSFWorkbook(userInfoFile.getInputStream());
		} else if (StringUtil.isNotBlank(userInfoFile.getOriginalFilename()) && userInfoFile.getOriginalFilename().endsWith(".xlsx")) {
			book = new XSSFWorkbook(userInfoFile.getInputStream());
		}else{
			logger.error("文件格式错误"+userInfoFile.getOriginalFilename());
		}

		Date date = new Date();
		
		if (book != null) {
			Sheet sheet = book.getSheetAt(0);
			int lostRowNum = sheet.getLastRowNum();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			List<Long> userInfoIds = new ArrayList<Long>();
			for (int i = 1; i <= lostRowNum; i++) {
				Row row = sheet.getRow(i);
				getCellValue(row,0);
				String realName = getCellValue(row,0);
				String idNo = getCellValue(row,1);
				String phone = getCellValue(row,2);
				values = new ArrayList<String>();
				values.add(realName);
				values.add(idNo);
				values.add(phone);
				paramMap.put("realName", realName);
				paramMap.put("idNo", idNo);
				paramMap.put("phone", phone);
				UserBlackInfo info = userBlackInfoMapper.findSelective(paramMap);
				if(info==null ){
					if(StringUtil.isNotBlank(realName) && StringUtil.isNumber(idNo) && idNo.length()==18 && StringUtil.isNumber(phone) && phone.length()==11 ){
						info = new UserBlackInfo();
						info.setRealName(realName);
						info.setIdNo(idNo);
						info.setPhone(phone);
						info.setCreateTime(date);
						info.setType(type);
						userBlackInfoMapper.save(info);
						
						//需要更新的用户信息
						paramMap.clear();
						paramMap.put("realName", row.getCell(0).toString());
						paramMap.put("idNo", row.getCell(1).toString());
						paramMap.put("phone", row.getCell(2).toString());
						UserBaseInfo userInfo = userBaseInfoMapper.findSelective(paramMap);
						if(userInfo!=null){
							userInfoIds.add(userInfo.getId());
						}
						values.add("已处理");
					}else{
						values.add("未处理，格式错误");
					}
				}else if(info!=null){
					values.add("未处理，已存在对应信息");
				}
				
				blackInfos.add(values);
			}
			
			if(userInfoIds.size()>0){
				if("10".equals(type)){
					userBaseInfoMapper.updateBlackIdNos(userInfoIds);
				}else if("20".equals(type)){
					userBaseInfoMapper.updateWhiteIdNos(userInfoIds);
				}
			}
			
		}
		
		return blackInfos;
	}

	@Override
	public UserBlackInfo findByIdNo(String idNo) {
		return userBlackInfoMapper.findByIdNo(idNo);
	}

	@Override
	public List<UserBlackInfo> listSelective(Map<String, Object> params) {
		return userBlackInfoMapper.listSelective(params);
	}

	@Override
	public int deleteByID(Long id) {
		//将用户黑名单状态修改为正常
		UserBlackInfo blackInfo = userBlackInfoMapper.findByPrimary(id);
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idNo", blackInfo.getIdNo());
		UserBaseInfo baseInfo = userBaseInfoMapper.findSelective(paramMap);
		if(!ObjectUtils.isEmpty(baseInfo)){
			baseInfo.setState(UserBaseInfoModel.USER_STATE_NOBLACK);
			userBaseInfoMapper.update(baseInfo);
		}
	
			return userBlackInfoMapper.deleteByID(id);
		
		
	}
	
	/**
	 * 黑名单操作
	 * @param idNo
	 * @param userId
	 * @param realName
	 */
	public void validUserBlackInfo(UserBaseInfo baseInfo){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idNo", baseInfo.getIdNo());
		paramMap.put("realName", baseInfo.getRealName());
		paramMap.put("phone", baseInfo.getPhone());
		paramMap.put("type", "20");
		
		//查询白名单用户
		List<UserBlackInfo> info = userBlackInfoMapper.listSelective(paramMap);
		if(info.size()>0){
			baseInfo.setState(UserBaseInfoModel.USER_STATE_WHITE);
			baseInfo.setBlackReason("白名单用户");
			userBaseInfoMapper.update(baseInfo);
		}
		
		//查询黑名单用户
		paramMap.put("type", "10");
		info = userBlackInfoMapper.listSelective(paramMap);
		if(info.size()>0){
			baseInfo.setState(UserBaseInfoModel.USER_STATE_BLACK);
			baseInfo.setBlackReason("黑名单用户");
			userBaseInfoMapper.update(baseInfo);
		}
		
		//出于安全性考虑，请勿改变查询顺序，避免因为数据重复造成某些用户错误进入黑名单，直接放款
	}

	@Override
	public void deleteBlackOrWhiteInfo(Long userId, String state) {
		if(StringUtil.isNotBlank(state) && UserBaseInfoModel.USER_STATE_NOBLACK.equals(state) && userId!=null && userId>0){
			UserBaseInfo baseInfo = userBaseInfoMapper.findByUserId(userId);
			String type = "";
			if(UserBaseInfoModel.USER_STATE_BLACK.equals(baseInfo.getState())){
				type = "10";
			}else if(UserBaseInfoModel.USER_STATE_WHITE.equals(baseInfo.getState())){
				type = "20";
			}
			if(StringUtil.isNotBlank(type)){
				Map<String,Object> params = new HashMap<String, Object>();
				if(StringUtil.isNotBlank(baseInfo.getPhone())){
					params.clear();
					params.put("type",type);
					params.put("phone", baseInfo.getPhone());
					userBlackInfoMapper.deleteByPhone(params);
				}
				
				if(StringUtil.isNotBlank(baseInfo.getIdNo())){
					params.clear();
					params.put("type",type);
					params.put("idNo", baseInfo.getIdNo());
					userBlackInfoMapper.deleteByIdNo(params);
				}
				
			}
		}
		
		
	}
	
	private int CheckRowError(Row row,int i,List<String> values){
		for(int j=0;j<i;j++){
			row.getCell(j).setCellType(row.getCell(j).CELL_TYPE_STRING);
			if(row.getCell(j)==null|| row.getCell(j).equals("")||row.getCell(j).getCellType()== HSSFCell.CELL_TYPE_BLANK){
			   
			}
            
		}
		return 1;
		
	}
	private String getCellValue(Row row,int j){
		String cellValue ="";
		if(row.getCell(j)==null|| row.getCell(j).equals("")||row.getCell(j).getCellType()== HSSFCell.CELL_TYPE_BLANK){
		  cellValue =""; 
		} else {
			row.getCell(j).setCellType(row.getCell(j).CELL_TYPE_STRING);
			cellValue =  row.getCell(j).getStringCellValue();
		}
		return cellValue;
		
		
	}
}