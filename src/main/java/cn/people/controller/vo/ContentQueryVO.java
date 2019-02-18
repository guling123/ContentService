package cn.people.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * 内容查询参数VO
 * </p>
 *
 * @author gaoyongjiu
 * @since 2018-12-04
 */
@ApiModel(value = "内容查询参数", description = "内容查询参数") public class ContentQueryVO extends PageVO
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键字")
    private String key;
    private String Lkey;

    @ApiModelProperty(value = "站点ID")
    private String siteid;

    @NotBlank(message = "栏目id不能为空")
    @ApiModelProperty(value = "栏目id")
    private String channelid;

    @ApiModelProperty(value = "创建人id")
    private String createid;

    @ApiModelProperty(value = "创建人名称")
    private String createName;

    @ApiModelProperty(value = "来源id")
    private String sourceid;

    @ApiModelProperty(value = "状态。1未提审，2已提审，2一审通过，3一审失败，4二审通过，5二审失败，6已发布,7 已删除")
    private Integer dstatus;

    @ApiModelProperty(value = "内容类型。1图文、2图集、3点播、4直播")
    private Integer dtype;

    @ApiModelProperty(value = "创建人ids")
    private Set<String> createIds;

    @ApiModelProperty(value = "创建人ids")
    private List<String> createIdsList;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getChannelid()
    {
        return channelid;
    }

    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Set<String> getCreateIds()
    {
        return createIds;
    }

    public void setCreateIds(Set<String> createIds)
    {
        this.createIds = createIds;
    }

    public List<String> getCreateIdsList()
    {
        return createIdsList;
    }

    public void setCreateIdsList(List<String> createIdsList)
    {
        this.createIdsList = createIdsList;
    }

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getSiteid()
    {
        return siteid;
    }

    public void setSiteid(String siteid)
    {
        this.siteid = siteid;
    }

    public String getKey()
    {
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
        this.Lkey = key;
    }

    public String getLkey()
    {
        return Lkey;
    }

    public void setLkey(String lkey)
    {
        Lkey = lkey;
    }

    public String getCreateid()
    {
        return createid;
    }

    public void setCreateid(String createid)
    {
        this.createid = createid;
    }

    public String getSourceid()
    {
        return sourceid;
    }

    public void setSourceid(String sourceid)
    {
        this.sourceid = sourceid;
    }

    public Integer getDstatus()
    {
        return dstatus;
    }

    public void setDstatus(Integer dstatus)
    {
        this.dstatus = dstatus;
    }

    public Integer getDtype()
    {
        return dtype;
    }

    public void setDtype(Integer dtype)
    {
        this.dtype = dtype;
    }
}
