/**   
* @Title: ModelSplitPropVO.java 
* @Package cn.people.controller.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2019年1月29日 上午10:29:13 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelSplitPropVO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author shidandan
* @date 2019年1月29日 上午10:29:13 
*  
*/
public class ModelSplitPropVO implements Serializable
{

    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = -6603977332726204242L;
    @ApiModelProperty(value = "id")
    private String id;
    
    @ApiModelProperty(value = "属性值")
    private String propValue;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPropValue()
    {
        return propValue;
    }

    public void setPropValue(String propValue)
    {
        this.propValue = propValue;
    }
    
}
