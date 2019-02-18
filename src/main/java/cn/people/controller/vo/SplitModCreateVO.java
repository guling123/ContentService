/**   
* @Title: SplitModCreateVO.java 
* @Package cn.people.controller.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2019年1月8日 上午10:02:59 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitModCreateVO 
* @Description: 碎片方案添加参数
* @author shidandan
* @date 2019年1月8日 上午10:02:59 
*  
*/
@ApiModel(value="碎片方案添加参数", description="碎片方案添加参数")
public class SplitModCreateVO implements Serializable
{

    private static final long serialVersionUID = 8107705886857452417L;
    
    @ApiModelProperty(value = "碎片方案名称")
    private String modName;

    @ApiModelProperty(value = "模板碎片ID")
    private String modelSplitId;

    @ApiModelProperty(value = "添加人")
    private String createId;

    public String getModName()
    {
        return modName;
    }

    public void setModName(String modName)
    {
        this.modName = modName;
    }

    public String getModelSplitId()
    {
        return modelSplitId;
    }

    public void setModelSplitId(String modelSplitId)
    {
        this.modelSplitId = modelSplitId;
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
