/**   
* @Title: ModelSplitPropListVO.java 
* @Package cn.people.controller.vo 
* @Description: 模板碎片属性列表查询结果
* @author shidandan
* @date 2019年1月16日 上午10:52:54 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelSplitPropListVO 
* @Description: 模板碎片属性列表查询结果
* @author shidandan
* @date 2019年1月16日 上午10:52:54 
*  
*/
@ApiModel(value="模板碎片属性列表查询结果", description="模板碎片属性列表查询结果")
public class ModelSplitPropListVO implements Serializable
{

    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = -2978645903300051671L;
    @ApiModelProperty(value = "id")
    private String id;
    
    @ApiModelProperty(value = "碎片id")
    private String splitId;
    
    @ApiModelProperty(value = "属性名称")
    private String propName;

    @ApiModelProperty(value = "属性描述")
    private String propDes;

    @ApiModelProperty(value = "属性值")
    private String propValue;

    @ApiModelProperty(value = "属性级别，0表示高级，1表示普通(编辑在页面控制中可以修改其值)符型")
    private Integer propLevel;
    
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createId;

    public String getId()
    {
        return id;
    }

    public String getSplitId()
    {
        return splitId;
    }

    public void setSplitId(String splitId)
    {
        this.splitId = splitId;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPropName()
    {
        return propName;
    }

    public void setPropName(String propName)
    {
        this.propName = propName;
    }

    public String getPropDes()
    {
        return propDes;
    }

    public void setPropDes(String propDes)
    {
        this.propDes = propDes;
    }

    public String getPropValue()
    {
        return propValue;
    }

    public void setPropValue(String propValue)
    {
        this.propValue = propValue;
    }

    public Integer getPropLevel()
    {
        return propLevel;
    }

    public void setPropLevel(Integer propLevel)
    {
        this.propLevel = propLevel;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
