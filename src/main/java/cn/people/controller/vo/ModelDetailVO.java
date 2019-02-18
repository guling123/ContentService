/**   
* @Title: ModelDetailVO.java 
* @Package cn.people.controller.vo 
* @Description: 模板详情
* @author shidandan
* @date 2019年1月15日 下午5:59:55 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelDetailVO 
* @Description: 模板详情 
* @author shidandan
* @date 2019年1月15日 下午5:59:55 
*  
*/
public class ModelDetailVO implements Serializable
{

    private static final long serialVersionUID = 1608094544879948266L;
    
    @ApiModelProperty(value = "id")
    private String  id;
    
    @ApiModelProperty(value = "模板名称")
    private String modelName;
    
    @ApiModelProperty(value = "类型id")
    private String typeId;
    
    @ApiModelProperty(value = "模板内容体")
    private String content;

    public String getId()
    {
        return id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getModelName()
    {
        return modelName;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }
}
