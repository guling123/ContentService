/**   
* @Title: ModelSplitPropCreateVO.java 
* @Package cn.people.controller.vo 
* @Description: 模板碎片属性创建参数
* @author tianlu
* @date 2019年1月14日 下午4:42:40 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelSplitPropCreateVO 
* @Description: 模板碎片属性创建参数
* @author tianlu
* @date 2019年1月14日 下午4:42:40 
*  
*/
@ApiModel(value="模板碎片属性创建参数", description="模板碎片属性创建参数")
public class ModelSplitPropCreateVO implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "碎片属性id")
    private String splitPropId;
    
    @ApiModelProperty(value = "碎片方案id")
    private String modId;

    @ApiModelProperty(value = "创建人 ")
    private String createId;
    
 
    public String getSplitPropId()
    {
        return splitPropId;
    }

    public String getModId()
    {
        return modId;
    }

    public void setModId(String modId)
    {
        this.modId = modId;
    }

    public void setSplitPropId(String splitPropId)
    {
        this.splitPropId = splitPropId;
    }

    public String getCreateId()
    {
        return createId;
    }

    public void setCreateId(String createId)
    {
        this.createId = createId;
    }

}
