package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 模板碎片属性表,对应原TEMPLATE_TAG_PROP
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_model_split_prop")
@ApiModel(value="ModelSplitProp对象", description="模板碎片属性表,对应原TEMPLATE_TAG_PROP")
public class ModelSplitProp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板碎片表id,对应原TT_ID,tb_model_split表的id")
    @TableField("model_split_id")
    private String modelSplitId;

    @ApiModelProperty(value = "碎片属性表id,原TAG_PROP_ID,对应tb_split_prop.id")
    @TableField("split_prop_id")
    private String splitPropId;
    
    @ApiModelProperty(value = "碎片属性表逻辑ID 6 位数值，100000起,站点ID内不重复  以TAG_逻辑ID_TAG 形式定位")
    @TableField("model_split_logic_id")
    private Integer modelSplitLogicId;

    @TableField("prop_value")
    private String propValue;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "类型id")
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "模板 id")
    @TableField("model_id")
    private String modelId;

    @ApiModelProperty(value = "状态 ， 1正常,2已删除 ")
    @TableField("state")
    private Integer state;

    @TableField("create_id")
    private String createId;

    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    public String getModelSplitId() {
        return modelSplitId;
    }

    public ModelSplitProp setModelSplitId(String modelSplitId) {
        this.modelSplitId = modelSplitId;
        return this;
    }
    public String getSplitPropId() {
        return splitPropId;
    }

    public ModelSplitProp setSplitPropId(String splitPropId) {
        this.splitPropId = splitPropId;
        return this;
    }
        
    public Integer getModelSplitLogicId()
    {
        return modelSplitLogicId;
    }

    public void setModelSplitLogicId(Integer modelSplitLogicId)
    {
        this.modelSplitLogicId = modelSplitLogicId;
    }

    public String getPropValue() {
        return propValue;
    }

    public ModelSplitProp setPropValue(String propValue) {
        this.propValue = propValue;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public ModelSplitProp setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public ModelSplitProp setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }
    public String getModelId() {
        return modelId;
    }

    public ModelSplitProp setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }
    public String getCreateId() {
        return createId;
    }

    public ModelSplitProp setCreateId(String createId) {
        this.createId = createId;
        return this;
    }

    @Override
    public String toString() {
        return "ModelSpiltProp{" +
        "modelSpiltId=" + modelSplitId +
        ", spiltPropId=" + splitPropId +
        ", propValue=" + propValue +
        ", createTime=" + createTime +
        ", modifyTime=" + modifyTime +
        ", modelId=" + modelId +
        ", createId=" + createId +
        "}";
    }
}
