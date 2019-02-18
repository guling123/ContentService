/**   
* @Title: ModelSplitPropBatchUpdateVO.java 
* @Package cn.people.controller.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2019年1月29日 上午11:02:55 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelSplitPropBatchUpdateVO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author shidandan
* @date 2019年1月29日 上午11:02:55 
*  
*/
public class ModelSplitPropBatchUpdateVO implements Serializable
{

    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = -8499043938762829319L;
    @ApiModelProperty(value = "栏目ID")
    private String channelId;
    
    @ApiModelProperty(value = "属性值")
    private List<ModelSplitPropVO> propList;
    public List<ModelSplitPropVO> getPropList()
    {
        return propList;
    }
    public void setPropList(List<ModelSplitPropVO> propList)
    {
        this.propList = propList;
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
