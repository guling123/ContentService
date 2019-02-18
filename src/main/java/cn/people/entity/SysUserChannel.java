package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户常用栏目
 * </p>
 *
 * @author shidandan
 * @since 2019-01-28
 */
@TableName("tb_sys_user_channel")
@ApiModel(value="SysUserChannel对象", description="用户常用栏目")
public class SysUserChannel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "栏目ID")
    @TableField("channel_id")
    private String channelId;

    public String getUserId() {
        return userId;
    }

    public SysUserChannel setUserId(String userId) {
        this.userId = userId;
        return this;
    }
    public String getChannelId() {
        return channelId;
    }

    public SysUserChannel setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    @Override
    public String toString() {
        return "SysUserChannel{" +
        "userId=" + userId +
        ", channelId=" + channelId +
        "}";
    }
}
