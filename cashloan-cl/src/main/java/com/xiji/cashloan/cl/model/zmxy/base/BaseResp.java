package com.xiji.cashloan.cl.model.zmxy.base;

import java.util.Map;

import com.antgroup.zmxy.openplatform.api.ZhimaResponse;

/**
 * 抽象结果返回类
 *
 * @author wnb
 * @date 2018/11/30
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public abstract class BaseResp extends ZhimaResponse{

    private ZhimaResponse response;

    public BaseResp(ZhimaResponse response) {
        this.response = response;
    }

    @Override
    public boolean isSuccess() {
        return response.isSuccess();
    }

    @Override
    public String getErrorCode() {
        return response.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return response.getErrorMessage();
    }

    @Override
    public String getBody() {
        return response.getBody();
    }

    @Override
    public Map<String, String> getParams() {
        return response.getParams();
    }
}
