package cn.people.controller.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
* @ClassName: PublishChanneRequest 
* @Description: 栏目发布下线资源树入参对象
* @author zhangchengchun
* @date 2019年1月23日 下午1:01:14 
*  
 */
@ApiModel(value = "栏目发布下线资源树入参对象", description = "栏目发布下线资源树入参对象")
public class PublishChanneRequest
{
    @ApiModelProperty(value = "栏目逻辑id")
    private Integer logicId;
    
    @ApiModelProperty(value = "是否包含子栏目  true 包含子栏目 ; false 不包含子栏目")
    private Boolean isSubChannels=false;
    
    @ApiModelProperty(value = "是否包含子稿件  true 包含稿件 ; false 不包含稿件")
    private Boolean isContents=false;
    
    @ApiModelProperty(value = "状态 1发布  0 下线")
    private Integer type=1;
    
    @ApiModelProperty(value = "有权限的栏目id集合")
    private Set<String> authorizedChannelIds=new HashSet<String>();
    
    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public Boolean getIsSubChannels()
    {
        return isSubChannels;
    }

    public void setIsSubChannels(Boolean isSubChannels)
    {
        this.isSubChannels = isSubChannels;
    }

    public Boolean getIsContents()
    {
        return isContents;
    }

    public void setIsContents(Boolean isContents)
    {
        this.isContents = isContents;
    }

    public Set<String> getAuthorizedChannelIds()
    {
        return authorizedChannelIds;
    }

    public void setAuthorizedChannelIds(Set<String> authorizedChannelIds)
    {
        this.authorizedChannelIds = authorizedChannelIds;
    }
}
