package com.xiji.cashloan.cl.model;

import com.xiji.cashloan.cl.domain.BorrowRepay;

/**
 * 还款计划Model
 * 
 * @author wnb
 * @version 1.0.0
 * @date 2018/11/12
 *
 *
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public class BorrowRepayModel extends BorrowRepay{

	private static final long serialVersionUID = 1L;

	
	/** 还款状态 -已还款 */
	public static final String STATE_REPAY_YES = "10";
	
	/** 还款状态 - 未还款 */
	public static final String STATE_REPAY_NO = "20";

	/** 还款状态 - 展期还款已还款 */
	public static final String STATE_REPAY_DELAY_YES = "30";

	/**
	 * 借款人手机号
	 */
	private String phone;
	/**
	 * 还款时间格式化 (yyyy-MM-dd HH:mm)
	 */
	private String repayTimeStr;
	/**
	 * 实际还款时间
	 */
	private String realRepayTime;
	
	/**
	 * 实际还款金额
	 */
	private String realRepayAmount;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRepayTimeStr() {
		return repayTimeStr;
	}

	public void setRepayTimeStr(String repayTimeStr) {
		this.repayTimeStr = repayTimeStr;
	}

	public String getRealRepayTime() {
		return realRepayTime;
	}

	public void setRealRepayTime(String realRepayTime) {
		this.realRepayTime = realRepayTime;
	}

	public String getRealRepayAmount() {
		return realRepayAmount;
	}

	public void setRealRepayAmount(String realRepayAmount) {
		this.realRepayAmount = realRepayAmount;
	}

	
	


}
