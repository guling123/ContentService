package cn.people.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 内容详情
 * <p>
 * Created by wilson on 2018-12-06.
 */
@ApiModel(value = "内容详情", description = "内容详情")
public class ContentDetailVO implements Serializable {

    private static final long serialVersionUID = -5188570182119720125L;

    @ApiModelProperty(value = "内容ID")
    private String id;

    @ApiModelProperty(value = "站点ID")
    private String siteid;

    @ApiModelProperty(value = "栏目id")
    private Map<String,String> channels;
    
    @ApiModelProperty(value = "栏目id")
    private List<String> channelids;

    @ApiModelProperty(value = "版本id")
    private String versionid;

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

    @ApiModelProperty(value = "来源id")
    private String sourceid;
    
    @ApiModelProperty(value = "稿源名称")
    private String sourcename;
    
    @ApiModelProperty(value = "稿源地址")
    private String sourceurl;

    @ApiModelProperty(value = "缩略图id")
    private String imageid;

    @ApiModelProperty(value = "缩略图URL")
    private String imageurl;

    @ApiModelProperty(value = "发布时间")
    private Date sendtime;
    
    @ApiModelProperty(value = "状态。0未提审，1已提审，2一审通过，3一审失败，4二审通过，5二审失败，6已发布,7 已删除")
    private Integer dstatus;

    @ApiModelProperty(value = "内容类型。1图文、2图集、3点播、4直播")
    private Integer dtype;

    @ApiModelProperty(value = "模板id")
    private String modelid;

    @ApiModelProperty(value = "内容主体")
    private String content;
    

    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    
    @ApiModelProperty(value = "修改时间")
   private Date updatetime;
    
    @ApiModelProperty(value = "跳转链接")
    private String redirecturl;
    
    @ApiModelProperty(value = "地址")
    private String url;
    
    @ApiModelProperty(value = "逻辑ID 4 位数值，100000起,站点ID内不重复")
    private Integer logicId;

    @ApiModelProperty(value = "栏目ID")
    private String channelid;
    
    public Date getUpdatetime()
    {
        return updatetime;
    }

    public String getUrl()
    {
        return url;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getRedirecturl()
    {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl)
    {
        this.redirecturl = redirecturl;
    }

    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }

    public String getSourcename()
    {
        return sourcename;
    }

    public void setSourcename(String sourcename)
    {
        this.sourcename = sourcename;
    }

    public String getSourceurl()
    {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl)
    {
        this.sourceurl = sourceurl;
    }

    @ApiModelProperty(value = "栏目逻辑id")
    private Integer channelLogicId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getShouldertitle() {
        return shouldertitle;
    }

    public void setShouldertitle(String shouldertitle) {
        this.shouldertitle = shouldertitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
    public Integer getDstatus() {
        return dstatus;
    }

    public void setDstatus(Integer dstatus) {
        this.dstatus = dstatus;
    }

    public Integer getDtype() {
        return dtype;
    }

    public Map<String,String> getChannels()
    {
        return channels;
    }

    public void setChannels(Map<String,String> channels)
    {
        this.channels = channels;
    }

    public void setDtype(Integer dtype) {
        this.dtype = dtype;
    }

    public String getModelid() {
        return modelid;
    }

    public void setModelid(String modelid) {
        this.modelid = modelid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }

    public Integer getChannelLogicId()
    {
        return channelLogicId;
    }

    public void setChannelLogicId(Integer channelLogicId)
    {
        this.channelLogicId = channelLogicId;
    }

    public Integer getMediaCount()
    {
        // TODO 暂不支持图集
        return 0;
    }

    public List<String> getChannelids()
    {
        return channelids;
    }

    public void setChannelids(List<String> channelids)
    {
        this.channelids = channelids;
    }

    public String getChannelid()
    {
        return channelid;
    }

    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }
}
