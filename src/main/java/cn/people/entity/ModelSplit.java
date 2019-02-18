package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 模板碎片表,对应原TEMPLATE_TAG
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_model_split")
@ApiModel(value="ModelSplit对象", description="模板碎片表,对应原TEMPLATE_TAG")
public class ModelSplit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "站点ID")
    @TableField("site_id")
    private String siteId;

    @ApiModelProperty(value = "逻辑ID 6 位数值，100000起,站点ID内不重复 （暂时不用） 暂以TAG_id_TAG 形式定位")
    @TableField("logic_id")
    private Integer logicId;

    @ApiModelProperty(value = "模板id")
    @TableField("model_id")
    private String modelId;

    @ApiModelProperty(value = "碎片ID")
    @TableField("split_id")
    private String splitId;

    @ApiModelProperty(value = "碎片名称")
    @TableField("split_name")
    private String splitName;
    
    @ApiModelProperty(value = "碎片类型")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "栏目id")
    @TableField("channel_id")
    private String channelId;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "标记原文,作模板之前原始代码内容与TAG的对应")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "一个模板标记，最多允许手工插入的记录条数 如果超出，则提示用户删除旧数据后再进行插入（缺省30条）")
    @TableField("count_limit")
    private Integer countLimit;

    @ApiModelProperty(value = "引用时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_id")
    private String createId;

    public String getSiteId() {
        return siteId;
    }

    public ModelSplit setSiteId(String siteId) {
        this.siteId = siteId;
        return this;
    }
    public Integer getLogicId() {
        return logicId;
    }

    public ModelSplit setLogicId(Integer logicId) {
        this.logicId = logicId;
        return this;
    }
    public String getModelId() {
        return modelId;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public ModelSplit setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }
    public String getSplitId() {
        return splitId;
    }

    public ModelSplit setSplitId(String splitId) {
        this.splitId = splitId;
        return this;
    }
    public String getSplitName() {
        return splitName;
    }

    public ModelSplit setSplitName(String splitName) {
        this.splitName = splitName;
        return this;
    }
    public String getChannelId() {
        return channelId;
    }

    public ModelSplit setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public ModelSplit setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public String getContent() {
        return content;
    }

    public ModelSplit setContent(String content) {
        this.content = content;
        return this;
    }
    public Integer getCountLimit() {
        return countLimit;
    }

    public ModelSplit setCountLimit(Integer countLimit) {
        this.countLimit = countLimit;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public ModelSplit setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getCreateId() {
        return createId;
    }

    public ModelSplit setCreateId(String createId) {
        this.createId = createId;
        return this;
    }

    @Override
    public String toString() {
        return "ModelSplit{" +
        "siteId=" + siteId +
        ", logicId=" + logicId +
        ", modelId=" + modelId +
        ", splitId=" + splitId +
        ", splitName=" + splitName +
        ", channelId=" + channelId +
        ", sort=" + sort +
        ", content=" + content +
        ", countLimit=" + countLimit +
        ", createTime=" + createTime +
        ", createId=" + createId +
        "}";
    }
}
