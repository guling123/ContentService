/**   
* @Title: ModelSplitPropUpdateVO.java 
* @Package cn.people.controller.vo 
* @Description: 模板碎片属性修改参数 
* @author shidandan
* @date 2019年1月8日 上午9:47:55 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelSplitPropUpdateVO 
* @Description: 模板碎片属性修改参数 
* @author shidandan
* @date 2019年1月8日 上午9:47:55 
*  
*/
@ApiModel(value="模板碎片属性修改参数", description="模板碎片属性修改参数")
public class ModelSplitPropUpdateVO implements Serializable
{

    private static final long serialVersionUID = 7588405722691610630L;
    @ApiModelProperty(value = "属性值")
    private String propValue;
    public String getPropValue()
    {
        return propValue;
    }
    public void setPropValue(String propValue)
    {
        this.propValue = propValue;
    }
    
    
}
