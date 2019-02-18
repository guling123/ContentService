/**
 *   
 *
 * @Title: ContentCreateResultVO.java 
 * @Package cn.people.controller.vo 
 * @Description: 添加内容结果
 * @author shidandan
 * @date 2018年12月4日 下午4:06:14 
 * @version V1.0   
 */
package cn.people.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *  
 *
 * @author shidandan
 * @ClassName: ContentCreateResultVO 
 * @Description: 添加内容结果 
 * @date 2018年12月4日 下午4:06:14 
 *   
 */
@ApiModel(value = "删除内容结果", description = "删除内容结果")
public class ContentDelResultVO implements Serializable {

    private static final long serialVersionUID = 446433378851789074L;

    @ApiModelProperty(value = "是否删除成功")
    private boolean isSuccess;

    @ApiModelProperty(value = "内容ID")
    private String contentId;

    @ApiModelProperty(value = "备注信息")
    private String msg;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
