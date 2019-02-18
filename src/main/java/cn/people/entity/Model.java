package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 模板表
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_model")
@ApiModel(value="Model对象", description="模板表")
public class Model extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "逻辑id")
    @TableField("logic_id")
    private Integer logicId;
    
    @ApiModelProperty(value = "站点id")
    @TableField("siteid")
    private String siteId;

    @ApiModelProperty(value = "模板名称")
    @TableField("model_name")
    private String modelName;

    @ApiModelProperty(value = "内容的地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "创建人id")
    @TableField("create_id")
    private String createId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "类型id")
    @TableField("type_id")
    private String typeId;

    @ApiModelProperty(value = "状态 ， 1正常,2已删除 ")
    @TableField("dstatus")
    private Integer dstatus;

    @ApiModelProperty(value = "版本id")
    @TableField("version_id")
    private String versionId;

    public String getSiteId() {
        return siteId;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public Model setSiteId(String siteId) {
        this.siteId = siteId;
        return this;
    }
    public String getModelName() {
        return modelName;
    }

    public Model setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }
    public String getUrl() {
        return url;
    }

    public Model setUrl(String url) {
        this.url = url;
        return this;
    }
    public String getCreateId() {
        return createId;
    }

    public Model setCreateId(String createId) {
        this.createId = createId;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Model setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getTypeId() {
        return typeId;
    }

    public Model setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }
    public Integer getDstatus() {
        return dstatus;
    }

    public Model setDstatus(Integer dstatus) {
        this.dstatus = dstatus;
        return this;
    }
    public String getVersionId() {
        return versionId;
    }

    public Model setVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    @Override
    public String toString() {
        return "Model{" +
        "siteId=" + siteId +
        ", modelName=" + modelName +
        ", url=" + url +
        ", createId=" + createId +
        ", createTime=" + createTime +
        ", typeId=" + typeId +
        ", dstatus=" + dstatus +
        ", versionId=" + versionId +
        "}";
    }
}
