package com.xiji.cashloan.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xiji.cashloan.core.common.context.Constant;
import com.xiji.cashloan.core.common.context.ExportConstant;
import com.xiji.cashloan.core.common.util.JsonUtil;
import com.xiji.cashloan.core.common.util.RdPage;
import com.xiji.cashloan.core.common.util.ServletUtils;
import com.xiji.cashloan.core.common.util.excel.JsGridReportBase;
import com.xiji.cashloan.system.domain.SysUser;
import com.xiji.cashloan.system.permission.annotation.RequiresPermission;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.xiji.cashloan.cl.model.TongdunReqLogModel;
import com.xiji.cashloan.cl.service.TongdunReqLogService;


/**
 * 同盾请求记录 Controller
 * @author wnb
 * @version 1.0.0
 * @date 2018/11/27
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class ManageTongdunController extends ManageBaseController{
	
	@Resource
	private TongdunReqLogService tongdunReqLogService;
 
	/**
	 * 同盾机审请求记录列表
	 * @param searchParams
	 * @param current
	 * @param pageSize
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/manage/borrow/tongdun/list.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:tongdun:list",name = "同盾机审请求记录列表")
	public void list(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<TongdunReqLogModel> page = tongdunReqLogService.pageListModel(params, current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	
	/**
	 * 导出 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/modules/manage/borrow/tongdun/export.htm")
	public void export(
			@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		if(user!=null){
			List list = tongdunReqLogService.listByMap(params);
			response.setContentType("application/msexcel;charset=UTF-8");
			String title = "同盾机审请求记录";
			String[] hearders =  ExportConstant.EXPORT_TONGDUNLOG_LIST_HEARDERS;
			String[] fields = ExportConstant.EXPORT_TONGDUNLOG_LIST_FIELDS;
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(list,title,hearders,fields,user.getName());
		}else{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "登录超时,请重新登录");
			ServletUtils.writeToResponse(response,result);
		}
	}
	
	/**
	 * 同盾机审详细内容
	 * @param id
	 */
	@RequestMapping(value="/modules/manage/borrow/tongdun/detail.htm")
	@RequiresPermission(code = "modules:manage:borrow:tongdun:detail",name = "同盾机审详细内容")
	public void detail(@RequestParam(value = "id") long id){
		TongdunReqLogModel model = tongdunReqLogService.getModelById(id);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, model);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	
}
