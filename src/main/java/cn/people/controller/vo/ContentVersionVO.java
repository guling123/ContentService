package cn.people.controller.vo;

import java.io.Serializable;
import java.util.Date;

import cn.people.entity.ContentVersion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 内容版本表
 * </p>
 *
 * @author gaoyongjiu
 * @since 2018-12-04
 */
@ApiModel(value="ContentVersion对象", description="内容版本表")
public class ContentVersionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;
    
    @ApiModelProperty(value = "内容id")
    private String contentid;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "内容主体")
    private String content;
    
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "肩标题")
    private String shouldertitle;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "内容描述")
    private String description;

    public ContentVersionVO(ContentVersion cv) {
    	this.id=cv.getId();
    	this.contentid=cv.getContentid();
    	this.createtime=cv.getCreatetime();
    	this.content=cv.getContent();
    	this.title=cv.getTitle();
    	this.subtitle=cv.getSubtitle();
    	this.shouldertitle=cv.getShouldertitle();
    }
    
    public String getContentid() {
        return contentid;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getShouldertitle()
    {
        return shouldertitle;
    }

    public void setShouldertitle(String shouldertitle)
    {
        this.shouldertitle = shouldertitle;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ContentVersionVO setContentid(String contentid) {
        this.contentid = contentid;
        return this;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public ContentVersionVO setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }
    public String getContent() {
        return content;
    }

    public ContentVersionVO setContent(String content) {
        this.content = content;
        return this;
    }
    
}
