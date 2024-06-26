package com.pg.backend.ctrl.common;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.pg.backend.common.exception.BizException;
import com.pg.backend.common.model.BaseModel;
import com.pg.backend.common.util.ReqKit;
import com.pg.backend.common.util.StringKit;
import com.pg.backend.common.model.ApiCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author paul 2024/4/9
 */
@Slf4j
public abstract class AbstractCtrl {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractCtrl.class);
    private static final String PAGE_INDEX_PARAM_NAME = "pageNumber";
    private static final String PAGE_SIZE_PARAM_NAME = "pageSize";
    private static final int DEFAULT_PAGE_INDEX = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String SORT_FIELD_PARAM_NAME = "sortField";
    private static final String SORT_ORDER_FLAG_PARAM_NAME = "sortOrder";
    @Autowired
    protected  HttpServletRequest request;
    @Autowired
    protected  HttpServletResponse response;
    @Autowired
    protected ReqKit reqKit;

    protected int getPageIndex() {
        Integer pageIndex = getReqParams().getInteger(PAGE_INDEX_PARAM_NAME);
        return pageIndex == null ? DEFAULT_PAGE_INDEX:pageIndex;
    }
    protected int getPageSize(boolean allowQueryAll) {
        Integer pageSize = getReqParams().getInteger(PAGE_SIZE_PARAM_NAME);
        // -1 represent max
        if(allowQueryAll && pageSize != null && pageSize == -1) {
            return Integer.MAX_VALUE;
        }
        if(pageSize == null || pageSize < 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }
    // common
    protected <T> T getV(String k,Class<T> clz) {
        return this.getReqParams().getObject(k,clz);}
    protected JSONObject getReqParams(){
        return reqKit.getReqParamJSON();
    }
    protected <T> T getVRequired(String key, Class<T> cls) {
        T value = this.getV(key, cls);
        if(ObjectUtils.isEmpty(value)) {
            throw new BizException(ApiCodeEnum.PARAMS_ERROR);
        }
        return value;
    }
    protected  <T> T getVDefault(String key, T defaultV, Class<T> cls) {
        T v = this.getV(key, cls);
        return  v == null ? defaultV : v;
    }
    protected <T> T getObject(Class<T> clazz) {
        JSONObject params = this.getReqParams();
        T result = params.toJavaObject(clazz);
        //belong to BaseModel, process apiExtVal
        if(result instanceof BaseModel){
            JSONObject resultTemp = (JSONObject) JSON.toJSON(result);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if(!resultTemp.containsKey(entry.getKey())){
                    ((BaseModel) result).addExt(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }
    // MutablePair<is Ascend， sort field>
    protected MutablePair<Boolean, String> getSortInfo() {

        String sortField = getReqParams().getString(SORT_FIELD_PARAM_NAME);
        String sortOrderFlag = getReqParams().getString(SORT_ORDER_FLAG_PARAM_NAME);
        if(StringKit.isAllEmpty(sortField, sortField)){
            return null;
        }
        return MutablePair.of("ascend".equalsIgnoreCase(sortOrderFlag), CharSequenceUtil.toUnderlineCase(sortField).toLowerCase());
    }


    // String
    protected String getValString(String key) {
        return this.getV(key, String.class);
    }
    protected String getValStringRequired(String key) {
        return getVRequired(key, String.class);
    }

    // Byte
    protected Byte getValByte(String key) {
        return this.getV(key, Byte.class);
    }
    protected Byte getValByteRequired(String key) {
        return getVRequired(key, Byte.class);
    }
    protected Byte getValByteDefault(String key, Byte defaultValue) {
        return getVDefault(key, defaultValue, Byte.class);
    }
    //Integer
    protected Integer getValInteger(String key) {
        return getV(key, Integer.class);
    }
    protected Integer getValIntegerRequired(String key) {
        return getVRequired(key, Integer.class);
    }
    protected Integer getValIntegerDefault(String key, Integer defaultValue) {
        return getVDefault(key, defaultValue, Integer.class);
    }
    //Long
    protected Long getValLong(String key) {
        return getV(key, Long.class);
    }
    protected Long getValLongRequired(String key) {
        return getVRequired(key, Long.class);
    }
    protected Long getValLongDefault(String key, Long defaultValue) {
        return getVDefault(key, defaultValue, Long.class);
    }
    //BigDecimal
    protected BigDecimal getValBigDecimal(String key) {
        return getV(key, BigDecimal.class);
    }
    protected BigDecimal getValBigDecimalRequired(String key) {
        return getVRequired(key, BigDecimal.class);
    }
    protected BigDecimal getValBigDecimalDefault(String key, BigDecimal defaultValue) {
        return getVDefault(key, defaultValue, BigDecimal.class);
    }
    public String getClientIp() {
        return reqKit.getClientIp();
    }
}
