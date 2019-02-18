package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 内容栏目表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_content_channel")
@ApiModel(value="ContentChannel对象", description="内容栏目表")
public class ContentChannel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容id")
    @TableField("contentid")
    private String contentid;

    @ApiModelProperty(value = "栏目id")
    @TableField("channelid")
    private String channelid;

    @ApiModelProperty(value = "站点id")
    @TableField("siteid")
    private String siteid;

    public String getContentid() {
        return contentid;
    }

    public ContentChannel setContentid(String contentid) {
        this.contentid = contentid;
        return this;
    }
    public String getChannelid() {
        return channelid;
    }

    public ContentChannel setChannelid(String channelid) {
        this.channelid = channelid;
        return this;
    }
    public String getSiteid() {
        return siteid;
    }

    public ContentChannel setSiteid(String siteid) {
        this.siteid = siteid;
        return this;
    }

    @Override
    public String toString() {
        return "ContentChannel{" +
        "contentid=" + contentid +
        ", channelid=" + channelid +
        ", siteid=" + siteid +
        "}";
    }
}
