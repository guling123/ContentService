/**   
* @Title: ChannelListRequestVO.java 
* @Package cn.people.controller.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2019年2月1日 下午6:33:59 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ChannelListRequestVO 
* @Description: 栏目请求参数
* @author shidandan
* @date 2019年2月1日 下午6:33:59 
*  
*/
@ApiModel(value = "栏目请求参数", description = "栏目请求参数")
public class ChannelListRequestVO implements Serializable
{

    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = -2125077813874582832L;
    
    @ApiModelProperty(value = "栏目ID集合")
    private Set<String> channelIds;

    public Set<String> getChannelIds()
    {
        return channelIds;
    }

    public void setChannelIds(Set<String> channelIds)
    {
        this.channelIds = channelIds;
    }
    
}
