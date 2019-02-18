package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 内容版本表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_content_version")
@ApiModel(value="ContentVersion对象", description="内容版本表")
public class ContentVersion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容id")
    @TableField("contentid")
    private String contentid;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "副标题")
    @TableField("subtitle")
    private String subtitle;

    @ApiModelProperty(value = "肩标题")
    @TableField("shouldertitle")
    private String shouldertitle;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "内容描述")
    @TableField("description")
    private String description;
    
    
    @ApiModelProperty(value = "跳转链接")
    @TableField("redirecturl")
    private String redirecturl;
    
    @ApiModelProperty(value = "图片数")
    @TableField("imagecount")
    private String imagecount;

    @ApiModelProperty(value = "缩略图id")
    @TableField("imageid")
    private String imageid;

    @ApiModelProperty(value = "缩略图URL")
    @TableField("imageurl")
    private String imageurl;

    @ApiModelProperty(value = "模板id")
    @TableField("modelid")
    private String modelid;

    @ApiModelProperty(value = "内容主体")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "操作时间")
    @TableField("createtime")
    private Date createtime;

    @ApiModelProperty(value = "操作类型，1创建，2保存，3保存并提审，4提审，5二审通过，6二审不通过，7三审通过，8三审不通过，9上线，10下线，11置顶")
    @TableField("dtype")
    private Integer dtype;

    @ApiModelProperty(value = "操作人id")
    @TableField("operatorid")
    private String operatorid;
    
    @ApiModelProperty(value = "操作人名称")
    @TableField("operatorname")
    private String operatorname;

    @ApiModelProperty(value = "内容变化。0 无变化，1有变化")
    @TableField("contentchange")
    private Integer contentchange;

    public String getContentid() {
        return contentid;
    }

    public String getRedirecturl()
    {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl)
    {
        this.redirecturl = redirecturl;
    }

    public String getImagecount()
    {
        return imagecount;
    }

    public void setImagecount(String imagecount)
    {
        this.imagecount = imagecount;
    }

    public String getOperatorname()
    {
        return operatorname;
    }

    public void setOperatorname(String operatorname)
    {
        this.operatorname = operatorname;
    }

    public ContentVersion setContentid(String contentid) {
        this.contentid = contentid;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public ContentVersion setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getSubtitle() {
        return subtitle;
    }

    public ContentVersion setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }
    public String getShouldertitle() {
        return shouldertitle;
    }

    public ContentVersion setShouldertitle(String shouldertitle) {
        this.shouldertitle = shouldertitle;
        return this;
    }
    public String getAuthor() {
        return author;
    }

    public ContentVersion setAuthor(String author) {
        this.author = author;
        return this;
    }
    public String getDescription() {
        return description;
    }

    public ContentVersion setDescription(String description) {
        this.description = description;
        return this;
    }
    public String getImageid() {
        return imageid;
    }

    public ContentVersion setImageid(String imageid) {
        this.imageid = imageid;
        return this;
    }
    public String getImageurl() {
        return imageurl;
    }

    public ContentVersion setImageurl(String imageurl) {
        this.imageurl = imageurl;
        return this;
    }
    public String getModelid() {
        return modelid;
    }

    public ContentVersion setModelid(String modelid) {
        this.modelid = modelid;
        return this;
    }
    public String getContent() {
        return content;
    }

    public ContentVersion setContent(String content) {
        this.content = content;
        return this;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public ContentVersion setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }
    public Integer getDtype() {
        return dtype;
    }

    public ContentVersion setDtype(Integer dtype) {
        this.dtype = dtype;
        return this;
    }
    public String getOperatorid() {
        return operatorid;
    }

    public ContentVersion setOperatorid(String operatorid) {
        this.operatorid = operatorid;
        return this;
    }
    public Integer getContentchange() {
        return contentchange;
    }

    public ContentVersion setContentchange(Integer contentchange) {
        this.contentchange = contentchange;
        return this;
    }

    @Override
    public String toString() {
        return "ContentVersion{" +
        "contentid=" + contentid +
        ", title=" + title +
        ", subtitle=" + subtitle +
        ", shouldertitle=" + shouldertitle +
        ", author=" + author +
        ", description=" + description +
        ", imageid=" + imageid +
        ", imageurl=" + imageurl +
        ", modelid=" + modelid +
        ", content=" + content +
        ", createtime=" + createtime +
        ", dtype=" + dtype +
        ", operatorid=" + operatorid +
        ", contentchange=" + contentchange +
        "}";
    }
}
