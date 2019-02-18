package cn.people.controller.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
* @ClassName: PublishChannel 
* @Description: 栏目发布下线资源树对象
* @author zhangchengchun
* @date 2019年1月23日 下午12:55:02 
*  
 */
@ApiModel(value = "栏目发布下线资源树反参对象", description = "栏目发布下线资源树反参对象")
public class PublishChannelResponseVO
{
    @ApiModelProperty(value = "栏目逻辑id")
    private Integer logicId;
    
    @ApiModelProperty(value = "栏目id")
    private String id;
    
    @ApiModelProperty(value = "该栏目的子栏目集合")
    private List<PublishChannelResponseVO> subChannels=new ArrayList<PublishChannelResponseVO>();
    
    @ApiModelProperty(value = "该栏目的稿件逻辑Id集合")
    private List<Integer> contentLogicIds=new ArrayList<Integer>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public List<PublishChannelResponseVO> getSubChannels()
    {
        return subChannels;
    }

    public void setSubChannels(List<PublishChannelResponseVO> subChannels)
    {
        this.subChannels = subChannels;
    }

    public List<Integer> getContentLogicIds()
    {
        return contentLogicIds;
    }

    public void setContentLogicIds(List<Integer> contentLogicIds)
    {
        this.contentLogicIds = contentLogicIds;
    }

    
}
