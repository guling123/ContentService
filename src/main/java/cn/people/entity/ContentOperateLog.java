package cn.people.entity;

import cn.people.entity.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 内容操作记录表
 * </p>
 *
 * @author gaoyongjiu
 * @since 2018-12-04
 */
@ApiModel(value="ContentOperateLog对象", description="内容操作记录表")
@TableName(value="tb_content_operate_log")
public class ContentOperateLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容id")
    @TableField("content_id")
    private String contentId;

    @ApiModelProperty(value = "操作时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "操作类型，1创建，2保存，3保存并提审，4提审，5二审通过，6二审不通过，7三审通过，8三审不通过，9上线，10下线，11置顶")
    @TableField("dtype")
    private Integer dtype;
    
    @ApiModelProperty(value = "版本ID")
    @TableField("version_id")
    private String versionId;
    
    @ApiModelProperty(value = "操作人id")
    @TableField("operator_id")
    private String operatorId;
    
    @ApiModelProperty(value = "操作人名称")
    @TableField("operator_name")
    private String operatorName;
    
    @ApiModelProperty(value = "理由")
    @TableField("reason")
    private String reason;

    public String getOperatorName()
    {
        return operatorName;
    }

    public String getVersionId()
    {
        return versionId;
    }

    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public Integer getDtype() {
        return dtype;
    }

    public ContentOperateLog setDtype(Integer dtype) {
        this.dtype = dtype;
        return this;
    }
   

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "TbContentOperateLog{" +
        "contentid=" + contentId +
        ", createtime=" + createTime +
        ", dtype=" + dtype +
        ", operatorid=" + operatorId +
        "}";
    }
}
