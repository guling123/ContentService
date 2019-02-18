/**   
* @Title: SplitContentAddVO.java 
* @Package cn.people.controller.vo 
* @Description: 碎片内容 
* @author shidandan
* @date 2019年1月9日 上午11:06:24 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitContentAddVO 
* @Description: 碎片内容 
* @author shidandan
* @date 2019年1月9日 上午11:06:24 
*  
*/
@ApiModel(value="碎片内容", description="碎片内容")
public class SplitContentAddVO implements Serializable
{

    private static final long serialVersionUID = 3529037167498300994L;
    @NotBlank(message="模板碎片ID不能为空")
    @ApiModelProperty(value = "模板碎片ID")
    private String modelSpiltId;
    
    @NotBlank(message="栏目ID不能为空")
    @ApiModelProperty(value = "栏目ID")
    private String channelId;
    
    @ApiModelProperty(value = "碎片内容集合")
    private List<SplitContentVO> spiltContentList;

    public List<SplitContentVO> getSpiltContentList()
    {
        return spiltContentList;
    }

    public void setSpiltContentList(List<SplitContentVO> spiltContentList)
    {
        this.spiltContentList = spiltContentList;
    }

    public String getModelSpiltId()
    {
        return modelSpiltId;
    }

    public void setModelSpiltId(String modelSpiltId)
    {
        this.modelSpiltId = modelSpiltId;
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
