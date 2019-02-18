/**   
* @Title: ChannelListVO.java 
* @Package cn.people.controller.vo 
* @Description: 栏目查询
* @author shidandan
* @date 2018年12月20日 下午1:37:19 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ChannelListVO 
* @Description: 栏目查询
* @author shidandan
* @date 2018年12月20日 下午1:37:19 
*  
*/
@ApiModel(value = "栏目查询", description = "栏目查询")
public class ChannelListVO implements Serializable
{

    private static final long serialVersionUID = -1914644388040844476L;
    
    @ApiModelProperty(value = "下级节点列表")
    List<ChannelListTreeVO> children =new ArrayList<>();

    public List<ChannelListTreeVO> getChildren()
    {
        return children;
    }

    public void setChildren(List<ChannelListTreeVO> children)
    {
        this.children = children;
    }
}
