package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 内容主体表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_content")
@ApiModel(value="Content对象", description="内容主体表")
public class Content extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "站点ID")
    @TableField("siteid")
    private String siteid;

    @ApiModelProperty(value = "版本id")
    @TableField("versionid")
    private String versionid;

    @ApiModelProperty(value = "栏目ID")
    @TableField("channelid")
    private String channelid;

    @ApiModelProperty(value = "地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "稿源id")
    @TableField("sourceid")
    private String sourceid;

    @ApiModelProperty(value = "稿源名称")
    @TableField("sourcename")
    private String sourcename;

    @ApiModelProperty(value = "稿源地址")
    @TableField("sourceurl")
    private String sourceurl;

    @ApiModelProperty(value = "发布时间")
    @TableField("sendtime")
    private Date sendtime;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private Date createtime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("createtid")
    private String createtid;
    
    @ApiModelProperty(value = "创建人姓名")
    @TableField("createtname")
    private String createtname;

    @ApiModelProperty(value = "修改时间")
    @TableField("updatetime")
    private Date updatetime;

    @ApiModelProperty(value = "状态。1未提审，2已提审，3一审通过，4一审失败，5二审通过，6二审失败，7已发布,8 已删除,9已下线,10发布中")
    @TableField("dstatus")
    private Integer dstatus;

    @ApiModelProperty(value = "内容类型。1图文、2图集、3点播、4直播")
    @TableField("dtype")
    private Integer dtype;

    @ApiModelProperty(value = "逻辑ID 4 位数值，100000起,站点ID内不重复")
    @TableField("logic_id")
    private Integer logicId;


    /**
     * 稿件状态 - 未提审
     */
    public static final Integer DSTATUS_CREATED=1;
    /**
     * 稿件状态 - 已提审交
     */
    public static final Integer DSTATUS_SUBMIT_AUDIT=2;
    /**
     * 稿件状态 - 一审通过
     */
    public static final Integer DSTATUS_FIRST_PASS=3;
    /**
     * 稿件状态 - 一审失败
     */
    public static final Integer DSTATUS_FIRST_REJECT=4;
    /**
     * 稿件状态 - 二审通过
     */
    public static final Integer DSTATUS_SECOND_PASS=5;
    /**
     * 稿件状态 - 二审失败
     */
    public static final Integer DSTATUS_SECOND_REJECT=6;
    /**
     * 稿件状态 - 已发布
     */
    public static final Integer DSTATUS_PUBLISHED=7;
    /**
     * 稿件状态 - 已删除
     */
    public static final Integer DSTATUS_DELETED=8;
    /**
     * 稿件状态 - 已下线
     */
    public static final Integer DSTATUS_DOWN=9;
    /**
     * 稿件状态 - 发布中
     */
    public static final Integer DSTATUS_PUBLISHING=10;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getCreatetid()
    {
        return createtid;
    }

    public void setCreatetid(String createtid)
    {
        this.createtid = createtid;
    }

    public String getCreatetname()
    {
        return createtname;
    }

    public void setCreatetname(String createtname)
    {
        this.createtname = createtname;
    }

    public String getSiteid() {
        return siteid;
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

    public String getChannelid()
    {
        return channelid;
    }

    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }

    public Content setSiteid(String siteid) {
        this.siteid = siteid;
        return this;
    }
    public String getVersionid() {
        return versionid;
    }

    public Content setVersionid(String versionid) {
        this.versionid = versionid;
        return this;
    }
    public String getSourceid() {
        return sourceid;
    }

    public Content setSourceid(String sourceid) {
        this.sourceid = sourceid;
        return this;
    }
    public Date getSendtime() {
        return sendtime;
    }

    public Content setSendtime(Date sendtime) {
        this.sendtime = sendtime;
        return this;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public Content setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }
    public Date getUpdatetime() {
        return updatetime;
    }

    public Content setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
        return this;
    }
    public Integer getDstatus() {
        return dstatus;
    }

    public Content setDstatus(Integer dstatus) {
        this.dstatus = dstatus;
        return this;
    }
    public Integer getDtype() {
        return dtype;
    }

    public Content setDtype(Integer dtype) {
        this.dtype = dtype;
        return this;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    @Override public String toString()
    {
        return "Content{" + "siteid='" + siteid + '\'' + ", versionid='" + versionid + '\''
               + ", channelid='" + channelid + '\'' + ", url='" + url + '\'' + ", sourceid='"
               + sourceid + '\'' + ", sourcename='" + sourcename + '\'' + ", sourceurl='"
               + sourceurl + '\'' + ", sendtime=" + sendtime + ", createtime=" + createtime
               + ", createtid=" + createtid + ", createtname=" + createtname + ", updatetime="
               + updatetime + ", dstatus=" + dstatus + ", dtype=" + dtype + ", logicId=" + logicId
               + '}';
    }
}
