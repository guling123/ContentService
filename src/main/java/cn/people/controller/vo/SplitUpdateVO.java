/**   
* @Title: SplitUpdateVO.java 
* @Package cn.people.controller.vo 
* @Description: 修改碎片参数 
* @author shidandan
* @date 2019年1月7日 下午7:50:51 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitUpdateVO 
* @Description: 修改碎片参数 
* @author shidandan
* @date 2019年1月7日 下午7:50:51 
*  
*/
@ApiModel(value="修改碎片参数", description="修改碎片参数")
public class SplitUpdateVO implements Serializable
{

    private static final long serialVersionUID = -7164032044955849783L;
    
    @ApiModelProperty(value = "碎片名称")
    private String splitName;

    @ApiModelProperty(value = "碎片类型")
    private Integer type;
    
    @ApiModelProperty(value = "主控栏目")
    private String channelId;

    public String getSplitName()
    {
        return splitName;
    }

    public void setSplitName(String splitName)
    {
        this.splitName = splitName;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getChannelId()
    {
        return channelId;
    }

    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }
    
}
