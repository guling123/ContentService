package cn.people.controller.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;


/**
 * @author guling
 * @ClassName: ContentListVO
 * @Description: 内容返回列表(这里用一句话描述这个类的作用)
 * @date 2019/1/24 12:17
 */
public class ContentListVO implements Serializable
{
    @ApiModelProperty(value = "内容id")
    private String id;

    @ApiModelProperty(value = "站点ID")
    private String siteId;

    @ApiModelProperty(value = "稿源地址")
    private String sourceUrl;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容类型。1图文、2图集、3点播、4直播")
    private Integer dtype;

    @ApiModelProperty(value = "地址")
    private String url;
    
    @ApiModelProperty(value = "稿源名称")
    private String sourceName;

    @ApiModelProperty(value = "创建者name")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "状态。1未提审，2已提审，2一审通过，3一审失败，4二审通过，5二审失败，6已发布,7 已删除")
    private Integer dstatus;

    @ApiModelProperty(value = "发布时间")
    private Date sendTime;
    
    @ApiModelProperty(value = "发布人")
    private String sendName;

    @ApiModelProperty(value = "逻辑ID 4 位数值，100000起,站点ID内不重复")
    private Integer logicId;

    @ApiModelProperty(value = "channel logic_id")
    private  String channelLogicId;

    public String getChannelLogicId()
    {
        return channelLogicId;
    }

    public void setChannelLogicId(String channelLogicId)
    {
        this.channelLogicId = channelLogicId;
    }

    public String getId()
    {
        return id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getSendName()
    {
        return sendName;
    }

    public void setSendName(String sendName)
    {
        this.sendName = sendName;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSiteId()
    {
        return siteId;
    }

    public void setSiteId(String siteId)
    {
        this.siteId = siteId;
    }

    public String getSourceUrl()
    {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getDtype()
    {
        return dtype;
    }

    public void setDtype(Integer dtype)
    {
        this.dtype = dtype;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Integer getDstatus()
    {
        return dstatus;
    }

    public void setDstatus(Integer dstatus)
    {
        this.dstatus = dstatus;
    }

    public Date getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }
}
