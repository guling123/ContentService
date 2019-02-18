/**
 *   
 *
 * @Title: ResultVO.java 
 * @Package cn.people.controller.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author shidandan
 * @date 2018年11月28日 下午2:31:33 
 * @version V1.0   
 */
package cn.people.controller.vo;

import cn.people.commons.constants.CodeConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 *  
 *
 * @author shidandan
 * @ClassName: ResultVO 
 * @Description:请求结果
 * @date 2018年11月28日 下午2:31:33 
 *   
 */
@ApiModel(value = "返回结果")
public class ResultVO<T> {

    @ApiModelProperty("编码，200为正常")
    private String code;

    @ApiModelProperty("描述")
    private String message;

    @ApiModelProperty("时间戳")
    private long timestamp;

    @ApiModelProperty("返回数据")
    private T data;

    public static <T> ResultVO<T> ok(T data) {
        ResultVO<T> rj = new ResultVO<T>();
        rj.code = CodeConstants.RESULT_OK;
        rj.message = "OK";
        rj.timestamp = System.currentTimeMillis();
        rj.data = data;
        return rj;
    }

    public static <T> ResultVO<T> badRequest(String code, String message) {
        ResultVO<T> rj = new ResultVO<T>();
        rj.code = code;
        rj.message = StringUtils.defaultIfEmpty(message, "发生错误!");
        rj.timestamp = System.currentTimeMillis();
        return rj;
    }

    public static <T> ResultVO<T> badRequest(String code, String message, T data) {
        ResultVO<T> rj = new ResultVO<T>();
        rj.code = code;
        rj.message = StringUtils.defaultIfEmpty(message, "发生错误!");
        rj.timestamp = System.currentTimeMillis();
        rj.data = data;
        return rj;
    }

    public Date getTimestampDate() {
        return new Date(this.timestamp);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
