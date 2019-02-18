/**   
* @Title: ContentOperateLogVO.java 
* @Package cn.people.controller.vo 
* @Description: 内容操作日志 
* @author shidandan
* @date 2018年12月26日 下午5:10:01 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ContentOperateLogVO 
* @Description: 内容操作日志 
* @author shidandan
* @date 2018年12月26日 下午5:10:01 
*  
*/
public class ContentOperateLogVO implements Serializable
{
    private static final long serialVersionUID = 8242702368009302004L;
    @ApiModelProperty(value = "内容id")
    private String contentid;

    @ApiModelProperty(value = "操作时间")
    private Date createtime;
    
    @ApiModelProperty(value = "版本ID")
    private String versionId;

    @ApiModelProperty(value = "操作类型")
    private String dtype;
    
    @ApiModelProperty(value = "操作描述")
    private String dtypeDesc;

    @ApiModelProperty(value = "操作人id")
    private String operatorid;
    
    @ApiModelProperty(value = "操作人名称")
    private String operatorName;
    
    @ApiModelProperty(value = "理由")
    private String reason;

    public String getContentid()
    {
        return contentid;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public String getDtypeDesc()
    {
        return dtypeDesc;
    }

    public void setDtypeDesc(String dtypeDesc)
    {
        this.dtypeDesc = dtypeDesc;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getVersionId()
    {
        return versionId;
    }

    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }

    public void setContentid(String contentid)
    {
        this.contentid = contentid;
    }

    public Date getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }

    public String getDtype()
    {
        return dtype;
    }

    public void setDtype(String dtype)
    {
        this.dtype = dtype;
    }

    public String getOperatorid()
    {
        return operatorid;
    }

    public void setOperatorid(String operatorid)
    {
        this.operatorid = operatorid;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}
