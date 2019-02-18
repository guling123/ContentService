/**
 *   
 *
 * @Title: ContentCreateVO.java 
 * @Package cn.people.controller.vo 
 * @Description: 添加内容请求参数 
 * @author shidandan
 * @date 2018年12月4日 下午4:03:25 
 * @version V1.0   
 */
package cn.people.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
/**
 *  
 *
 * @author shidandan
 * @ClassName: ContentCreateVO 
 * @Description: 添加内容请求参数 
 * @date 2018年12月4日 下午4:03:25 
 *   
 */
@ApiModel(value = "更新内容请求参数", description = "更新内容请求参数")
public class ContentUpdateVO implements Serializable {

    private static final long serialVersionUID = -2549238145526943882L;
    @ApiModelProperty(value = "站点id")
    private String siteid;
    
    @ApiModelProperty(value = "id")
    private String id;
    
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

    @ApiModelProperty(value = "缩略图URL")
    private String imageurl;

    @ApiModelProperty(value = "内容类型。1图文、2图集、3点播、4直播")
    private Integer dtype;

    @ApiModelProperty(value = "内容主题")
    private String content;
    
    @ApiModelProperty(value = "操作标识，1保存，2提审")
    private String dstatus;
    
    @ApiModelProperty(value = "操作人")
    private String operatorid;

    @ApiModelProperty(value = "操作人名称")
    private String operatorname;

    @ApiModelProperty(value = "发布时间")
    private long sendtime;
    
    @ApiModelProperty(value = "跳转链接")
    private String redirecturl;
    
    @ApiModelProperty(value = "内容正文中的URL集合")
    private String imageUrls;

    public long getSendtime()
    {
        return sendtime;
    }

    public String getImageUrls()
    {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls)
    {
        this.imageUrls = imageUrls;
    }

    public String getSiteid()
    {
        return siteid;
    }

    public void setSiteid(String siteid)
    {
        this.siteid = siteid;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDstatus()
    {
        return dstatus;
    }

    public void setDstatus(String dstatus)
    {
        this.dstatus = dstatus;
    }

    public String getRedirecturl()
    {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl)
    {
        this.redirecturl = redirecturl;
    }

    public void setSendtime(long sendtime)
    {
        this.sendtime = sendtime;
    }

    public String getOperatorid()
    {
        return operatorid;
    }

    public void setOperatorid(String operatorid)
    {
        this.operatorid = operatorid;
    }

    public String getOperatorname()
    {
        return operatorname;
    }

    public void setOperatorname(String operatorname)
    {
        this.operatorname = operatorname;
    }

    public List<String> getChannelids()
    {
        return channelids;
    }

    public void setChannelids(List<String> channelids)
    {
        this.channelids = channelids;
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
    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getDtype() {
        return dtype;
    }

    public void setDtype(Integer dtype) {
        this.dtype = dtype;
    }
}
