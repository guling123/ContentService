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
@ApiModel(value = "更新内容结果", description = "更新内容结果")
public class ContentUpdateResultVO implements Serializable {

    private static final long serialVersionUID = 446433378851789074L;

    @ApiModelProperty(value = "是否创建成功")
    private boolean isSuccess;

    @ApiModelProperty(value = "内容ID")
    private String contentId;

    @ApiModelProperty(value = "版本ID")
    private String versionId;

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

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }
}
