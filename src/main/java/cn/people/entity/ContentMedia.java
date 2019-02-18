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
 * 
 * </p>
 *
 * @author shidandan
 * @since 2019-01-15
 */
@TableName("tb_content_media")
@ApiModel(value="ContentMedia对象", description="ContentMedia对象")
public class ContentMedia extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "该媒体文件所对应的正文ID")
    @TableField("content_id")
    private String contentId;

    @ApiModelProperty(value = "媒体文件类型")
    @TableField("media_type")
    private String mediaType;

    @ApiModelProperty(value = "媒体文件名称")
    @TableField("media_name")
    private String mediaName;

    @ApiModelProperty(value = "媒体文件保存的物理路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "媒体文件说明")
    @TableField("media_desc")
    private String mediaDesc;

    @ApiModelProperty(value = "媒体文件的URL字符串")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "媒体文件排序标示，level>=0 才会自动显示在正文")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "文章创建时间")
    @TableField("content_input_date")
    private Date contentInputDate;

    @ApiModelProperty(value = "0:媒体列表上传 1:稿件内容中上传")
    @TableField("uploadtype")
    private Integer uploadtype;

    @ApiModelProperty(value = "媒体文件标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "是否是封面图片 1：封面 0：非封面")
    @TableField("head_pic")
    private Integer headPic;

    public String getUserId() {
        return userId;
    }

    public ContentMedia setUserId(String userId) {
        this.userId = userId;
        return this;
    }
    public String getContentId() {
        return contentId;
    }

    public ContentMedia setContentId(String contentId) {
        this.contentId = contentId;
        return this;
    }
    public String getMediaType() {
        return mediaType;
    }

    public ContentMedia setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }
    public String getMediaName() {
        return mediaName;
    }

    public ContentMedia setMediaName(String mediaName) {
        this.mediaName = mediaName;
        return this;
    }
    public String getPath() {
        return path;
    }

    public ContentMedia setPath(String path) {
        this.path = path;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ContentMedia setUrl(String url) {
        this.url = url;
        return this;
    }
    public Integer getLevel() {
        return level;
    }

    public ContentMedia setLevel(Integer level) {
        this.level = level;
        return this;
    }
    public Date getContentInputDate() {
        return contentInputDate;
    }

    public ContentMedia setContentInputDate(Date contentInputDate) {
        this.contentInputDate = contentInputDate;
        return this;
    }
    public Integer getUploadtype() {
        return uploadtype;
    }

    public ContentMedia setUploadtype(Integer uploadtype) {
        this.uploadtype = uploadtype;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public ContentMedia setTitle(String title) {
        this.title = title;
        return this;
    }
    public Integer getHeadPic() {
        return headPic;
    }

    public ContentMedia setHeadPic(Integer headPic) {
        this.headPic = headPic;
        return this;
    }

    public String getMediaDesc()
    {
        return mediaDesc;
    }

    public void setMediaDesc(String mediaDesc)
    {
        this.mediaDesc = mediaDesc;
    }

    @Override public String toString()
    {
        return "ContentMedia{" + "userId='" + userId + '\'' + ", contentId='" + contentId + '\''
               + ", mediaType='" + mediaType + '\'' + ", mediaName='" + mediaName + '\''
               + ", path='" + path + '\'' + ", mediaDesc='" + mediaDesc + '\'' + ", url='" + url
               + '\'' + ", level=" + level + ", contentInputDate=" + contentInputDate
               + ", uploadtype=" + uploadtype + ", title='" + title + '\'' + ", headPic=" + headPic
               + '}';
    }
}
