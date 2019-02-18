/**   
* @Title: ContentAuditVO.java 
* @Package cn.people.controller.vo 
* @Description: 文章审核 
* @author shidandan
* @date 2018年12月25日 下午8:04:36 
* @version V1.0   
*/
package cn.people.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ContentAuditVO 
* @Description: 文章审核
* @author shidandan
* @date 2018年12月25日 下午8:04:36 
*  
*/
@ApiModel(value = "文章审核", description = "文章审核")
public class ContentAuditVO
{   
    @ApiModelProperty(value = "驳回理由")
    private String  rejectReason;

    @ApiModelProperty(value = "操作人")
    private String operatorid;

    @ApiModelProperty(value = "操作人名称")
    private String operatorname;
    
    public String getRejectReason()
    {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason)
    {
        this.rejectReason = rejectReason;
    }

    public String getOperatorid()
    {
        return operatorid;
    }

    public void setOperatorid(String operatorid)
    {
        this.operatorid = operatorid;
    }

    public String getOperatorname()
    {
        return operatorname;
    }

    public void setOperatorname(String operatorname)
    {
        this.operatorname = operatorname;
    }

     
}