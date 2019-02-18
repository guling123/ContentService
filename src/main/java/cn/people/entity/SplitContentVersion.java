package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片内容表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-20
 */
@TableName("tb_split_content_version")
@ApiModel(value="SplitContentVersion对象", description="碎片内容表")
public class SplitContentVersion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "碎片id")
    @TableField("splitid")
    private String splitid;

    @ApiModelProperty(value = "显示标题")
    @TableField("showtitle")
    private String showtitle;

    @ApiModelProperty(value = "内容类型,1内容,2外联")
    @TableField("dtype")
    private Integer dtype;

    @ApiModelProperty(value = "内容id")
    @TableField("contentid")
    private String contentid;

    @ApiModelProperty(value = "添加时间")
    @TableField("createtime")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "排序")
    @TableField("subindex")
    private Integer subindex;

    @ApiModelProperty(value = "添加人id")
    @TableField("createrid")
    private String createrid;

    @ApiModelProperty(value = "外联地址")
    @TableField("href")
    private String href;

    @ApiModelProperty(value = "缩略图")
    @TableField("thumbnail")
    private String thumbnail;
    
    @ApiModelProperty(value = "状态，0删除，1显示")
    @TableField("dstatus")
    private Integer dstatus;

    @ApiModelProperty(value = "置顶，0未置顶，1置顶")
    @TableField("tops")
    private Integer tops;

    @ApiModelProperty(value = "碎片版本id")
    @TableField("versionid")
    private String versionid;

    public String getSplitid() {
        return splitid;
    }

    public SplitContentVersion setSplitid(String splitid) {
        this.splitid = splitid;
        return this;
    }
    public String getShowtitle() {
        return showtitle;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public SplitContentVersion setShowtitle(String showtitle) {
        this.showtitle = showtitle;
        return this;
    }
    public Integer getDtype() {
        return dtype;
    }

    public SplitContentVersion setDtype(Integer dtype) {
        this.dtype = dtype;
        return this;
    }
    public String getContentid() {
        return contentid;
    }

    public SplitContentVersion setContentid(String contentid) {
        this.contentid = contentid;
        return this;
    }
    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public SplitContentVersion setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
        return this;
    }
    public Integer getSubindex() {
        return subindex;
    }

    public SplitContentVersion setSubindex(Integer subindex) {
        this.subindex = subindex;
        return this;
    }
    public String getCreaterid() {
        return createrid;
    }

    public SplitContentVersion setCreaterid(String createrid) {
        this.createrid = createrid;
        return this;
    }
    public String getHref() {
        return href;
    }

    public SplitContentVersion setHref(String href) {
        this.href = href;
        return this;
    }
    public Integer getDstatus() {
        return dstatus;
    }

    public SplitContentVersion setDstatus(Integer dstatus) {
        this.dstatus = dstatus;
        return this;
    }
    public Integer getTops() {
        return tops;
    }

    public SplitContentVersion setTops(Integer tops) {
        this.tops = tops;
        return this;
    }
    public String getVersionid() {
        return versionid;
    }

    public SplitContentVersion setVersionid(String versionid) {
        this.versionid = versionid;
        return this;
    }

    @Override
    public String toString() {
        return "SplitContentVersion{" +
        "splitid=" + splitid +
        ", showtitle=" + showtitle +
        ", dtype=" + dtype +
        ", contentid=" + contentid +
        ", createtime=" + createtime +
        ", subindex=" + subindex +
        ", createrid=" + createrid +
        ", href=" + href +
        ", dstatus=" + dstatus +
        ", tops=" + tops +
        ", versionid=" + versionid +
        "}";
    }
}
