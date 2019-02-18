package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片内容表,对应原表TITLE_PROP_BOX
 * </p>
 *
 * @author shidandan
 * @since 2019-01-08
 */
@TableName("tb_split_content")
@ApiModel(value="SplitContent对象", description="碎片内容表,对应原表TITLE_PROP_BOX")
public class SplitContent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板碎片id,原TT_ID,对应表tb_model_spilt.id")
    @TableField("model_split_id")
    private String modelSplitId;
    
    @ApiModelProperty(value = "模板碎片logic_id,原TT_ID,对应表tb_model_spilt.logic＿id")
    @TableField("model_split_logic_id")
    private Integer modelSplitLogicId;
    
    @ApiModelProperty(value = "模板id,原TEMPLATE_ID，对应表tb_model.id")
    @TableField("model_id")
    private String modelId;

    @ApiModelProperty(value = "所属栏目id,原NODE_ID,对应表tb_channel.id")
    @TableField("channel_id")
    private String channelId;
    
    @ApiModelProperty(value = "所属栏目logic_id,原NODE_ID,对应表tb_channel.logic＿id")
    @TableField("channel_logic_id")
    private Integer channelLogicId;

    @ApiModelProperty(value = "显示标题")
    @TableField("show_title")
    private String showTitle;

    @ApiModelProperty(value = "内容类型,1文章2.文字3图片")
    @TableField("dtype")
    private Integer dtype;

    @ApiModelProperty(value = "被选择新闻的节点(注：应该代表的是插入栏目时的栏目ID)")
    @TableField("content_channel_id")
    private String contentChannelId;
    
    @ApiModelProperty(value = "被选择新闻的节点(注：应该代表的是插入栏目时的栏目logic＿id) CNODE_ID")
    @TableField("content_channel_logic_id")
    private Integer contentChannelLogicId;

    @ApiModelProperty(value = "内容id")
    @TableField("contentid")
    private String contentid;
    
    @ApiModelProperty(value = "内容逻辑id")
    @TableField("content_logic_id")
    private Integer contentLogicId;

    @ApiModelProperty(value = "手选内容为图片时，表示图片的链接")
    @TableField("link_url")
    private String linkUrl;

    @ApiModelProperty(value = "排序")
    @TableField("subindex")
    private Integer subindex;
    
    @ApiModelProperty(value = "添加人id")
    @TableField("createrid")
    private String createrid;
    
    @ApiModelProperty(value = "dtype=1时是文章的说明 =2文字说明 =3图片的说明")
    @TableField("text_box")
    private String textBox;

    @ApiModelProperty(value = "图片的存放位置")
    @TableField("text_url")
    private String textUrl;

    @ApiModelProperty(value = "置顶，0未置顶，1置顶")
    @TableField("tops")
    private Integer tops;

    @ApiModelProperty(value = "碎片版本id")
    @TableField("version_id")
    private String versionId;

    @ApiModelProperty(value = "图片的浮动类型 0-不浮动 1-左浮动 2-右浮动")
    @TableField("img_float_type")
    private Integer imgFloatType;

    @ApiModelProperty(value = "添加时间")
    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @ApiModelProperty(value = "激活标志 ，0默认")
    @TableField("publish_enable")
    private Integer publishEnable;

    /**
     *内容类型 - 文章
     */
    public static final Integer DTYPE_CONTENT=1;
    /**
     * 内容类型 - 文字
     */
    public static final Integer DTYPE_WORD=2;
    /**
     * 内容类型 - 图片
     */
    public static final Integer DTYPE_PHOTO=3;
    
    public String getModelSplitId() {
        return modelSplitId;
    }

    public SplitContent setModelSplitId(String modelSplitId) {
        this.modelSplitId = modelSplitId;
        return this;
    }
    public String getModelId() {
        return modelId;
    }

    public SplitContent setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }
    public String getChannelId() {
        return channelId;
    }

    public SplitContent setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }
    public String getShowTitle() {
        return showTitle;
    }

    public SplitContent setShowTitle(String showTitle) {
        this.showTitle = showTitle;
        return this;
    }
    public Integer getDtype() {
        return dtype;
    }

    public SplitContent setDtype(Integer dtype) {
        this.dtype = dtype;
        return this;
    }
    public String getContentChannelId() {
        return contentChannelId;
    }

    public SplitContent setContentChannelId(String contentChannelId) {
        this.contentChannelId = contentChannelId;
        return this;
    }
    public String getContentid() {
        return contentid;
    }

    public SplitContent setContentid(String contentid) {
        this.contentid = contentid;
        return this;
    }
    public String getLinkUrl() {
        return linkUrl;
    }

    public SplitContent setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
        return this;
    }
    public Integer getSubindex() {
        return subindex;
    }

    public SplitContent setSubindex(Integer subindex) {
        this.subindex = subindex;
        return this;
    }
    public String getCreaterid() {
        return createrid;
    }

    public SplitContent setCreaterid(String createrid) {
        this.createrid = createrid;
        return this;
    }
    public String getTextBox() {
        return textBox;
    }

    public SplitContent setTextBox(String textBox) {
        this.textBox = textBox;
        return this;
    }
    public String getTextUrl() {
        return textUrl;
    }

    public SplitContent setTextUrl(String textUrl) {
        this.textUrl = textUrl;
        return this;
    }
    public Integer getTops() {
        return tops;
    }

    public SplitContent setTops(Integer tops) {
        this.tops = tops;
        return this;
    }
    public String getVersionId() {
        return versionId;
    }

    public SplitContent setVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }
    public Integer getImgFloatType() {
        return imgFloatType;
    }

    public SplitContent setImgFloatType(Integer imgFloatType) {
        this.imgFloatType = imgFloatType;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public SplitContent setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public SplitContent setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }
    public Integer getPublishEnable() {
        return publishEnable;
    }

    public SplitContent setPublishEnable(Integer publishEnable) {
        this.publishEnable = publishEnable;
        return this;
    }

    @Override
    public String toString() {
        return "SplitContent{" +
        "modelSplitId=" + modelSplitId +
        ", modelId=" + modelId +
        ", channelId=" + channelId +
        ", showTitle=" + showTitle +
        ", dtype=" + dtype +
        ", contentChannelId=" + contentChannelId +
        ", contentid=" + contentid +
        ", linkUrl=" + linkUrl +
        ", subindex=" + subindex +
        ", createrid=" + createrid +
        ", textBox=" + textBox +
        ", textUrl=" + textUrl +
        ", tops=" + tops +
        ", versionId=" + versionId +
        ", imgFloatType=" + imgFloatType +
        ", createTime=" + createTime +
        ", modifyTime=" + modifyTime +
        ", publishEnable=" + publishEnable +
        "}";
    }

    public Integer getModelSplitLogicId()
    {
        return modelSplitLogicId;
    }

    public void setModelSplitLogicId(Integer modelSplitLogicId)
    {
        this.modelSplitLogicId = modelSplitLogicId;
    }

    public Integer getChannelLogicId()
    {
        return channelLogicId;
    }

    public void setChannelLogicId(Integer channelLogicId)
    {
        this.channelLogicId = channelLogicId;
    }

    public Integer getContentChannelLogicId()
    {
        return contentChannelLogicId;
    }

    public void setContentChannelLogicId(Integer contentChannelLogicId)
    {
        this.contentChannelLogicId = contentChannelLogicId;
    }

    public Integer getContentLogicId()
    {
        return contentLogicId;
    }

    public void setContentLogicId(Integer contentLogicId)
    {
        this.contentLogicId = contentLogicId;
    }
}
