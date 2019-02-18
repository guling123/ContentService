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
 * 栏目信息表
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_channel")
@ApiModel(value="Channel对象", description="栏目信息表")
public class Channel extends BaseEntity {
    
    /**
     * 未签发
     */
    public static final Integer STATUSRELEASE_UNRELEASE=0;
    /**
     * 已签发 
     */
    public static final Integer STATUSRELEASE_RELEASED=1;
    /**
     * 发布中 
     */
    public static final Integer STATUSRELEASE_RELEASEING=2;
    /**
     * 未启用状态 
     */
    public static final Integer DSTATUS_NOTENABLED=1;
    /**
     * 启用状态 
     */
    public static final Integer DSTATUS_ENABLED=2;
    /**
     * 停用状态 
     */
    public static final Integer DSTATUS_DISABLED=3;
    
    

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "站点id")
    @TableField("siteid")
    private String siteid;

    @ApiModelProperty(value = "逻辑ID 4 位数值，100000起,站点ID内不重复 （暂时不用）")
    @TableField("logic_id")
    private Integer logicId;

    @ApiModelProperty(value = "栏目名称")
    @TableField("channel_name")
    private String channelName;

    @ApiModelProperty(value = "栏目标识")
    @TableField("ident")
    private String ident;

    @ApiModelProperty(value = "栏目类型 ，1 栏目，2专题")
    @TableField("dtype")
    private Integer dtype;

    @ApiModelProperty(value = "栏目显示，0隐藏，1显示")
    @TableField("display")
    private Integer display;

    @ApiModelProperty(value = "栏目头图图片id")
    @TableField("image_url")
    private String imageUrl;

    @ApiModelProperty(value = "栏目描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "栏目深度。跟栏目为0")
    @TableField("deep")
    private Integer deep;

    @ApiModelProperty(value = "父栏目id")
    @TableField("parent_id")
    private String parentId;
    
    @ApiModelProperty(value = "父栏目id字符串")
    @TableField("parent_string")
    private String parentString;

    @ApiModelProperty(value = "栏目页模板id")
    @TableField("channel_model_id")
    private String channelModelId;

    @ApiModelProperty(value = "图文内容模板id")
    @TableField("content_model_id")
    private String contentModelId;

    @ApiModelProperty(value = "图集内容模板id")
    @TableField("images_model_id")
    private String imagesModelId;

    @ApiModelProperty(value = "域名")
    @TableField("domain")
    private String domain;
    
    @ApiModelProperty(value = "URL路径")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "签发状态 , 1为已签发")
    @TableField("status_release")
    private Integer statusRelease;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    @TableField("creater_id")
    private String createrId;

    @ApiModelProperty(value = "状态。1未启用，2已启用，3停用")
    @TableField("dstatus")
    private Integer dstatus;

    public String getSiteid() {
        return siteid;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Channel setSiteid(String siteid) {
        this.siteid = siteid;
        return this;
    }
    public Integer getLogicId() {
        return logicId;
    }

    public Channel setLogicId(Integer logicId) {
        this.logicId = logicId;
        return this;
    }
    public String getChannelName() {
        return channelName;
    }

    public Channel setChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }
    public String getIdent() {
        return ident;
    }

    public Channel setIdent(String ident) {
        this.ident = ident;
        return this;
    }
    public Integer getDtype() {
        return dtype;
    }

    public Channel setDtype(Integer dtype) {
        this.dtype = dtype;
        return this;
    }
    public Integer getDisplay() {
        return display;
    }

    public Channel setDisplay(Integer display) {
        this.display = display;
        return this;
    }
   
    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public Channel setDescription(String description) {
        this.description = description;
        return this;
    }
    public Integer getDeep() {
        return deep;
    }

    public Channel setDeep(Integer deep) {
        this.deep = deep;
        return this;
    }
    public String getParentId() {
        return parentId;
    }

    public Channel setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }
    public String getChannelModelId() {
        return channelModelId;
    }

    public Channel setChannelModelId(String channelModelId) {
        this.channelModelId = channelModelId;
        return this;
    }
    public String getContentModelId() {
        return contentModelId;
    }

    public Channel setContentModelId(String contentModelId) {
        this.contentModelId = contentModelId;
        return this;
    }
    public String getImagesModelId() {
        return imagesModelId;
    }

    public Channel setImagesModelId(String imagesModelId) {
        this.imagesModelId = imagesModelId;
        return this;
    }
    public String getDomain() {
        return domain;
    }

    public Channel setDomain(String domain) {
        this.domain = domain;
        return this;
    }
    public Integer getStatusRelease() {
        return statusRelease;
    }

    public Channel setStatusRelease(Integer statusRelease) {
        this.statusRelease = statusRelease;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Channel setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getCreaterId() {
        return createrId;
    }

    public Channel setCreaterId(String createrId) {
        this.createrId = createrId;
        return this;
    }
    public Integer getDstatus() {
        return dstatus;
    }

    public Channel setDstatus(Integer dstatus) {
        this.dstatus = dstatus;
        return this;
    }
    
    

    public String getParentString()
    {
        return parentString;
    }

    public void setParentString(String parentString)
    {
        this.parentString = parentString;
    }

    @Override
    public String toString() {
        return "Channel{" +
               "siteid=" + siteid +
               ", logicId=" + logicId +
               ", channelName=" + channelName +
               ", ident=" + ident +
               ", dtype=" + dtype +
               ", display=" + display +
               ", description=" + description +
               ", deep=" + deep +
               ", parentId=" + parentId +
               ", parentString=" + parentString +
               ", channelModelId=" + channelModelId +
               ", contentModelId=" + contentModelId +
               ", imagesModelId=" + imagesModelId +
               ", domain=" + domain +
               ", statusRelease=" + statusRelease +
               ", createTime=" + createTime +
               ", createrId=" + createrId +
               ", dstatus=" + dstatus +
               "}";
    }
}
