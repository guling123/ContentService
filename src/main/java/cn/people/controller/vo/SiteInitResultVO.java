/**
 *   
 *
 * @Title: SiteInitResultVO.java 
 * @Package cn.people.controller.vo 
 * @Description: 站点初始化结果 
 * @author shidandan
 * @date 2018年12月4日 下午3:49:37 
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
 * @ClassName: SiteInitResultVO 
 * @Description: 站点初始化结果 
 * @date 2018年12月4日 下午3:49:37 
 *   
 */
@ApiModel(value = "站点初始化结果", description = "站点初始化结果")
public class SiteInitResultVO implements Serializable {

    private static final long serialVersionUID = 3544213702771106398L;

    @ApiModelProperty(value = "是否创建成功")
    private boolean isSuccess;

    @ApiModelProperty(value = "站点ID")
    private String siteId;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
