/**   
* @Title: SysUserChannelVO.java 
* @Package cn.people.controller.vo 
* @Description: 用户常用栏目
* @author shidandan
* @date 2019年1月28日 下午1:56:15 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SysUserChannelVO 
* @Description: 用户常用栏目
* @author shidandan
* @date 2019年1月28日 下午1:56:15 
*  
*/
@ApiModel(value="用户常用栏目", description="用户常用栏目")
public class SysUserChannelVO implements Serializable
{

    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = 280178255385343186L;
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "栏目ID")
    private String channelId;
    
    @ApiModelProperty(value = "栏目名称")
    private String channelName;
    
    @ApiModelProperty(value = "ID")
    private String id;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getChannelId()
    {
        return channelId;
    }

    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
